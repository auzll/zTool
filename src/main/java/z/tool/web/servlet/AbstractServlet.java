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
package z.tool.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import z.tool.checker.ServletError;
import z.tool.entity.enums.Error;
import z.tool.entity.json.JsonObject;
import z.tool.entity.json.Jsons;
import z.tool.util.LogUtil;
import z.tool.util.ServletUtil;
import z.tool.util.StringUtil;
import z.tool.util.ToStringBuilder;
import z.tool.web.Context;


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
    
    private ReentrantLock lock = new ReentrantLock();
    
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
    
    protected Part getPart(HttpServletRequest request, String key) throws ServletError {
        try {
            return request.getPart(key);
        } catch (Exception e) {
            throw new ServletError(e);
        }
    }
    
    protected File getPartFile(HttpServletRequest request, String key) throws ServletError {
        Part part = getPart(request, key);
        if (null == part) {
            return null;
        }
        
        FileOutputStream fos = null;
        try {
            File tmp = File.createTempFile("servlet_part", "tmp");
            fos = new FileOutputStream(tmp);
            IOUtils.copy(part.getInputStream(), fos);
            return tmp;
        } catch (IOException e) {
            throw new ServletError(e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Context context = Context.get();
        StringBuilder logBuff = null != context ? context.getLogBuff() : new StringBuilder();
        
        final long start = System.currentTimeMillis();
        boolean gotError = false;
        try {
            LogUtil.appendLogClassSimpleNameBegin(logBuff, AbstractServlet.class);
            LogUtil.appendCurrentThreadId(logBuff);
            
            LogUtil.appendLog(logBuff, "clazz", getRealClassName());
            LogUtil.appendLog(logBuff, "httpMethod", request.getMethod());
            LogUtil.appendLog(logBuff, "requestURI", request.getRequestURI());
            LogUtil.appendLog(logBuff, "queryString", request.getQueryString());
            
            LogUtil.appendIdLog(logBuff, "userId", getModelUserId(request));
            
            if (requestCount > maxConcurrent) {
                gotError = true;
                LogUtil.appendLog(logBuff, "descr", "overload");
                if (LOG_DEBUG) {
                    LogUtil.appendLog(logBuff, "overloadDiff", (System.currentTimeMillis() - start));
                }
                response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                        Error.TOO_MANY_USER.tip());
                return;
            }
            
            int requestIndex;
            try {
                lock.lock();
                requestIndex = requestCount++;
                
                if (requestIndex > maxConcurrent) {
                    gotError = true;
                    LogUtil.appendLog(logBuff, "descr", "overload in lock");
                    if (LOG_DEBUG) {
                        LogUtil.appendLog(logBuff, "overloadDiff", (System.currentTimeMillis() - start));
                    }
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, 
                            Error.TOO_MANY_USER.tip());
                    return;
                }
                
            } finally {
                lock.unlock();
            }
            
            super.service(request, response);
            
            if (LOG_DEBUG) {
                LogUtil.appendLog(logBuff, "requestIndex", requestIndex);
                LogUtil.appendLog(logBuff, "serviceDiff", (System.currentTimeMillis() - start));
            }
            
        } catch (ServletError e) {
            if (LOG_DEBUG) {
                LogUtil.appendLog(logBuff, "errorDiff", (System.currentTimeMillis() - start));
            }
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
            LogUtil.appendLogClassSimpleNameFinish(logBuff);
            if (gotError) {
                LOG.error(LogUtil.finishLog(logBuff));
            } else if (LOG_INFO) {
                LOG.info(LogUtil.finishLog(logBuff));
            }
            
            requestCount--;
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
                .add("maxConcurrent", this.maxConcurrent).plain());
        }
    }
    
    @Override protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            post(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "post")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doPost(req, resp);
    }
    
    @Override protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            get(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "get")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doGet(req, resp);
    }
    
    @Override protected final void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            delete(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "delete")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doDelete(req, resp);
    }
    
    @Override protected final void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            head(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "head")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void head(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doHead(req, resp);
    }

    @Override protected final void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            options(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "options")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void options(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doOptions(req, resp);
    }

    @Override protected final void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            put(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "put")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void put(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doPut(req, resp);
    }
    
    @Override protected final void doTrace(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            trace(req, resp);
        } catch (Exception e) {
            LOG.error(new ToStringBuilder()
                .add("method", "trace")
                .add("errorMsg", e.getMessage()).plain(), e);
            throw new ServletError(Error.SYSTEM_BUSY);
        }
    }
    
    protected void trace(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        super.doTrace(req, resp);
    }
}
