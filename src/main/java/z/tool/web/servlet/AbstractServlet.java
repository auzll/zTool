/**
 * Code by auzll (http://auzll.iteye.com)
 */
package z.tool.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import z.tool.entity.enums.Error;
import z.tool.entity.exceptions.ServletError;
import z.tool.entity.json.JsonObject;
import z.tool.entity.json.Jsons;
import z.tool.util.ServletUtil;
import z.tool.util.StringUtil;
import z.tool.util.ToStringBuilder;


/**
 * 
 * @author auzll
 * @since 2013-6-22 下午02:38:33
 */
public abstract class AbstractServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(AbstractServlet.class);
    private static final boolean LOG_DEBUG = LOG.isDebugEnabled();
    private static final boolean LOG_INFO = LOG.isInfoEnabled();
    
    private static final long serialVersionUID = -6467306905009045762L;
    
    private static final int DEFAULT_MAX_CONCURRENT = 100;
    
    /** 最大并发量 */
    private int maxConcurrent = DEFAULT_MAX_CONCURRENT;
    
    /** 当前请求量 */
    private volatile int requestCount;
    
    protected long getModelUserId(HttpServletRequest request) {
        return 0l;
    }
    
    protected void setMaxConcurrent(int maxConcurrent) {
        this.maxConcurrent = maxConcurrent;
    }
    
    private String getRealClassName() {
        return getRealClassName(2);
    }
    
    private String getRealClassName(int max) {
        char[] chars = this.getClass().getName().toCharArray();
        
        int found = 0;
        StringBuilder buff = new StringBuilder();
        for (int i = chars.length-1; i > -1; i--) {
            char c = chars[i];
            if ('.' == c) {
                found++;
            }
            if (found >= max) {
                break;
            }
            buff.append(c);
        }
        
        return buff.reverse().toString();
    }
    
    protected String getParamString(HttpServletRequest request, String key) {
        return StringUtil.trimAndTryReturnNull(request.getParameter(key));
    }
    
    protected int getParamInt(HttpServletRequest request, String key) {
        String str = getParamString(request, key);
        if (null == str) {
            return 0;
        }
        
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    protected long getParamLong(HttpServletRequest request, String key) {
        String str = getParamString(request, key);
        if (null == str) {
            return 0;
        }
        
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    protected double getParamDouble(HttpServletRequest request, String key) {
        String str = getParamString(request, key);
        if (null == str) {
            return 0;
        }
        
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return 0d;
        }
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ToStringBuilder logBuilder = null;
        
        if (requestCount > maxConcurrent) {
            logBuilder = new ToStringBuilder()
                .add("method", "service")
                .add("clazz", getRealClassName())
                .add("httpMethod", request.getMethod())
                .add("requestURI", request.getRequestURI());
            
            if (null != request.getQueryString()) {
                logBuilder.getOrTryInitBuff()
                    .append("queryString")
                    .append(":{")
                    .append(request.getQueryString())
                    .append("},");
            }
            
            logBuilder.add("threadId", Thread.currentThread().getId())
                .addId("userId", getModelUserId(request))
                .add("descr", "overload")
                ;
            LOG.error(logBuilder.build());
            
            response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                    Error.TOO_MANY_USER.tip());
            return;
        }
        
        long start = 0;
        try {
            if (LOG_DEBUG) {
                start = System.currentTimeMillis();
            }
            requestCount++;
            
            super.service(request, response);
            
        } catch (ServletError e) {
            String requestType = request.getHeader("X-Requested-With");  
            if (null != requestType && "XMLHttpRequest".equalsIgnoreCase(requestType)) {
                JsonObject data = Jsons.newJsonObject();
                data.put("success", false);
                data.put("descr", e.getErrorTip().tip());
                ServletUtil.printUtf8Json(request, response, data);
            } else {
                request.setAttribute(ServletError.class.getSimpleName(), e);
                throw e;
            }
        } finally {
            requestCount--;
            
            if (LOG_DEBUG) {
                long end = System.currentTimeMillis();
                logBuilder = new ToStringBuilder()
                    .add("method", "service")
                    .add("clazz", getRealClassName())
                    .add("httpMethod", request.getMethod())
                    .add("requestURI", request.getRequestURI());
                
                if (null != request.getQueryString()) {
                    logBuilder.getOrTryInitBuff()
                        .append("queryString")
                        .append(":{")
                        .append(request.getQueryString())
                        .append("},");
                }
                
                logBuilder.add("threadId", Thread.currentThread().getId())
                    .addId("userId", getModelUserId(request))
                    .add("requestCount", requestCount)
                    .add("start", start)
                    .add("end", end)
                    .add("diffTime", (end- start));
                LOG.debug(logBuilder.build());
            }
        }
    }
    
    @Override public void init(ServletConfig config) throws ServletException {
        String maxConcurrent = config.getInitParameter("maxConcurrent");
        if (null != maxConcurrent) {
            int intMaxConcurrent = StringUtil.stringToInt(maxConcurrent);
            if (intMaxConcurrent > 0) {
                this.maxConcurrent = intMaxConcurrent;
            }
        }
        
        if (LOG_INFO) {
            LOG.info(new ToStringBuilder()
                .add("method", "init")
                .add("clazz", getRealClassName())
                .add("maxConcurrent", this.maxConcurrent).build());
        }
    }
    
    @Override protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            post(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "post")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doPost(req, resp);
    }
    
    @Override protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            get(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "get")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doGet(req, resp);
    }
    
    @Override protected final void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            delete(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "delete")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doDelete(req, resp);
    }
    
    @Override protected final void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            head(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "head")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void head(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doHead(req, resp);
    }

    @Override protected final void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            options(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "options")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void options(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doOptions(req, resp);
    }

    @Override protected final void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            put(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "put")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void put(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doPut(req, resp);
    }
    
    @Override protected final void doTrace(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            trace(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "trace")
                .add("errorMsg", e.getMessage()).build(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    void trace(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doTrace(req, resp);
    }
}
