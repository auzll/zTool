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

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import z.tool.entity.interfaces.IUser;

public class SessionModel implements HttpSessionBindingListener, Serializable {
    
    private static final long serialVersionUID = 3817628290419587422L;

    public static SessionModel valueOf(IUser user) {
        return new SessionModel(user);
    }
        
    private final IUser user;

    private SessionModel(IUser user) {
        this.user = user;
    }

    public void valueBound(HttpSessionBindingEvent event) {}

    public void valueUnbound(HttpSessionBindingEvent event) {}

    @SuppressWarnings("unchecked")
    public <T extends IUser>T getUser() {
        return (T)(user);
    }
}
