/**
 * https://github.com/auzll/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package z.tool.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import z.tool.checker.Checker;
import z.tool.entity.interfaces.IKey;
import z.tool.entity.interfaces.IParent;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
    
    public static <E>List<E> convertTreeList(List<? extends IParent<E>> sourceList) {
        if (ZUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        
        List<E> treeList = Lists.newArrayList();
        
        // tree map
        Map<Long, IParent<E>> tMap = Maps.newTreeMap();
        for (IParent<E> a : sourceList) {
            tMap.put(a.getId(), a);
        }
        
        for (IParent<E> r : tMap.values()) {
            @SuppressWarnings("unchecked")
            E e = (E) r; 
            if (r.getParentId() > 0) {
                tMap.get(r.getParentId()).addChild(e);
            } else {
                treeList.add(e);
            }
        }
        
        return treeList;
    }
    
    public static <E>void checkDeadLock(List<? extends IParent<E>> sourceList, IParent<E> tobeCheck) {
        if (tobeCheck.getParentId() < 1 || tobeCheck.getId() < 1) {
            // 未设置，不用检查
            return;
        }
        
        Checker.checkLogic(tobeCheck.getId() != tobeCheck.getParentId(), 
                "归属关系设置有误(父子节点相同)");
        
        Map<Long, IParent<E>> tMap = Maps.newTreeMap();
        for (IParent<E> a : sourceList) {
            tMap.put(a.getId(), a);
        }
        
        IParent<E> me = tMap.get(tobeCheck.getId());
        Checker.checkLogic(null != me, 
            "归属关系设置有误(当前节点不存在)");
        
        for (IParent<E> r : tMap.values()) {
            @SuppressWarnings("unchecked")
            E e = (E) r; 
            if (r.getParentId() > 0) {
                tMap.get(r.getParentId()).addChild(e);
            }
        }
        
        if (ZUtils.isEmpty(me.getChildren())) {
            // 没有子节点，不用检查
            return;
        }
        
        Set<Long> allSubIds = Sets.newHashSet();
        Queue<IParent<E>> queue = new LinkedList<IParent<E>>();
        queue.add(me);
        do {
            IParent<E> elem = queue.poll();
            if (!ZUtils.isEmpty(elem.getChildren())) {
                queue.addAll(elem.getChildren());
                for (IParent<E> e : elem.getChildren()) {
                    allSubIds.add(e.getId());
                }
            }
        } while (!queue.isEmpty());
        
        Checker.checkLogic(!allSubIds.contains(tobeCheck.getParentId()), 
                "归属关系设置有误(有死循环)");
    }
}
