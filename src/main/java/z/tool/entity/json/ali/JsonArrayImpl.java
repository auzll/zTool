/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.json.ali;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

import z.tool.entity.json.JsonArray;
import z.tool.entity.json.JsonObject;

import com.alibaba.fastjson.JSONArray;

/**
 * @author auzll
 *
 */
public final class JsonArrayImpl implements JsonArray {
    private JSONArray jsonArray;
    
    private JSONArray getOrNew() {
        if (null == jsonArray) {
            jsonArray = new JSONArray();
        }
        return jsonArray;
    }
    
    JSONArray getJSONArray() {
        return jsonArray;
    }
    
    public static JsonArray parseArray(String text) {
        return from(JSONArray.parseArray(text));
    }
    
    public static JsonArray from(JSONArray jsonArray) {
        if (null == jsonArray) {
            return null;
        }
        
        JsonArrayImpl impl = new JsonArrayImpl();
        Iterator<Object> it = jsonArray.iterator();
        while (it.hasNext()) {
            // value 可能是JSONObject,JSONArray等
            impl.add(it.next());
        }
        
        return impl;
    }

    public int size() {
        if (null == jsonArray) {
            return 0;
        }
        return jsonArray.size();
    }

    public JsonArray add(Object e) {
        if (null != e) {
            getOrNew().add(InternalTool.interface2Ali(e));
        }
        return this;
    }

    public JsonArray remove(Object o) {
        if (null != jsonArray) {
            jsonArray.remove(o);
        }
        return this;
    }

    public JsonArray addAll(Collection<? extends Object> c) {
        if (null != c && c.size() > 0) {
            for (Object e : c) {
                getOrNew().add(InternalTool.interface2Ali(e));
            }
        }
        return this;
    }

    public JsonArray removeAll(Collection<?> c) {
        if (null != jsonArray) {
            jsonArray.removeAll(c);
        }
        return this;
    }

    public Object get(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return InternalTool.ali2Interface(jsonArray.get(index));
    }

    public JsonObject getJsonObject(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return JsonObjectImpl.from(jsonArray.getJSONObject(index));
    }

    public JsonArray getJsonArray(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return from(jsonArray.getJSONArray(index));
    }
    
    public String toJsonString() {
        if (null == jsonArray) {
            return "[]";
        }
        return jsonArray.toJSONString();
    }

    public String toString() {
        if (null == jsonArray) {
            return "[]";
        }
        return jsonArray.toString();
    }
    
    public boolean equals(Object obj) {
        if (null == jsonArray) {
            return null == obj;
        } else {
            return null != obj && jsonArray.equals(obj);
        }
    }
    
    public int hashCode() {
        return null != jsonArray ? jsonArray.hashCode() : 0;
    }

    @Override
    public Boolean getBoolean(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getBoolean(index);
    }

    @Override
    public boolean getBooleanValue(int index) {
        if (null == jsonArray || index < 0) {
            return false;
        }
        return jsonArray.getBooleanValue(index);
    }

    public Byte getByte(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getByte(index);
    }

    public byte getByteValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getByteValue(index);
    }

    public Short getShort(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getShort(index);
    }

    public short getShortValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getShortValue(index);
    }

    public Integer getInteger(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getInteger(index);
    }

    public int getIntValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getIntValue(index);
    }

    public Long getLong(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getLong(index);
    }

    public long getLongValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getLongValue(index);
    }

    public Float getFloat(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getFloat(index);
    }

    public float getFloatValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getFloatValue(index);
    }

    public Double getDouble(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getDouble(index);
    }

    public double getDoubleValue(int index) {
        if (null == jsonArray || index < 0) {
            return 0;
        }
        return jsonArray.getDoubleValue(index);
    }

    public BigDecimal getBigDecimal(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getBigDecimal(index);
    }

    public BigInteger getBigInteger(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getBigInteger(index);
    }

    public String getString(int index) {
        if (null == jsonArray || index < 0) {
            return null;
        }
        return jsonArray.getString(index);
    }
    
    
}
