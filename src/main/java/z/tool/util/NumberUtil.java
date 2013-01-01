/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Collection;

/**
 * 数值工具类
 * @author auzll@163.com
 * @since 2012-1-27 下午08:13:00
 */
public final class NumberUtil {
    /**
     * 把字符串转化为double，转换之前会先trim字符串
     */
    public static double parseBaseDouble(String value) {
        Double newValue = parseDouble(value);
        return null != newValue ? newValue.doubleValue() : 0;
    }
    
    /**
     * 把字符串转化为float，转换之前会先trim字符串
     */
    public static float parseBaseFloat(String value) {
        Float newValue = parseFloat(value);
        return null != newValue ? newValue.floatValue() : 0;
    }
    
    /**
     * 把字符串转化为long，转换之前会先trim字符串
     */
    public static long parseBaseLong(String value) {
        Long newValue = parseLong(value);
        return null != newValue ? newValue.longValue() : 0;
    }
    
    /**
     * 把字符串转化为{@link Double}，转换之前会先trim字符串
     */
    public static Double parseDouble(String value) {
        value = StringUtil.trimAndTryReturnNull(value);
        if (null != value) {
            try {
                return Double.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
    
    /**
     * 把字符串转化为{@link Float}，转换之前会先trim字符串
     */
    public static Float parseFloat(String value) {
        value = StringUtil.trimAndTryReturnNull(value);
        if (null != value) {
            try {
                return Float.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
    
    /**
     * 把字符串转化为int，转换之前会先trim字符串
     */
    public static int parseInt(String value) {
        Integer newValue = parseInteger(value);
        return null != newValue ? newValue.intValue() : 0;
    }
    
    /**
     * 把字符串转化为{@link Integer}，转换之前会先trim字符串
     */
    public static Integer parseInteger(String value) {
        value = StringUtil.trimAndTryReturnNull(value);
        if (null != value) {
            try {
                return Integer.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
    
    /**
     * 把字符串转化为{@link Long}，转换之前会先trim字符串
     */
    public static Long parseLong(String value) {
        value = StringUtil.trimAndTryReturnNull(value);
        if (null != value) {
            try {
                return Long.valueOf(value);
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }
    
    /**
     * 把{@link Byte}数组转化为基本类型byte的数组
     */
    public static byte[] unwrap(Byte[] src) {
        if (null == src) {
            return null;
        }
        byte[] dest = new byte[src.length];
        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i];
        }
        return dest;
    }
    
    /**
     * 把{@link Collection}<{@link Byte}>转化为基本类型byte的数组
     */
    public static byte[] unwrap(Collection<Byte> src) {
        if (null == src) {
            return null;
        }
        byte[] dest = new byte[src.size()];
        int index = 0;
        for (Byte b : src) {
            dest[index++] = b.byteValue();
        }
        return dest;
    }
    
    private NumberUtil() {}
}
