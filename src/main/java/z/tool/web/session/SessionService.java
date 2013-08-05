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
package z.tool.web.session;

import javax.servlet.http.HttpSession;

import z.tool.web.Context;

public final class SessionService  {
    /** SessionModel */
    public static final String SESSION_NAME = "SessionModel";
    
    private SessionService() {}

    public static void clearCurrentSessionModel() {
        HttpSession session = getSession();
        if ( null != session ) {
            session.removeAttribute(SESSION_NAME);
        }
    }
    
    public static SessionModel getCurrentSessionModel() {
        HttpSession session = getSession();
        if ( null != session ) {
            return (SessionModel) session.getAttribute(SESSION_NAME);
        }
        return null;
    }

    public static void setCurrentSessionModel(SessionModel sessionModel) {
        HttpSession session = getSession();
        if ( null != session ) {
            session.setAttribute(SESSION_NAME, sessionModel);
        }
    }

    private static HttpSession getSession() {
        Context context = Context.get();
        return null != context ? context.getSession() : null;
    }

}
