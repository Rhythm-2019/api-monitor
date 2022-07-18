package org.mdnote.apiMonitor.util;

import java.nio.ByteBuffer;

public class ByteUtil {

    public static byte[] toBytes(long l) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putLong(l);
        return buf.array();
    }

    public static long toLong(byte[] b) {
        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.put(b);
        return buf.getLong();
    }
}
