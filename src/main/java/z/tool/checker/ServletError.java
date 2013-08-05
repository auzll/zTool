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

import javax.servlet.ServletException;

import z.tool.entity.interfaces.ErrorTip;

/**
 * 
 * @author auzll
 * @since 2013-7-2 上午01:32:19
 */
public final class ServletError extends ServletException {

    
    /**  */
    private static final long serialVersionUID = -7264754235209774755L;

    private ErrorTip errorTip;
    
    public ServletError(ErrorTip errorTip) {
        super(errorTip.tip());
        this.errorTip = errorTip;
    }

    /**
     * 
     */
    public ServletError() {
    }

    /**
     * @param message
     */
    public ServletError(String message) {
        super(message);
    }

    /**
     * @param rootCause
     */
    public ServletError(Throwable rootCause) {
        super(rootCause);
    }

    /**
     * @param message
     * @param rootCause
     */
    public ServletError(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public ErrorTip getErrorTip() {
        return errorTip;
    }
}
