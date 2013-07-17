/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.json;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

/**
 * @author auzll
 *
 */
public interface JsonArray {
    int size();
    
    JsonArray add(Object e);
    
    JsonArray remove(Object o);
    
    JsonArray addAll(Collection<? extends Object> c);
    
    JsonArray removeAll(Collection<?> c);
    
    Object get(int index);
    
    JsonObject getJsonObject(int index);
    
    JsonArray getJsonArray(int index);
    
    String toJsonString();
    
    public Boolean getBoolean(int index);

    public boolean getBooleanValue(int index);

    public Byte getByte(int index);

    public byte getByteValue(int index);

    public Short getShort(int index);

    public short getShortValue(int index);

    public Integer getInteger(int index);

    public int getIntValue(int index);

    public Long getLong(int index);
    
    public long getLongValue(int index);

    public Float getFloat(int index);

    public float getFloatValue(int index);

    public Double getDouble(int index);

    public double getDoubleValue(int index);

    public BigDecimal getBigDecimal(int index);

    public BigInteger getBigInteger(int index);

    public String getString(int index);
}
