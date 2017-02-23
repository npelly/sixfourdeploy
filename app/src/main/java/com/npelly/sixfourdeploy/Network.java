package com.npelly.sixfourdeploy;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteOrder;


public class Network {
    public interface Callback {
        void updateListening(boolean isListening, String status);
    }


    private boolean listening = false;
    private boolean cancelListening = false;
    private byte[] ip;
    private final int port = 12364;
    private Callback callback;
    private ServerSocket serverSocket;

    public static final String ACTION_INSTALL_UPDATE = "com.npelly.ACTION_INSTALL_UPDATE";

    public Network() {

    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static String ipAddressToString(byte[] ip) {
        StringBuffer s = new StringBuffer(15);
        s.append(ip[0] & 0xFF).append('.');
        s.append(ip[1] & 0xFF).append('.');
        s.append(ip[2] & 0xFF).append('.');
        s.append(ip[3] & 0xFF);
        return s.toString();
    }

    public static byte[] getIpAddress() {
        WifiManager wm = (WifiManager) Base.get().getContext().getSystemService(Context.WIFI_SERVICE);
        int ipInt = wm.getConnectionInfo().getIpAddress();
        byte[] ip = new byte[4];

        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ip[3] = (byte) (ipInt >> 24);
            ip[2] = (byte) (ipInt >> 16);
            ip[1] = (byte) (ipInt >> 8);
            ip[0] = (byte) (ipInt);
        } else {
            ip[0] = (byte) (ipInt >> 24);
            ip[1] = (byte) (ipInt >> 16);
            ip[2] = (byte) (ipInt >> 8);
            ip[3] = (byte) (ipInt);
        }
        return ip;
    }

    public boolean isListening() {
        return listening;
    }

    public void stopListen() {
        if (!listening) return;
        Base.logd("stopping...");
        cancelListening = true;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {}
        }
    }

    public void startListen() {
        if (listening) return;
        listening = true;
        cancelListening = false;
        ip = getIpAddress();
        if (callback != null) {
            callback.updateListening(listening, getStatus());
        }


        new Thread() {
            @Override
            public void run() {
                try {
                    while (listening && !cancelListening) {

                        serverSocket = new ServerSocket(port);
                        Base.logd("listening on %s:%d...", ipAddressToString(ip), port);
                        Socket socket = serverSocket.accept();  // blocks

                        String address = socket.getInetAddress().toString();
                        Base.logd("connection from " + address);
                        serverSocket.close();
                        serverSocket = null;
                        InputStream in = socket.getInputStream();

                        PackageInstaller packageInstaller = Base.get().getContext().getPackageManager().getPackageInstaller();
                        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                                PackageInstaller.SessionParams.MODE_FULL_INSTALL);
                        int sessionId = packageInstaller.createSession(params);
                        PackageInstaller.Session session = packageInstaller.openSession(sessionId);
                        OutputStream out = session.openWrite("foo", 0, -1);
                        byte[] buffer = new byte[65536];
                        int c;
                        while ((c = in.read(buffer)) != -1) {  // blocks
                            Base.logd("read " + c + " bytes");
                            out.write(buffer, 0, c);
                        }
                        session.fsync(out);
                        in.close();
                        out.close();

                        Base.logd("read complete, committing...");
                        session.commit(createIntentSender(Base.get().getContext(), sessionId));
                    }
                } catch (IOException e) {
                    Base.logd(e.toString());
                }
                listening = false;
                if (callback != null) {
                    callback.updateListening(listening, getStatus());
                }
                Base.logd("stopped");
            }
        }.start();
    }

    public String getStatus() {
        if (!listening) {
            return "OFF";
        } else {
            return Shortcode.encode(ip, port);
        }
    }

    private static IntentSender createIntentSender(Context context, int sessionId) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                sessionId,
                new Intent(ACTION_INSTALL_UPDATE),
                0);
        return pendingIntent.getIntentSender();
    }
}
