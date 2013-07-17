/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * @author auzll
 *
 */
public final class DateUtil {
    private DateUtil() {}

    public static Date string2Date(String date, String pattern) {
        if (null == date || null == pattern) {
            return null;
        }
        
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (Exception e) {
            // ignore
            return null;
        }
        
    }
    
    public static String date2String(Date date, String pattern) {
        if (null == date || null == pattern) {
            return null;
        }
        
        try {
            return FastDateFormat.getInstance(pattern).format(date);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }
    
    /**
     * 把参数当 "yyyy-MM-dd" 格式转换
     */
    public static Date simpleString2Date(String date) {
        return string2Date(date, "yyyy-MM-dd");
    }
    
    /**
     * 把参数当 "yyyy-MM-dd HH:mm:ss" 格式转换
     */
    public static Date detailString2Date(String date) {
        return string2Date(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static Date long2Date(long date) {
        return new Date(date);
    }
    
    /**
     * 按 "yyyy-MM-dd" 格式化日期
     */
    public static String date2SimpleString(Date date) {
        return date2String(date, "yyyy-MM-dd");
    }
    
    /**
     * 按 "yyyy-MM-dd HH:mm:ss" 格式化日期
     */
    public static String date2DetailString(Date date) {
        return date2String(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    private static Date internalTrimOrAlterDate(
            Date date, 
            boolean trim, 
            int dayDiff) {
        
        if (null == date) {
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        if (trim) {
            cal.set(Calendar.MILLISECOND, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.HOUR_OF_DAY, 0);
        }
        
        if (0 != dayDiff) {
            cal.add(Calendar.DAY_OF_MONTH, dayDiff);
        }
        
        return cal.getTime();
    }
    
    /**
     * 重置日期中的毫秒、秒、分、时参数为0
     */
    public static Date trimDate(Date date) {
        return internalTrimOrAlterDate(date, true, 0);
    }
    
    /**
     * 修改日期：基于该时间增加或减少指定的天数
     */
    public static Date alterDate(Date date, int diff) {
        return internalTrimOrAlterDate(date, false, diff);
    }
    
    /**
     * 修改日期：基于该时间增加天数
     */
    public static Date increDate(Date date, int diff) {
        return alterDate(date, Math.abs(diff));
    }
    
    /**
     * 修改日期：基于该时间减少天数
     */
    public static Date decreDate(Date date, int diff) {
        return alterDate(date, -Math.abs(diff));
    }
    
    /**
     * 修改日期：重置日期中的毫秒、秒、分、时参数为0，并基于该时间增加天数
     */
    public static Date trimAndIncreDate(Date date, int diff) {
        return internalTrimOrAlterDate(date, true, Math.abs(diff));
    }
    
    /**
     * 修改日期：重置日期中的毫秒、秒、分、时参数为0，并基于该时间减少天数
     */
    public static Date trimAndDecreDate(Date date, int diff) {
        return internalTrimOrAlterDate(date, true, -Math.abs(diff));
    }
}
