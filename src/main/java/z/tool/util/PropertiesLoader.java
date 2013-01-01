/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * @author auzll@163.com
 */
public final class PropertiesLoader {
    public static String getPropery(String fileName, String key) {
        Properties properties = loadProperties(fileName);
        return null != properties ? StringUtil.trimAndTryReturnNull(properties.getProperty(key)) : null;
    }
    
    public static boolean hasProperyKey(String fileName, String key) {
        Properties properties = loadProperties(fileName);
        return null != properties ? properties.containsKey(key) : false;
    }
    
    public static Properties loadProperties(String fileName) {
        Properties properties = new Properties();
        
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
            properties.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        
        return properties;
    }
    
    private PropertiesLoader() {}
}
