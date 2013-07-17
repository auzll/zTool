/**
 * 
 */
package z.tool.entity.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import z.tool.entity.json.ali.JsonFactoryImpl;
import z.tool.util.StringUtil;

/**
 * @author auzll
 *
 */
public final class Jsons {
    private static final Logger LOG = Logger.getLogger(Jsons.class);
    private static final JsonFactoryInterface JSON_FACTORY;
    
    static {
        Class<?> factoryClass = null;
        
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("zTool.properties");
            if (null != is) {
                Properties properties = new Properties();
                properties.load(is);
                String className = StringUtil.trimAndTryReturnNull(properties.getProperty("json.factory.impl"));
                if (null != className) {
                    factoryClass = Class.forName(className);
                }
            }
        } catch (Exception e) {
            LOG.info("method:static,errorMsg:" + e.getMessage());
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        
        if (null == factoryClass) {
            factoryClass = JsonFactoryImpl.class;
        }
        
        try {
            JSON_FACTORY = (JsonFactoryInterface) factoryClass.newInstance();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    private Jsons() {}
    
    public static JsonObject newJsonObject() {
        return JSON_FACTORY.newJsonObject();
    }
    
    public static JsonObject parseObject(String text) {
        return JSON_FACTORY.parseObject(text);
    }
    
    public static JsonArray newJsonArray() {
        return JSON_FACTORY.newJsonArray();
    }

    public static JsonArray parseArray(String text) {
        return JSON_FACTORY.parseArray(text);
    }

}
