/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Collection;
import java.util.Map;


/**
 * @author auzll@163.com
 */
public final class ZUtils {
    public static boolean isEmpty(Collection<?> collection) {
        return null == collection || 0 == collection.size();
    }
    
    public static <K, V>boolean isEmpty(Map<K, V> map) {
        return null == map || 0 == map.size();
    }
    
    public static boolean isEmpty(Double value) {
        return null == value || value < 1e-6;
    }

    public static boolean isEmpty(Float value) {
        return null == value || value < 1e-6;
    }

    public static boolean isEmpty(Integer value) {
        return null == value || 0 == value;
    }

    public static boolean isEmpty(Long value) {
        return null == value || 0 == value;
    }

    public static boolean isEmpty(Object value) {
        return null == value;
    }

    public static boolean isEmpty(String str) {
        if (null == (str = StringUtil.trim(str))) {
            return true;
        }
        return str.length() == 0;
    }
    
    private ZUtils() {}
}
