/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

import z.tool.entity.interfaces.ErrorTip;

/**
 * 需要报警，人为跟进的错误
 */
public class HumanNeededError extends AbsError {
    private static final long serialVersionUID = 5924133530290728918L;
    
    public HumanNeededError(ErrorTip errorTip) {
        super(errorTip.tip());
        super.setErrorTip(errorTip);
    }

    public HumanNeededError(String message) {
        super(message);
    }
    
    public HumanNeededError(String format, Object... args) {
        super(String.format(format, args));
    }
    
    @Override public HumanNeededError attach(Object key, Object value) {
        return (HumanNeededError) super.attach(key, value);
    }
}
