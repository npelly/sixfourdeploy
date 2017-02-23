package com.npelly.sixfourdeploy;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by npelly on 23/02/17.
 */
public class ShortcodeTest {
    @Test
    public void testEncode() throws Exception {
        assertEquals("<error ip>", Shortcode.encode(new byte[]{1, 2, 3, 4}, 12364));
        assertEquals("00cp", Shortcode.encode(new byte[]{(byte)192, (byte)168, 4, (byte)179}, 12364));
        assertEquals("16ox", Shortcode.encode(new byte[]{(byte)192, (byte)168, 4, (byte)179}, 12366));
        assertEquals("00002", Shortcode.encode(new byte[]{10, 0, 0, 1}, 12364));
    }
}
