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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import z.tool.entity.json.JsonObject;

/**
 * 
 * @author auzll
 * @since 2013-6-28 下午09:22:03
 */
public final class ServletUtil {
    private static final Logger LOG = Logger.getLogger(ServletUtil.class);
    
    private ServletUtil() {}
    
    public static void printUtf8Json(HttpServletRequest request, HttpServletResponse response, JsonObject data) {
        printUtf8Json(0l, request, response, data);
    }
    
    public static void printUtf8Json(long userId, HttpServletRequest request, HttpServletResponse response, JsonObject data) {
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control","no-cache");
            
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().print(data.toJsonString());
            response.getWriter().flush();
        } catch (IOException e) {
            LOG.error("method:printUtf8Json,userId:" + userId
                    + ",threadId:" + Thread.currentThread().getId()
                    + ",errorMsg:" + e.getMessage(), e);
        }
    }
}
