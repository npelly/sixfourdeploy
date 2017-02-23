package com.npelly.sixfourdeploy;


/**
 * Shortcode encoding
 *  PORT (2 bits)
 *      port = PORT + 12364
 *  IP ADDRESS (16 or 24 bits)
 *  IP ADDRESS CLASS (1 bit)
 *      0 Class A network 10.0.0.0/8
 *      1 Class C network 192.168.0.0/16
 */
public class Shortcode {
    private static final char[] SHORTCODE_MAP = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    };  // code space >= 60 to successfully encode 32 bits in 6 codes

    public static final String encode(byte[] ip, int port) {
        Code code = new Code();

        // PORT
        code.pushBits(port, 2);

        // IP
        if (ip[0] == 10) {
            code.pushBits(ip[1], 8);
            code.pushBits(ip[2], 8);
            code.pushBits(ip[3], 8);
            code.pushBits(0, 1);
        } else if (ip[0] == (byte)192 && ip[1] == (byte)168) {
            code.pushBits(ip[2], 8);
            code.pushBits(ip[3], 8);
            code.pushBits(1, 1);
        } else {
            return "<error ip>";
        }

        int[] codeBase = code.toBase(SHORTCODE_MAP.length);
        StringBuffer s = new StringBuffer(codeBase.length);
        for (int i = 0; i < codeBase.length; i++) {
            s.append(SHORTCODE_MAP[codeBase[i]]);
        }
        return s.toString();
    }

    private static class Code {
        long code;
        int  bits;
        public Code() {}

        public void pushBits(int b, int bits) {
            code <<= bits;
            int mask = (0x1 << bits) - 1;
            code |= b & mask;
            this.bits += bits;
        }

        public int[] toBase(int base) {
            int size = (int)Math.ceil(Math.log(Math.pow(2.0, bits)) / Math.log(base));
            int[] result = new int[size];
            long c = code;
            for (int i = size - 1; i >= 0; i--) {
                result[i] = (int)(c % (long)base);
                c /= base;
            }
            return result;
        }
    }


}
