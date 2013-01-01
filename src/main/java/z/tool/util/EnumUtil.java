/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import static z.tool.util.ZUtils.isEmpty;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * EnumUtil
 */
public class EnumUtil {
    public static String enum2String(Enum<?> e) {
        return null == e ? null : e.toString();
    }
    
    public static List<String> enumList2StringList(Collection<? extends Enum<?>> collection) {
        if ( isEmpty(collection) ) {
            return null;
        }
        Iterator<? extends Enum<?>> it = collection.iterator();
        List<String> strings = Lists.newArrayList();
        while (it.hasNext()) {
            strings.add(enum2String(it.next()));
        }
        return strings;
    }
    
    public static List<String> enumArray2StringList(Enum<?>[] collection) {
        if ( isEmpty(collection) ) {
            return null;
        }
        List<String> strings = Lists.newArrayList();
        for (Enum<?> e : collection) {
            strings.add(enum2String(e));
        }
        return strings;
    }
    
    public static <T extends Enum<T>> T of(Class<T> enumType, String name) {
        if ( isEmpty(name) ) {
            return null;
        }
        try {
            return Enum.valueOf(enumType, name);
        } catch (Exception e) {
            return null;
        }
    }
}
