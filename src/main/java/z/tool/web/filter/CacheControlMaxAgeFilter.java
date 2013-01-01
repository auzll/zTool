/**
 * https://github.com/auzll/zTool
 */
package z.tool.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import z.tool.util.StringUtil;

/**
 * @author auzll@163.com
 * @since 2011-12-22
 */
public final class CacheControlMaxAgeFilter implements Filter {
    private String maxAge = Long.valueOf(60 * 60 * 24 * 365).toString();

    public void destroy() {}
    
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        doFilter0((HttpServletRequest)req, (HttpServletResponse)resp, filterChain);
    }
    
    public void init(FilterConfig config) throws ServletException {
        String maxAge = StringUtil.trimAndTryReturnNull(config.getInitParameter("maxAge"));
        if (null != maxAge) {
            this.maxAge = maxAge;
        }
    }
    
    private void doFilter0(HttpServletRequest req, HttpServletResponse resp,
            FilterChain filterChain) throws IOException, ServletException {
        resp.addHeader("Cache-Control", "max-age=" + maxAge);
        filterChain.doFilter(req, resp);
    }
}
