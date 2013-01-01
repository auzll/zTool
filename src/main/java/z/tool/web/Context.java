/**
 * https://github.com/auzll/zTool
 */
package z.tool.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class Context {
    private static ThreadLocal<Context> LOCAL_CONTEXT = new ThreadLocal<Context>();
    
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final String contextRealPath;
    
    private Context(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        //this.contextRealPath = request.getServletContext().getRealPath("/");
        this.contextRealPath = request.getSession().getServletContext().getRealPath("/");
    }

    public HttpServletRequest getRequest() {
        return request;
    }
    
    public HttpSession getSession() {
        return null != request ? request.getSession() : null;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    
    public static Context get() {
        return LOCAL_CONTEXT.get();
    }
    
    public static void set(ServletRequest request, ServletResponse response) {
        LOCAL_CONTEXT.set(new Context((HttpServletRequest)request, (HttpServletResponse)response));
    }
    
    public static void remove() {
        LOCAL_CONTEXT.remove();
    }
    
    public static void destroy() {
        LOCAL_CONTEXT = null;
    }

    public String getContextRealPath() {
        return contextRealPath;
    }
    
    
}
