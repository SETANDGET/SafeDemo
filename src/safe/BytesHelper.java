package safe;

import java.nio.charset.Charset;
import java.util.Random;


/**
 * 实现 String 和byte[] 的多种相互转化方式
 */
public class BytesHelper {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();


    public static byte[] toBytes(String source) {
        return source.getBytes(Charset.forName("UTF-8"));
    }

    public static String fromBytes(byte[] source) {
        return new String(source, Charset.forName("UTF-8"));
    }

    public static byte[] fromBase64(String source) {
        return Base64.decode(source, Base64.URL_SAFE);
    }

    public static String toBase64(byte[] source) {
        return Base64.encodeToString(source, Base64.URL_SAFE);
    }

    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] fromHex(String source) {
        char[] data = source.toCharArray();
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }

        byte[] out = new byte[len >> 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);
        }

        return out;
    }

    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    static Random random = new Random();

    public static byte[] genRandomBytes(int len) {
        byte[] result = new byte[len];
        random.nextBytes(result);
        return result;
    }
}
