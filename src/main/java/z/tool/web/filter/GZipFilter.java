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
package z.tool.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * 参考django GZipMiddleware
 * @author auzll@163.com
 * @since 2011-12-22
 */
public final class GZipFilter implements Filter {
    private static class GZipResponse extends HttpServletResponseWrapper {
        private ServletOutputStream stream;
        public GZipResponse(HttpServletResponse response) throws IOException {
            super(response);
            stream = new GZipResponseStream(response);
        }
        public void flushBuffer() throws IOException {
            stream.flush();
        }
        public ServletOutputStream getOutputStream() throws IOException {
            return stream;
        }
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(stream);
        }
    }
    
    private static class GZipResponseStream extends ServletOutputStream {
        private HttpServletResponse response;
        private ByteArrayOutputStream baos;
        private GZIPOutputStream gzipStream;
        public GZipResponseStream(HttpServletResponse response) throws IOException {
            this.response = response;
            baos = new ByteArrayOutputStream();
            gzipStream = new GZIPOutputStream(baos);
        }
        public void close() throws IOException {
            gzipStream.finish();
            byte[] bytes = baos.toByteArray();
            response.addHeader("Content-Length", Integer.valueOf(bytes.length).toString());
            
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
        public void flush() throws IOException {
            gzipStream.flush();
        }
        public void write(int b) throws IOException {
            gzipStream.write(b);
        }
    }
    
    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        doFilter0((HttpServletRequest)req, (HttpServletResponse)resp, filterChain);
    }

    public void init(FilterConfig config) throws ServletException {}
    
    private void doFilter0(HttpServletRequest req, HttpServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        
        boolean wrap = false;
        String contentEncoding = req.getHeader("Content-Encoding");
        String acceptEncoding = req.getHeader("Accept-Encoding");
        
        if (null == contentEncoding 
                && null != acceptEncoding 
                && -1 != acceptEncoding.toLowerCase().indexOf("gzip")) {
            
            String varyHeaders = req.getHeader("Vary");
            if (null == varyHeaders) {
                varyHeaders = "Accept-Encoding";
            } else if (-1 == varyHeaders.indexOf("Accept-Encoding")) {
                varyHeaders += ", Accept-Encoding";
            }
            resp.addHeader("Vary", varyHeaders);
            
            resp.addHeader("Content-Encoding", "gzip");
            resp = new GZipResponse(resp);
            wrap = true;
        }
        
        filterChain.doFilter(req, resp);
        
        if (wrap) {
            resp.getOutputStream().close();
        }
    }
}
