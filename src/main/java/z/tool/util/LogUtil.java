/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.Arrays;
import java.util.Date;


/**
 * 日志工具类
 */
public final class LogUtil {
    private LogUtil() {}
    
    /**
     * 检查对象是否为空，是则创建新对象返回，否则返回原对象
     */
    private static StringBuilder checkBuff(StringBuilder buff) {
        if (null == buff) {
            buff = new StringBuilder();
        }
        return buff;
    }
    
    public static StringBuilder beginLog(String methodName) {
        return beginLog(null, null, methodName);
    }
    
    public static StringBuilder beginLog(String operatorId, String operatorNick, String methodName) {
        StringBuilder logBuff = new StringBuilder();
        
        if (null != operatorId) {
            appendLog(logBuff, "operatorId", operatorId);
        }
        
        if (null != operatorNick) {
            appendLog(logBuff, "operatorNick", operatorNick);
        }
        
        return appendLog(logBuff, "method", methodName);
    }
    
    public static String finishLog(StringBuilder logBuff) {
        if (null == logBuff || logBuff.length() < 1) {
            return "";
        }
        return StringUtil.deleteEnd(logBuff, ',').toString();
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, StringBuilder value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, String value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, Object value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, Date value) {
        return checkBuff(logBuff).append(key).append(":").append(null != value ? value.getTime() : 0).append(",");
    }
    
    public static StringBuilder appendCurrentThreadId(StringBuilder logBuff) {
        return checkBuff(logBuff).append("threadId").append(":").append(Thread.currentThread().getId()).append(",");
    }
    
    public static StringBuilder appendClassSimpleName(StringBuilder logBuff, String key, Class<?> clazz) {
        return checkBuff(logBuff).append(key).append(":").append(null != clazz ? clazz.getSimpleName() : null).append(",");
    }
    
    public static StringBuilder appendLogArrayBegin(StringBuilder logBuff, String key) {
        return checkBuff(logBuff).append(key).append(":[");
    }
    
    public static StringBuilder appendLogArrayFinish(StringBuilder logBuff) {
        return StringUtil.deleteEnd(checkBuff(logBuff), ',').append("]").append(",");
    }
    
    public static StringBuilder appendLogObjectBegin(StringBuilder logBuff, String key) {
        return checkBuff(logBuff).append(key).append(":{");
    }
    
    public static StringBuilder appendLogObjectFinish(StringBuilder logBuff) {
        return StringUtil.deleteEnd(checkBuff(logBuff), ',').append("}").append(",");
    }
    
    public static StringBuilder appendLogClassSimpleNameBegin(StringBuilder logBuff, Class<?> clazz) {
        return checkBuff(logBuff).append(null != clazz ? clazz.getSimpleName() : null).append(":{");
    }
    
    public static StringBuilder appendLogClassSimpleNameFinish(StringBuilder logBuff) {
        return StringUtil.deleteEnd(checkBuff(logBuff), ',').append("}").append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String keyA, Object valueA, String keyB, Object valueB) {
        return checkBuff(logBuff).append("{").append(keyA).append(":")
                .append(valueA).append(",").append(keyB).append(":")
                .append(valueB).append("}").append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, Object[] value) {
        return checkBuff(logBuff).append(key).append(":").append(Arrays.toString(value)).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, int value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, int[] values) {
        logBuff = checkBuff(logBuff).append(key).append(":[");
        for (int i = 0; i < values.length; i++) {
            logBuff.append(values[i]).append(',');
        }
        StringUtil.deleteEnd(logBuff, ',');
        logBuff.append("],");
        return logBuff;
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, long value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
    
    public static StringBuilder appendLog(StringBuilder logBuff, String key, boolean value) {
        return checkBuff(logBuff).append(key).append(":").append(value).append(",");
    }
}
