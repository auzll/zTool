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
