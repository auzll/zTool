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
package z.tool.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class Context {
    private static ThreadLocal<Context> LOCAL_CONTEXT = new ThreadLocal<Context>();
    
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String contextRealPath;
    
    private StringBuilder logBuff;
    
    private Context(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.contextRealPath = request.getSession().getServletContext().getRealPath("/");
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    
    public HttpSession getSession() {
        return null != request ? request.getSession() : null;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    
    public static Context get() {
        return LOCAL_CONTEXT.get();
    }
    
    public static void set(ServletRequest request, ServletResponse response) {
        LOCAL_CONTEXT.set(new Context((HttpServletRequest)request, (HttpServletResponse)response));
    }
    
    public static void remove() {
        LOCAL_CONTEXT.remove();
    }
    
    public static void destroy() {
        LOCAL_CONTEXT = null;
    }

    public String getContextRealPath() {
        return contextRealPath;
    }

    public StringBuilder getLogBuff() {
        if (null == logBuff) {
            logBuff = new StringBuilder();
        }
        return logBuff;
    }
    
    public StringBuilder getDirectLogBuff() {
        return logBuff;
    }
}
