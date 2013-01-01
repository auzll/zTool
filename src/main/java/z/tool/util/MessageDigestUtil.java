/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.security.MessageDigest;

/**
 * @author auzll@163.com
 * @since 2012-1-27 下午09:02:39
 */
public final class MessageDigestUtil {
    public static String md5(String src) {
        return md5(src, StringUtil.CHARSET_UTF_8);
    }
    
    public static String md5(String src, String charsetName) {
        try {
            return bytesToHex(md5Bytes(src, charsetName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static byte[] md5Bytes(String src, String charsetName) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            return md5.digest(StringUtil.string2Bytes(src, charsetName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String sha1(String src) {
        return sha1(src, StringUtil.CHARSET_UTF_8);
    }
    
    public static String sha1(String src, String charsetName) {
        try {
            return bytesToHex(sha1Bytes(src, charsetName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] sha1Bytes(String src, String charsetName) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            return digest.digest(StringUtil.string2Bytes(src, charsetName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder buff = new StringBuilder();
        for (byte b: bytes) {
            buff.append(Integer.toHexString(0xf & (b >> 4))).append(Integer.toHexString(0xf & b));
        }
        return buff.toString();
    }
    
    private MessageDigestUtil() {}
}
