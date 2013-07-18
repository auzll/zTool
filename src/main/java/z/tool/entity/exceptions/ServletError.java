/**
 * Code by auzll (http://auzll.iteye.com)
 */
package z.tool.entity.exceptions;

import javax.servlet.ServletException;

import z.tool.entity.interfaces.ErrorTip;

/**
 * 
 * @author auzll
 * @since 2013-7-2 上午01:32:19
 */
public final class ServletError extends ServletException {

    
    /**  */
    private static final long serialVersionUID = -7264754235209774755L;

    private ErrorTip errorTip;
    
    public ServletError(ErrorTip errorTip) {
        super(errorTip.tip());
        this.errorTip = errorTip;
    }

    /**
     * 
     */
    public ServletError() {
    }

    /**
     * @param message
     */
    public ServletError(String message) {
        super(message);
    }

    /**
     * @param rootCause
     */
    public ServletError(Throwable rootCause) {
        super(rootCause);
    }

    /**
     * @param message
     * @param rootCause
     */
    public ServletError(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public ErrorTip getErrorTip() {
        return errorTip;
    }
}
