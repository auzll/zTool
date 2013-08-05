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

import java.util.List;

import com.google.common.collect.Lists;

public abstract class Checker {
    
    public static void checkLogic(boolean expression,
            String format, 
            Object... args) {
        if (!expression) {
            throw new LogicError(String.format(format, args));
        }
    }
    
    public static void checkHumainNeeded(boolean expression,
            String format, 
            Object... args) {
        if (!expression) {
            throw new HumanNeededError(String.format(format, args));
        }
    }
    
    public static void checkPrivilege(boolean expression,
            String format, 
            Object... args) {
        if (!expression) {
            throw new PrivilegeError(String.format(format, args));
        }
    }
    
    private List<String> errors;
    
    private String errorSeprator = "；";
    
    public Checker() {}
    
    public Checker(String errorSeprator) {
        this.errorSeprator = errorSeprator;
    }

    private List<String> getAndTryInit() {
        if (null == this.errors) {
            this.errors = Lists.newArrayList();
        }
        return this.errors;
    }
    
    public Checker checkExpression(boolean expression,
            String format, 
            Object... args) {
        if (expression) {
            return this;
        }
        
        if (null != args) {
            getAndTryInit().add(String.format(format, args));
        } else {
            getAndTryInit().add(format);
        }
        
        return this;
    }
    
    public Checker checkNotNull(Object obj,
            String format, 
            Object... args) {
        if (null != obj) {
            return this;
        }
        
        if (null != args) {
            getAndTryInit().add(String.format(format, args));
        } else {
            getAndTryInit().add(format);
        }
        
        return this;
    }
    
    public void throwIfExistError() {
        if (null == this.errors || this.errors.size() < 1) {
            return;
        }
        
        StringBuilder buff = new StringBuilder();
        for (int i = 0, max = errors.size(), sLast = max - 1; i < max; i++) {
            buff.append(errors.get(i));
            if (i < sLast) {
                buff.append(errorSeprator);
            }
        }
        throw newBaseError(buff.toString());
    }
    
    abstract AbsError newBaseError(String message);
}
