package org.mdnote.apiMonitor.util;

import java.util.zip.CRC32;

public class HashUtil {
    public static long crc32(byte[] b) {
        CRC32 crc32 = new CRC32();
        crc32.update(b);
        return crc32.getValue();
    }

    public static long crc32(String s) {
        return crc32(s.getBytes());
    }
}
