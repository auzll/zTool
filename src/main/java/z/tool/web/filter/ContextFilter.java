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

import z.tool.web.Context;

public final class ContextFilter implements Filter {

    public void destroy() {
        Context.destroy();
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filter) throws IOException, ServletException {
        try {
            Context.set(request, response);
            filter.doFilter(request, response);
        } finally {
            Context.remove();
        }
    }

    public void init(FilterConfig config) throws ServletException {}
}
