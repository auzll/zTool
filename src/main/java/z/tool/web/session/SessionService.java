/**
 * https://github.com/auzll/zTool
 */
package z.tool.web.session;

import javax.servlet.http.HttpSession;

import z.tool.web.Context;

public final class SessionService  {
    /** SessionModel */
    public static final String SESSION_NAME = "SessionModel";
    
    private SessionService() {}

    public static void clearCurrentSessionModel() {
        HttpSession session = getSession();
        if ( null != session ) {
            session.removeAttribute(SESSION_NAME);
        }
    }
    
    public static SessionModel getCurrentSessionModel() {
        HttpSession session = getSession();
        if ( null != session ) {
            return (SessionModel) session.getAttribute(SESSION_NAME);
        }
        return null;
    }

    public static void setCurrentSessionModel(SessionModel sessionModel) {
        HttpSession session = getSession();
        if ( null != session ) {
            session.setAttribute(SESSION_NAME, sessionModel);
        }
    }

    private static HttpSession getSession() {
        Context context = Context.get();
        return null != context ? context.getSession() : null;
    }

}
