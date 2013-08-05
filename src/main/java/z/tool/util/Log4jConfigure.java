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

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

/**
 * @author auzll@163.com
 */
public final class Log4jConfigure implements ServletContextListener {
    private String log4jConfigLocation = "WEB-INF" + File.separator + "classes" + File.separator + "log4j.properties";
    
    private long log4jRefreshInterval = 60000;
    
    public void contextDestroyed(ServletContextEvent event) {
    }

    public void contextInitialized(ServletContextEvent event) {
        log4jConfigLocation = StringUtil.getNotEmptyString(event.getServletContext().getInitParameter("log4jConfigLocation"), log4jConfigLocation);
        
        long log4jRef = NumberUtil.parseBaseLong(StringUtil.trimAndTryReturnNull(event.getServletContext().getInitParameter("log4jRefreshInterval")));
        if (log4jRef > 0) {
            log4jRefreshInterval = log4jRef;
        }
        
        String configFilename = event.getServletContext().getRealPath("/");
        if (!configFilename.endsWith(File.separator)) {
            configFilename += File.separator;
        }
        configFilename += log4jConfigLocation;
        
        File file = new File(configFilename);
        if (!file.exists()) {
            System.out.println("== Log4jInit ["+ configFilename + "] is not exist ==");
        } else {
            System.out.println("== Log4jInit " + configFilename + " ==");
            System.out.println("== Log4jInit log4jRefreshInterval -> " + log4jRefreshInterval + " ==");
            PropertyConfigurator.configureAndWatch(configFilename, log4jRefreshInterval);
        }
    }

}
