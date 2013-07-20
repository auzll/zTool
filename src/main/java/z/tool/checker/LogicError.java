/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

import z.tool.entity.interfaces.ErrorTip;

/**
 * 逻辑错误
 */
public final class LogicError extends AbsError {
    
    /** */
    private static final long serialVersionUID = 7550813627149357333L;
    
    public LogicError(ErrorTip errorTip) {
        super(errorTip.tip());
        super.setErrorTip(errorTip);
    }

    public LogicError(String message) {
        super(message);
    }
    
    public LogicError(String format, Object... args) {
        super(String.format(format, args));
    }
    
    @Override public LogicError attach(Object key, Object value) {
        return (LogicError) super.attach(key, value);
    }
}
