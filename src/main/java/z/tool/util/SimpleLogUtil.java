/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

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
