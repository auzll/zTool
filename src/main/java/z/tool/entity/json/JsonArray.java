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
