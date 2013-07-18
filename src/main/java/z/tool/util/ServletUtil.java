/**
 * Code by auzll (http://auzll.iteye.com)
 */
package z.tool.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import z.tool.entity.json.JsonObject;

/**
 * 
 * @author auzll
 * @since 2013-6-28 下午09:22:03
 */
public final class ServletUtil {
    private static final Logger LOG = Logger.getLogger(ServletUtil.class);
    
    private ServletUtil() {}
    
    public static void printUtf8Json(HttpServletRequest request, HttpServletResponse response, JsonObject data) {
        printUtf8Json(0l, request, response, data);
    }
    
    public static void printUtf8Json(long userId, HttpServletRequest request, HttpServletResponse response, JsonObject data) {
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control","no-cache");
            
            response.setContentType("text/json;charset=utf-8");
            response.getWriter().print(data.toJsonString());
            response.getWriter().flush();
        } catch (IOException e) {
            LOG.error("method:printUtf8Json,userId:" + userId
                    + ",threadId:" + Thread.currentThread().getId()
                    + ",errorMsg:" + e.getMessage(), e);
        }
    }
}
