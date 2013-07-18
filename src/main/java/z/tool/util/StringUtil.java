/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * 字符串工具类
 * @author auzll@163.com
 * @since 2012-1-27 下午08:09:38
 */
public final class StringUtil {
    private static final Logger LOG = Logger.getLogger(StringUtil.class);
    
    /** UTF-8 */
    public static final String CHARSET_UTF_8 = "UTF-8";
    
    /** GBK */
    public static final String CHARSET_GBK = "GBK";
    
    /**
     * 字节数组按UTF-8编码转字符串
     * @param src 源字节数组
     */
    public static String bytes2GBKString(byte[] src) {
        return bytes2String(src, CHARSET_GBK);
    }
    
    /**
     * 字节数组按指定编码转字符串
     * @param src 源字节数组
     * @param charset 编码
     */
    public static String bytes2String(byte[] src, String charset) {
        if (null == src) {
            throw new NullPointerException("src is null");
        }
        
        if (null == charset) {
            throw new NullPointerException("charset is null");
        }
        
        try {
            return new String(src, charset);
        } catch (UnsupportedEncodingException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:bytes2String", e);
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * 字节数组按UTF-8编码转字符串
     * @param src 源字节数组
     */
    public static String bytes2Utf8String(byte[] src) {
        return bytes2String(src, CHARSET_UTF_8);
    }
    
    /**
     * 把字符串第一个字符变成大写
     */
    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        char first = s.charAt(0);
        char capitalized = Character.toUpperCase(first);
        return (first == capitalized) ? s : capitalized + s.substring(1);
    }
    
    /**
     * 尝试在源字符串末尾删除指定的字符
     * @param src 源字符串
     * @param end 末尾的字符
     */
    public static String deleteEnd(String src, char end) {
        if (null != src && src.length() > 0) {
            int endIndex = src.length() - 1;
            if (end == src.charAt(endIndex)) {
                if (0 == endIndex) {
                    return "";
                } else {
                    return src.substring(0, endIndex);
                }
            }
        }
        
        return src;
    }
    
    /**
     * 尝试在源字符串末尾删除指定的字符串
     * @param src 源字符串
     * @param end 末尾的字符串
     */
    public static String deleteEnd(String src, String end) {
        if (null != src && src.length() > 0 
                && null != end && end.length() > 0 
                && end.length() <= src.length()) {
            if (src.endsWith(end)) {
                if (end.length() == src.length()) {
                    return "";
                } else {
                    return src.substring(0, src.length() - end.length());
                }
            }
        }
        
        return src;
    }
    
    /**
     * 尝试在StringBuilder末尾删除指定的字符
     * @param src 源StringBuilder
     * @param end 末尾的字符
     */
    public static StringBuilder deleteEnd(StringBuilder src, char end) {
        if (null != src && src.length() > 0) {
            if (end == src.charAt(src.length() - 1)) {
                src.deleteCharAt(src.length() - 1);
            }
        }
        return src;
    }
    
    /**
     * 尝试在StringBuilder末尾删除指定的字符串
     * @param src 源StringBuilder
     * @param end 末尾的字符串
     */
    public static StringBuilder deleteEnd(StringBuilder src, String end) {
        if (null != src && src.length() > 0 
                && null != end && end.length() > 0
                && end.length() <= src.length()) {
            int endIndex = src.lastIndexOf(end);
            if (endIndex > 0) {
                return src.delete(endIndex, src.length());
            }
        }
        return src;
    }
    
    /**
     * 对源字符串trimStringNull操作，若结果为null，返回defValue，否则返回结果
     * @param str 源字符串
     * @param defValue
     */
    public static String getNotEmptyString(String str, String defValue) {
        str = StringUtil.trimAndTryReturnNull(str);
        return null != str ? str : defValue;
    }

    /**
     * 把字符串里面大写字母转化为小写
     */
    public static String lower(String src) {
        return null != src ? src.toLowerCase() : null;
    }
    
    /**
     * 字符串按指定编码转字节数组
     * @param src 源字符串
     * @param charset 编码
     */
    public static byte[] string2Bytes(String src, String charset) {
        if (null == src) {
            throw new NullPointerException("src is null");
        }
        
        if (null == charset) {
            throw new NullPointerException("charset is null");
        }
        
        try {
            return src.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:string2Bytes", e);
            }
            throw new RuntimeException(e.getMessage());
        }
    }
    
    /**
     * 字符串按GBK编码转字节数组
     * @param src 源字符串
     */
    public static byte[] string2GbkBytes(String src) {
        return string2Bytes(src, CHARSET_GBK);
    }
    
    /**
     * 字符串按UTF-8编码转字节数组
     * @param src 源字符串
     */
    public static byte[] string2Utf8Bytes(String src) {
        return string2Bytes(src, CHARSET_UTF_8);
    }
    
    /**
     * 删除字符串首尾的空格
     */
    public static String trim(String str) {
        if (null == str) {
            return null;
        }
        return str.trim();
    }
    
    /**
     * 删除字符串首尾的空格。如果字符串中含大写字母，转化为对应的小写。
     */
    public static String trimAndLowerCase(String str) {
        return lower(trim(str));
    }
    
    /**
     * 删除字符串首尾的空格。若最后为空字符串，返回null。
     */
    public static String trimAndTryReturnNull(String str) {
        if (null == str) {
            return str;
        }
        str = str.trim();
        return str.length() > 0 ? str : null;
    }
    
    /**
     * 把字符串里面小写字母转化为大写
     */
    public static String upper(String src) {
        return null != src ? src.toUpperCase() : null;
    }
    
    public static final int stringToInt(String src) {
        src = StringUtil.trimAndTryReturnNull(src);
        try {
            return null != src ? Integer.valueOf(src) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private StringUtil() {}
}
