/**
 * https://github.com/auzll/zTool
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
