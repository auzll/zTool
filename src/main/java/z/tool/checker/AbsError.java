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
package z.tool.checker;

import java.util.Map;

import z.tool.entity.interfaces.ErrorTip;

import com.google.common.collect.Maps;

class AbsError extends RuntimeException {
    
    /** */
    private static final long serialVersionUID = 1384838746221312014L;
    
    private Map<Object, Object> attachMap;
    
    private ErrorTip errorTip;
    
    private Map<Object, Object> newOrGetAttachMap() {
        if (null == attachMap) {
            attachMap = Maps.newHashMap();
        }
        return attachMap;
    }
    
    public AbsError attach(Object key, Object value) {
        if (null == key || null == value) {
            throw new IllegalArgumentException("key or value is null");
        }
        newOrGetAttachMap().put(key, value);
        return this;
    }
    
    public Object getAttach(Object key) {
        if (null == key) {
            throw new IllegalArgumentException("key is null");
        }
        if (null == attachMap) {
            return null;
        }
        return attachMap.get(key);
    }

    AbsError(String message) {
        super(message);
    }

    public ErrorTip getErrorTip() {
        return errorTip;
    }

    protected final void setErrorTip(ErrorTip errorTip) {
        this.errorTip = errorTip;
    }
    
    
}
