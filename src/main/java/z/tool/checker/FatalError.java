/**
 * 
 */
package z.tool.checker;

import z.tool.entity.interfaces.ErrorTip;

/**
 * @author auzll
 *
 */
public final class FatalError extends HumanNeededError {
    private static final long serialVersionUID = 84638553205689382L;
    
    public FatalError(ErrorTip errorTip) {
        super(errorTip);
    }

    public FatalError(String message) {
        super(message);
    }
    
    public FatalError(String format, Object... args) {
        super(String.format(format, args));
    }
    
    @Override public FatalError attach(Object key, Object value) {
        return (FatalError) super.attach(key, value);
    }
    
}
