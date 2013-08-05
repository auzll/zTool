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
