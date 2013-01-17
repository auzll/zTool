/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import z.tool.entity.interfaces.IEnumName;
import z.tool.entity.interfaces.IExtJsTreeJson;
import z.tool.entity.interfaces.IJson;

import com.google.common.collect.Lists;

public final class JsonUtil {
    private static final JSONObject EXTJS_TREE_ALL = new IExtJsTreeJson() {
        public JSONObject toExtjsTreeJson() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.element("id", 0);
            jsonObject.element("text", "所有");
            jsonObject.element("leaf", true);
            return jsonObject;
        }
    }.toExtjsTreeJson();
    
    private JsonUtil() {}
    
    public static String makeObjectResult(boolean success, IJson<?> item) {
        return makeObjectResult(success, item.toJson());
    }
    
    public static String makeObjectResult(boolean success, JSONObject item) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("success", success);
        jsonObj.element("item", item);
        return jsonObj.toString();
    }
    
    public static String makeMapResult(boolean success, Map<String, ? extends Object> map) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("success", success);
        for (String key : map.keySet()) {
            jsonObj.element(key, map.get(key));
        }
        return jsonObj.toString();
    }
    
    public static String makeArrayResult(int totalCount, List<? extends IJson<?>> array) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("success", true);
        jsonObj.element("totalCount", totalCount);
        
        if (null != array && array.size() > 0) {
            List<JSONObject> wrappedList = Lists.newArrayList();
            for (IJson<?> j : array) {
                wrappedList.add(j.toJson());
            }
            jsonObj.element("items", wrappedList);
        }
        
        return jsonObj.toString();
    }
    
    public static String makeExtjsTreeArrayResult(List<? extends IExtJsTreeJson> array, boolean needHeader) {
        JSONArray jsonArray = new JSONArray();
        
        if (needHeader) {
            jsonArray.add(EXTJS_TREE_ALL);
        }
        
        if (null != array && array.size() > 0) {
            for (IExtJsTreeJson j : array) {
                jsonArray.add(j.toExtjsTreeJson());
            }
        }
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("success", true);
        jsonObj.element("items", jsonArray);
        
        return jsonObj.toString();
    }
    
    public static String makeMapArrayResult(int totalCount, List<Map<String, Object>> array) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.element("success", true);
        jsonObj.element("totalCount", totalCount);
        
        if (null != array && array.size() > 0) {
            List<JSONObject> wrappedList = Lists.newArrayList();
            for (Map<String, Object> map : array) {
                JSONObject subObject = new JSONObject();
                for (String key : map.keySet()) {
                    subObject.put(key, map.get(key));
                }
                wrappedList.add(subObject);
            }
            jsonObj.element("items", wrappedList);
        }
        
        return jsonObj.toString();
    }
    
    public static JSONObject addListIntoJsonObject(JSONObject jsonObject, String key, List<?> list) {
        if (null != key && null != list && list.size() > 0) {
            jsonObject.element(key, list);
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, String value) {
        if (null != key && null != value) {
            jsonObject.element(key, value);
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, long value) {
        if (null != key) {
            jsonObject.element(key, value);
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, int value) {
        if (null != key) {
            jsonObject.element(key, value);
        }
        return jsonObject;
    }
    
    public static JSONObject copyKeyValue(JSONObject jsonObject, String srcKey, String distKey) {
        if (jsonObject.containsKey(srcKey)) {
            jsonObject.element(distKey, jsonObject.get(srcKey));
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, boolean value) {
        if (null != key) {
            jsonObject.element(key, value);
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, double value) {
        if (null != key) {
            jsonObject.element(key, value);
        }
        return jsonObject;
    }
    
    public static JSONObject addEnumEnIntoJsonObject(JSONObject jsonObject, String key, IEnumName value) {
        if (null != key && null != value) {
            jsonObject.element(key, value.getEnName());
        }
        return jsonObject;
    }
    
    public static JSONObject addEnumCnIntoJsonObject(JSONObject jsonObject, String key, IEnumName value) {
        if (null != key && null != value) {
            jsonObject.element(key + "Cn", value.getCnName());
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, IEnumName value) {
        addEnumEnIntoJsonObject(jsonObject, key, value);
        addEnumCnIntoJsonObject(jsonObject, key, value);
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, Date value) {
        if (null != key && null != value) {
            jsonObject.element(key, value.getTime());
        }
        return jsonObject;
    }
    
    public static JSONObject addArrayIntoJsonObject(JSONObject jsonObject, String key, JSONArray array) {
        if (null != key && null != array) {
            jsonObject.element(key, array);
        }
        return jsonObject;
    }
    
    public static JSONObject addObjectIntoJsonObject(JSONObject jsonObject, String key, IJson<?> iJson) {
        if (null != key && null != iJson) {
            return addObjectIntoJsonObject(jsonObject, key, iJson.toJson());
        }
        return jsonObject;
    }
    
    public static JSONObject addObjectIntoJsonObject(JSONObject jsonObject, String key, JSONObject object) {
        if (null != key && null != object) {
            jsonObject.element(key, object);
        }
        return jsonObject;
    }
    
    public static JSONObject addIntoJsonObject(JSONObject jsonObject, String key, List<? extends IJson<?>> values) {
        if (null != key && null != values && values.size() > 0) {
            JSONArray array = new JSONArray();
            for (IJson<?> json : values) {
                array.add(json.toJson());
            }
            jsonObject.element(key, array);
        }
        return jsonObject;
    }
    
    public static String getString(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getString(key);
    }
    
    public static boolean getBoolean(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return false;
        }
        return jsonObject.getBoolean(key);
    }
    
    public static double getDouble(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getDouble(key);
    }
    
    public static int getInt(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getInt(key);
    }
    
    public static long getLong(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return 0;
        }
        return jsonObject.getLong(key);
    }
    
    public static Date getLongAsDate(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return new Date(jsonObject.getLong(key));
    }
    
    public static JSONObject getJSONObject(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getJSONObject(key);
    }
    
    public static JSONArray getJSONArray(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        return jsonObject.getJSONArray(key);
    }
    
    public static List<JSONObject> getJSONObjectList(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        List<JSONObject> list = Lists.newArrayList();
        
        Object unknowObject = jsonObject.get(key);
        if (unknowObject instanceof JSONArray) {
            JSONArray array = JSONArray.class.cast(unknowObject);
            for (int i = 0, max = array.size(); i < max; i++) {
                list.add(array.getJSONObject(i));
            }
        } else if (unknowObject instanceof JSONObject) {
            list.add(JSONObject.class.cast(unknowObject));
        }
        
        return list;
    }
    
    public static List<String> getStringList(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        List<String> list = Lists.newArrayList();
        
        Object unknowObject = jsonObject.get(key);
        if (unknowObject instanceof JSONArray) {
            JSONArray array = JSONArray.class.cast(unknowObject);
            for (int i = 0, max = array.size(); i < max; i++) {
                list.add(array.getString(i));
            }
        } else if (unknowObject instanceof String) {
            list.add(String.class.cast(unknowObject));
        }
        
        return list;
    }
    
    public static <T extends Enum<T>> T getEnum(Class<T> enumType, JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key)) {
            return null;
        }
        
        return EnumUtil.of(enumType, jsonObject.getString(key));
    }
}
