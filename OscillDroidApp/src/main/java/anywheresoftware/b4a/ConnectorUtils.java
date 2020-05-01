package anywheresoftware.b4a;

import android.graphics.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ConnectorUtils {
    public static final byte BOOL = 5;
    public static final byte CACHED_STRING = 9;
    public static final byte COLOR = 6;
    public static final byte ENDOFMAP = 4;
    public static final byte FLOAT = 7;
    public static final byte INT = 1;
    public static final byte MAP = 3;
    public static final byte SCALED_INT = 8;
    public static final byte STRING = 2;
    private static Charset charset = Charset.forName("UTF8");
    private static ThreadLocal<ByteBuffer> myBb = new ThreadLocal<ByteBuffer>() {
        /* access modifiers changed from: protected */
        public ByteBuffer initialValue() {
            ByteBuffer bbW = ByteBuffer.allocate(51200);
            bbW.order(ByteOrder.LITTLE_ENDIAN);
            return bbW;
        }
    };

    public static ByteBuffer startMessage(byte message) {
        ByteBuffer bbW = myBb.get();
        bbW.clear();
        bbW.put(message);
        return bbW;
    }

    public static void sendMessage(ConnectorConsumer consumer) {
        byte[] b;
        if (consumer != null) {
            ByteBuffer bbW = myBb.get();
            bbW.flip();
            if (consumer.shouldAddPrefix()) {
                b = new byte[(bbW.limit() + 4)];
                int size = b.length - 4;
                bbW.get(b, 4, size);
                for (int i = 0; i <= 3; i++) {
                    b[i] = (byte) (size & 255);
                    size >>= 8;
                }
            } else {
                b = new byte[bbW.limit()];
                bbW.get(b);
            }
            consumer.putTask(b);
        }
    }

    public static void writeInt(int i) {
        myBb.get().putInt(i);
    }

    public static void writeFloat(float f) {
        myBb.get().putFloat(f);
    }

    public static void mark() {
        myBb.get().mark();
    }

    public static void resetToMark() {
        myBb.get().reset();
    }

    public static boolean writeString(String str) {
        if (str == null) {
            str = "";
        }
        if (str.length() > 700) {
            str = String.valueOf(str.substring(0, 699)) + "......";
        }
        ByteBuffer bbW = myBb.get();
        int pos = bbW.position();
        ByteBuffer bb = charset.encode(str);
        if (bbW.remaining() - bb.remaining() < 1000) {
            return false;
        }
        bbW.putInt(0);
        bbW.put(bb);
        bbW.putInt(pos, (bbW.position() - pos) - 4);
        return true;
    }

    public static int readInt(DataInputStream in) throws IOException {
        return Integer.reverseBytes(in.readInt());
    }

    public static String readString(DataInputStream in) throws IOException {
        int size = readInt(in);
        ByteBuffer bb = ByteBuffer.allocate(size);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        in.readFully(bb.array());
        bb.limit(size);
        return charset.decode(bb).toString();
    }

    private static String readCacheString(DataInputStream din, String[] cache) throws IOException {
        if (cache == null) {
            return readString(din);
        }
        return cache[readInt(din)];
    }

    public static HashMap<String, Object> readMap(DataInputStream in, String[] cache) throws IOException {
        byte b;
        Object value;
        HashMap<String, Object> props = new HashMap<>();
        while (true) {
            String key = readCacheString(in, cache);
            b = in.readByte();
            if (b != 1) {
                if (b != 9) {
                    if (b != 2) {
                        if (b != 7) {
                            if (b != 3) {
                                if (b != 5) {
                                    if (b != 6) {
                                        break;
                                    }
                                    value = Integer.valueOf(Color.argb(in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte(), in.readUnsignedByte()));
                                } else {
                                    value = Boolean.valueOf(in.readByte() == 1);
                                }
                            } else {
                                value = readMap(in, cache);
                            }
                        } else {
                            value = Float.valueOf(Float.intBitsToFloat(readInt(in)));
                        }
                    } else {
                        value = readString(in);
                    }
                } else {
                    value = readCacheString(in, cache);
                }
            } else {
                value = Integer.valueOf(readInt(in));
            }
            props.put(key, value);
        }
        if (b == 4) {
            return props;
        }
        throw new RuntimeException("unknown type");
    }
}
