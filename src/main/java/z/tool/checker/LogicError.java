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

import z.tool.entity.interfaces.ErrorTip;

/**
 * 逻辑错误
 */
public final class LogicError extends AbsError {
    
    /** */
    private static final long serialVersionUID = 7550813627149357333L;
    
    public LogicError(ErrorTip errorTip) {
        super(errorTip.tip());
        super.setErrorTip(errorTip);
    }

    public LogicError(String message) {
        super(message);
    }
    
    public LogicError(String format, Object... args) {
        super(String.format(format, args));
    }
    
    @Override public LogicError attach(Object key, Object value) {
        return (LogicError) super.attach(key, value);
    }
}
