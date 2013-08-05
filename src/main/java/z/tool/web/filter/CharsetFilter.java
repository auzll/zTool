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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import z.tool.util.StringUtil;

/**
 * @author auzll@163.com
 */
public final class CharsetFilter implements Filter {
    protected String charset = StringUtil.CHARSET_UTF_8;

    public void destroy() {}

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        req.setCharacterEncoding(charset);
        filterChain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        String charset = config.getInitParameter("charset");
        if (null != charset && (charset = charset.trim()).length() > 0) {
            this.charset = charset;
        }
    }
}
