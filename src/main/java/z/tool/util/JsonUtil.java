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

import java.util.Date;
import java.util.List;
import java.util.Map;

import z.tool.entity.interfaces.IEnumName;
import z.tool.entity.json.ExtJsJsonable;
import z.tool.entity.json.JsonArray;
import z.tool.entity.json.JsonObject;
import z.tool.entity.json.Jsonable;
import z.tool.entity.json.Jsons;

import com.google.common.collect.Lists;

public final class JsonUtil {
    private static final JsonObject EXTJS_TREE_ALL = new ExtJsJsonable() {
        public JsonObject toExtjsTreeJson() {
            return Jsons.newJsonObject()
                .put("id", 0)
                .put("text", "所有")
                .put("leaf", true);
        }
    }.toExtjsTreeJson();
    
    private JsonUtil() {}
    
    public static String makeObjectResult(boolean success, Jsonable<?> item) {
        return makeObjectResult(success, item.toJson());
    }
    
    public static String makeObjectResult(boolean success, JsonObject item) {
        JsonObject jsonObj = Jsons.newJsonObject();
        jsonObj.put("success", success);
        jsonObj.put("item", item);
        return jsonObj.toString();
    }
    
    public static String makeMapResult(boolean success, Map<String, ? extends Object> map) {
        JsonObject jsonObj = Jsons.newJsonObject();
        jsonObj.put("success", success);
        for (String key : map.keySet()) {
            jsonObj.put(key, map.get(key));
        }
        return jsonObj.toString();
    }
    
    public static String makeArrayResult(int totalCount, List<? extends Jsonable<?>> array) {
        JsonObject jsonObj = Jsons.newJsonObject();
        jsonObj.put("success", true);
        jsonObj.put("totalCount", totalCount);
        
        if (null != array && array.size() > 0) {
            List<JsonObject> wrappedList = Lists.newArrayList();
            for (Jsonable<?> j : array) {
                wrappedList.add(j.toJson());
            }
            jsonObj.put("items", wrappedList);
        }
        
        return jsonObj.toString();
    }
    
    public static String makeExtjsTreeArrayResult(List<? extends ExtJsJsonable> array, boolean needHeader) {
        JsonArray jsonArray = Jsons.newJsonArray();
        
        if (needHeader) {
            jsonArray.add(EXTJS_TREE_ALL);
        }
        
        if (null != array && array.size() > 0) {
            for (ExtJsJsonable j : array) {
                jsonArray.add(j.toExtjsTreeJson());
            }
        }
        
        JsonObject jsonObj = Jsons.newJsonObject();
        jsonObj.put("success", true);
        jsonObj.put("items", jsonArray);
        
        return jsonObj.toString();
    }
    
    public static String makeMapArrayResult(int totalCount, List<Map<String, Object>> array) {
        JsonObject jsonObj = Jsons.newJsonObject();
        jsonObj.put("success", true);
        jsonObj.put("totalCount", totalCount);
        
        if (null != array && array.size() > 0) {
            List<JsonObject> wrappedList = Lists.newArrayList();
            for (Map<String, Object> map : array) {
                JsonObject subObject = Jsons.newJsonObject();
                for (String key : map.keySet()) {
                    subObject.put(key, map.get(key));
                }
                wrappedList.add(subObject);
            }
            jsonObj.put("items", wrappedList);
        }
        
        return jsonObj.toString();
    }
    
    public static JsonObject addListIntoJsonObject(JsonObject jsonObject, String key, List<?> list) {
        if (null != key && null != list && list.size() > 0) {
            jsonObject.put(key, list);
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, String value) {
        if (null != key && null != value) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addIdIntoJsonObject(JsonObject jsonObject, String key, long value) {
        if (null != key && value > 0) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addIdIntoJsonObject(JsonObject jsonObject, String key, int value) {
        if (null != key && value > 0) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, long value) {
        if (null != key) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, int value) {
        if (null != key) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject copyKeyValue(JsonObject jsonObject, String srcKey, String distKey) {
        if (jsonObject.containsKey(srcKey)) {
            jsonObject.put(distKey, jsonObject.get(srcKey));
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, boolean value) {
        if (null != key) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, double value) {
        if (null != key) {
            jsonObject.put(key, value);
        }
        return jsonObject;
    }
    
    public static JsonObject addEnumEnIntoJsonObject(JsonObject jsonObject, String key, IEnumName value) {
        if (null != key && null != value) {
            jsonObject.put(key, value.getEnName());
        }
        return jsonObject;
    }
    
    public static JsonObject addEnumCnIntoJsonObject(JsonObject jsonObject, String key, IEnumName value) {
        if (null != key && null != value) {
            jsonObject.put(key + "Cn", value.getCnName());
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, IEnumName value) {
        addEnumEnIntoJsonObject(jsonObject, key, value);
        addEnumCnIntoJsonObject(jsonObject, key, value);
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, Date value) {
        if (null != key && null != value) {
            jsonObject.put(key, value.getTime());
        }
        return jsonObject;
    }
    
    public static JsonObject addArrayIntoJsonObject(JsonObject jsonObject, String key, JsonArray array) {
        if (null != key && null != array) {
            jsonObject.put(key, array);
        }
        return jsonObject;
    }
    
    public static JsonObject addObjectIntoJsonObject(JsonObject jsonObject, String key, Jsonable<?> iJson) {
        if (null != key && null != iJson) {
            return addObjectIntoJsonObject(jsonObject, key, iJson.toJson());
        }
        return jsonObject;
    }
    
    public static JsonObject addObjectIntoJsonObject(JsonObject jsonObject, String key, JsonObject object) {
        if (null != key && null != object) {
            jsonObject.put(key, object);
        }
        return jsonObject;
    }
    
    public static JsonObject addIntoJsonObject(JsonObject jsonObject, String key, List<? extends Jsonable<?>> values) {
        if (null != key && null != values && values.size() > 0) {
            JsonArray array = Jsons.newJsonArray();
            for (Jsonable<?> json : values) {
                array.add(json.toJson());
            }
            jsonObject.put(key, array);
        }
        return jsonObject;
    }
    
    public static String getString(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getString(key);
    }
    
    public static boolean getBoolean(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return false;
        }
        return jsonObject.getBoolean(key);
    }
    
    public static double getDouble(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getDouble(key);
    }
    
    public static int getInt(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getIntValue(key);
    }
    
    public static long getLong(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getLong(key);
    }
    
    public static Date getLongAsDate(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return new Date(jsonObject.getLong(key));
    }
    
    public static JsonObject getJsonObject(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getJsonObject(key);
    }
    
    public static JsonArray getJsonArray(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getJsonArray(key);
    }
    
    public static List<JsonObject> getJsonObjectList(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        List<JsonObject> list = Lists.newArrayList();
        
        Object unknowObject = jsonObject.get(key);
        if (unknowObject instanceof JsonArray) {
            JsonArray array = JsonArray.class.cast(unknowObject);
            for (int i = 0, max = array.size(); i < max; i++) {
                list.add(array.getJsonObject(i));
            }
        } else if (unknowObject instanceof JsonObject) {
            list.add(JsonObject.class.cast(unknowObject));
        }
        
        return list;
    }
    
    public static List<String> getStringList(JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        List<String> list = Lists.newArrayList();
        
        Object unknowObject = jsonObject.get(key);
        if (unknowObject instanceof JsonArray) {
            JsonArray array = JsonArray.class.cast(unknowObject);
            for (int i = 0, max = array.size(); i < max; i++) {
                list.add(array.getString(i));
            }
        } else if (unknowObject instanceof String) {
            list.add(String.class.cast(unknowObject));
        }
        
        return list;
    }
    
    public static <T extends Enum<T>> T getEnum(Class<T> enumType, JsonObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        return EnumUtil.of(enumType, jsonObject.getString(key));
    }
}
