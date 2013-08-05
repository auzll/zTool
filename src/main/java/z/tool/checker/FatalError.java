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
 * @author auzll
 *
 */
public final class FatalError extends HumanNeededError {
    private static final long serialVersionUID = 84638553205689382L;
    
    public FatalError(ErrorTip errorTip) {
        super(errorTip);
    }

    public FatalError(String message) {
        super(message);
    }
    
    public FatalError(String format, Object... args) {
        super(String.format(format, args));
    }
    
    @Override public FatalError attach(Object key, Object value) {
        return (FatalError) super.attach(key, value);
    }
    
}
