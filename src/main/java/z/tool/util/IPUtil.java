/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author auzll@163.com
 */
public final class IPUtil {
    public static String getRequestIp(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ZUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (ZUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (ZUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddr();
                }
            }
        }
        int idx = -1;
        if (!ZUtils.isEmpty(ip) && (idx = ip.indexOf(',')) > -1) {
            ip = ip.substring(0, idx);
        }
        return ip;
    }
    
    private IPUtil() {}
}
