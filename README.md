# sixfourdeploy
Push APK to device over WIFI.

Setup:
1) Enable "Allow unknown sources" in security settings on Android device.
2) Build (./gradlew build) and run SixFourDeploy on Android device, press red FAB to enable.
3) Unplug, ensure device on WIFI network. Note the shortcode displayed in main text view.

Pushing an APK (from any computer on same network):
./send.py SHORTCODE APKNAME

That's it :-)


CHANGELOG
    v0.02 prototype.
    
    Features
    - P2P only (phone on WIFI, computer on same network).
    - Shortcode connections.
    - send.py to push from computer.
    - On/Off switch.
    
    TODO
    - Port shifting when port busy.
    - Check that "unknown sources" enabled.
    - Ongoing notification when enabled.
    - Hole punching for NAT / public networks.
    - AVD / Android Studio integration.
    - Encryption / shared secrets.
