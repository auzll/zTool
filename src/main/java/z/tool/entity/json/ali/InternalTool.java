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
package z.tool.entity.json.ali;

import java.util.Iterator;
import java.util.Map;

import z.tool.entity.json.Jsonable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author auzll
 *
 */
final class InternalTool {
    public static Object ali2Interface(Object value) {
        if (null == value) {
            return null;
        }
        
        if (value instanceof JSONObject) {
            return JsonObjectImpl.from((JSONObject) value);
        } else if (value instanceof JSONArray) {
            return JsonArrayImpl.from((JSONArray) value);
        } else {
            return value;
        }
    }
    
    public static Object interface2Ali(Object value) {
        if (null == value) {
            return null;
        }
        
        if (value instanceof JsonObjectImpl) {
            return JsonObjectImpl.class.cast(value).getJSONObject();
        } else if (value instanceof JsonArrayImpl) {
            return JsonArrayImpl.class.cast(value).getJSONArray();
        } else if (value instanceof Jsonable) {
            return interface2Ali(Jsonable.class.cast(value).toJson());
        } else if (value instanceof Iterable) {
            Iterator<?> it = Iterable.class.cast(value).iterator();
            JSONArray array = new JSONArray();
            while (it.hasNext()) {
                array.add(interface2Ali(it.next()));
            }
            return array;
        } else if (value instanceof Map) {
            Map<?, ?> map = Map.class.cast(value);
            JSONObject jsonObject = new JSONObject();
            for (Object key : map.keySet()) {
                jsonObject.put(key.toString(), interface2Ali(map.get(key)));
            }
            return jsonObject;
        } else if (value instanceof Object[]) {
            Object[] objArray = Object[].class.cast(value);
            JSONArray array = new JSONArray();
            for (Object obj : objArray) {
                array.add(interface2Ali(obj));
            }
            return array;
        } else {
            return value;
        }
    }
}
