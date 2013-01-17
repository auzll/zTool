/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import z.tool.entity.interfaces.IKey;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author auzll
 *
 */
public final class CollectionUtil {
    private CollectionUtil() {}
    
    @SuppressWarnings("unchecked")
    public static <K,V extends IKey<K>> Map<K, V> makeIKeyDataAsMap(List<? extends IKey<K>> list) {
        if (ZUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }
        
        Map<K, V> map = Maps.newHashMap();
        for (IKey<K> key : list) {
            map.put(key.getKey(), (V)key);
        }
        
        return map;
    }
    
    public static <K> List<K> extractIKeyAsList(List<? extends IKey<K>> list) {
        if (ZUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        
        List<K> newList = Lists.newArrayList();
        for (IKey<K> key : list) {
            newList.add(key.getKey());
        }
        
        return newList;
    }
    
    public static <T>T first(Collection<T> collection) {
        if (ZUtils.isEmpty(collection)) {
            return null;
        }
        Iterator<T> it = collection.iterator();
        return it.next();
    }
    
    public static <E>List<E> collectionAsList(Collection<E> list) {
        if (ZUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        
        if (list instanceof List) {
            return (List<E>) list;
        }
        
        List<E> target = Lists.newArrayList();
        for (E e : list) {
            target.add(e);
        }
        
        return target;
    }
}
