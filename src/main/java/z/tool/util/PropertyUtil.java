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

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.util.List;
import java.util.Properties;

import com.google.common.collect.Lists;

public final class PropertyUtil {
    private PropertyUtil() {}
    
    public static String getValue(Properties props, String key) {
        String value = props.getProperty(key);
        if (null != value) {
            value = value.trim();
            if (value.length() < 1) {
                value = null;
            }
        }
        return value;
    }
    
    public static String getNotNullValue(Properties props, String key) {
        return checkNotNull(getValue(props, key), "[" + key + "] is null");
    }
    
    public static boolean getNotNullBooleanValue(Properties props, String key) {
        String value = getNotNullValue(props, key);
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            throw new IllegalArgumentException("the value[" + value + "] for key[" + key + "] is error");
        }
    }
    
    public static List<Long> getNotNullLongValues(Properties props, String key) {
        String value = getNotNullValue(props, key);
        try {
            String[] values = value.split(",");
            List<Long> list = Lists.newArrayList();
            for (String v : values) {
                list.add(Long.valueOf(v));
            }
            return list;
        } catch (Exception e) {
            throw new IllegalArgumentException("the value[" + value + "] for key[" + key + "] is error");
        }
    }
    
    public static String getFilePath(Properties props, String key) {
        String value = getNotNullValue(props, key);
        if (value.endsWith(File.separator)) {
            return value;
        }
        return value + File.separator;
    }
}
