/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.json.ali;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import z.tool.entity.json.JsonArray;
import z.tool.entity.json.JsonObject;

import com.alibaba.fastjson.JSONObject;

/**
 * @author auzll
 *
 */
public final class JsonObjectImpl implements JsonObject {
    
    private JSONObject jsonObject;
    
    private JSONObject getOrNew() {
        if (null == jsonObject) {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }
    
    JSONObject getJSONObject() {
        return jsonObject;
    }
    
    public static JsonObject parseObject(String text) {
        return from(JSONObject.parseObject(text));
    }
    
    public static JsonObject from(JSONObject jsonObject) {
        if (null == jsonObject) {
            return null;
        }
        JsonObjectImpl impl = new JsonObjectImpl();
        
        for (String key : jsonObject.keySet()) {
            // value 可能是JSONObject,JSONArray等
            Object value = jsonObject.get(key);
            impl.put(key, value);
        }
        
        return impl;
    }

    public boolean containsKey(Object key) {
        if (null == key || null == jsonObject) {
            return false;
        }
        return jsonObject.containsKey(key);
    }

    public Object get(Object key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return InternalTool.ali2Interface(jsonObject.get(key));
    }

    public JsonObject getJsonObject(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return from(jsonObject.getJSONObject(key));
    }

    public JsonArray getJsonArray(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return JsonArrayImpl.from(jsonObject.getJSONArray(key));
    }

    public Boolean getBoolean(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getBoolean(key);
    }

    public boolean getBooleanValue(String key) {
        if (null == key || null == jsonObject) {
            return false;
        }
        return jsonObject.getBooleanValue(key);
    }

    public Byte getByte(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getByte(key);
    }

    public byte getByteValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getByteValue(key);
    }

    public Short getShort(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getShort(key);
    }

    public short getShortValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getShortValue(key);
    }

    public Integer getInteger(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getInteger(key);
    }

    public int getIntValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getIntValue(key);
    }

    public Long getLong(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getLong(key);
    }

    public long getLongValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getLongValue(key);
    }

    public Float getFloat(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getFloat(key);
    }

    public float getFloatValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getFloatValue(key);
    }

    public Double getDouble(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getDouble(key);
    }

    public double getDoubleValue(String key) {
        if (null == key || null == jsonObject) {
            return 0;
        }
        return jsonObject.getDoubleValue(key);
    }

    public BigDecimal getBigDecimal(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getBigDecimal(key);
    }

    public BigInteger getBigInteger(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getBigInteger(key);
    }

    public String getString(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getString(key);
    }

    public Date getDate(String key) {
        if (null == key || null == jsonObject) {
            return null;
        }
        return jsonObject.getDate(key);
    }

    public JsonObject put(String key, Object value) {
        getOrNew().put(key, InternalTool.interface2Ali(value));
        return this;
    }
    
    public JsonObject putId(String key, long value) {
        if (value > 0 && null != key) {
            getOrNew().put(key, value);
        }
        return this;
    }
    
    public JsonObject putId(String key, int value) {
        if (value > 0 && null != key) {
            getOrNew().put(key, value);
        }
        return this;
    }
    
    public JsonObject put(String key, Date value) {
        if (null != value && null != key) {
            getOrNew().put(key, value.getTime());
        }
        return this;
    }

    public Set<String> keySet() {
        if (null == jsonObject) {
            return null;
        }
        return jsonObject.keySet();
    }
    
    public String toJsonString() {
        if (null == jsonObject) {
            return "{}";
        }
        return jsonObject.toJSONString();
    }

    @Override
    public String toString() {
        if (null == jsonObject) {
            return "{}";
        }
        return jsonObject.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (null == jsonObject) {
            return null == obj;
        } else {
            return null != obj && jsonObject.equals(obj);
        }
    }
    
    @Override
    public int hashCode() {
        return null != jsonObject ? jsonObject.hashCode() : 0;
    }
}
