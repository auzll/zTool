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

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.helpers.FileWatchdog;

/**
 * @author auzll@163.com
 */
public final class PropertiesWatchDog {
    private static final Logger LOG = Logger.getLogger(PropertiesWatchDog.class);

    private Properties properties;
    private FileWatchdog watchdog;

    public PropertiesWatchDog(String filename) {
        properties = PropertiesLoader.loadProperties(filename);

        watchdog = new FileWatchdog(filename) {
            protected void doOnChange() {
                String oldProps = properties.toString();
                properties = PropertiesLoader.loadProperties(filename);
                LOG.info("method:watch,desc:loadProperties,oldProps:" + oldProps + ",newProps:" + properties);
            }
        };
    }

    public boolean containsKey(String key) {
        return null != properties ? properties.containsKey(key) : false;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return null != properties ? StringUtil.trimAndTryReturnNull(properties.getProperty(key)) : null;
    }

    public PropertiesWatchDog start() {
        watchdog.start();
        return this;
    }
    
    @SuppressWarnings("deprecation")
    public PropertiesWatchDog stop() {
        watchdog.stop();
        return this;
    }
}
