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

import org.apache.commons.lang3.time.FastDateFormat;

import z.tool.entity.interfaces.Builder;

/**
 * toString()工具类
 */
public final class ToStringBuilder implements Builder<String> {
    private final char SEP = ',';
    private FastDateFormat format;
    private StringBuilder buff;
    
    private FastDateFormat getAndTryInitFormat() {
        if (null == format) {
            format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        }
        return format;
    }
    
    public StringBuilder getOrTryInitBuff() {
        if (null == buff) {
            buff = new StringBuilder();
        }
        return buff;
    }
    
    public ToStringBuilder add(String key, Object value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, String value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Date value) {
        if (null != key && null != value) {
            return add(key, getAndTryInitFormat().format(value));
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Long value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.longValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Integer value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.intValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Short value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.shortValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Byte value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.byteValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Double value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.doubleValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Float value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.floatValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Boolean value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.booleanValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, Character value) {
        if (null != key && null != value) {
            getOrTryInitBuff().append(key).append(":").append(value.charValue()).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder addId(String key, long value) {
        if (null != key && value > 0) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, long value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, int value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, short value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, byte value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, double value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, float value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, boolean value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }
    
    public ToStringBuilder add(String key, char value) {
        if (null != key) {
            getOrTryInitBuff().append(key).append(":").append(value).append(SEP);
        }
        return this;
    }

    public String build() {
        if (null != buff) {
            return buff.insert(0, '{').deleteCharAt(buff.length() - 1)
                    .append('}').toString();
        }
        return null;
    }
    
    public String plain() {
        if (null != buff) {
            return buff.deleteCharAt(buff.length() - 1).toString();
        }
        return null;
    }

}
