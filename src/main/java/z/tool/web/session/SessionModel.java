/**
 * https://github.com/auzll/zTool
 */
package z.tool.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import z.tool.entity.interfaces.IUser;

public class SessionModel implements HttpSessionBindingListener, Serializable {
    
    private static final long serialVersionUID = 3817628290419587422L;

    public static SessionModel valueOf(IUser user) {
        return new SessionModel(user);
    }
        
    private final IUser user;

    private SessionModel(IUser user) {
        this.user = user;
    }

    public void valueBound(HttpSessionBindingEvent event) {}

    public void valueUnbound(HttpSessionBindingEvent event) {}

    @SuppressWarnings("unchecked")
    public <T extends IUser>T getUser() {
        return (T)(user);
    }
}
