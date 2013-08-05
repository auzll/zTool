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

import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

public final class SimpleLogUtil {
    private SimpleLogUtil() {}
    
    public static void log(String type, String log) {
        System.out.println(FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + type + " " + log);
    }
    
    public static void info(String log) {
        log("INFO", log);
    }
    
    public static void error(String log) {
        log("ERROR", log);
    }
    
    public static void warn(String log) {
        log("WARN", log);
    }
}
