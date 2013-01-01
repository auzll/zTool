/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;

public final class PropertyUtil {
    private PropertyUtil() {}
    
    public static String getValue(Properties props, String key) {
        String value = props.getProperty(key);
        if (null != value) {
            value = value.trim();
            if (value.length() < 1) {
                value = null;
            }
        }
        return value;
    }
    
    public static String getNotNullValue(Properties props, String key) {
        return checkNotNull(getValue(props, key), "[" + key + "] is null");
    }
    
    public static boolean getNotNullBooleanValue(Properties props, String key) {
        String value = getNotNullValue(props, key);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("the value[" + value + "] for key[" + key + "] is error");
        }
    }
    
    public static List<Long> getNotNullLongValues(Properties props, String key) {
        String value = getNotNullValue(props, key);
        try {
            String[] values = value.split(",");
            List<Long> list = Lists.newArrayList();
            for (String v : values) {
                list.add(Long.valueOf(v));
            }
            return list;
        } catch (Exception e) {
            throw new IllegalArgumentException("the value[" + value + "] for key[" + key + "] is error");
        }
    }
    
    public static String getFilePath(Properties props, String key) {
        String value = getNotNullValue(props, key);
        if (value.endsWith(File.separator)) {
            return value;
        }
        return value + File.separator;
    }
}
