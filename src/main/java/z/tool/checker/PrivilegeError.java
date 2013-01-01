/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

/**
 * 非法访问
 * @author auzll
 */
public final class PrivilegeError extends AbsError {
    private static final long serialVersionUID = 5924133530290728918L;

    public PrivilegeError(String message) {
        super(message);
    }
    
    public PrivilegeError(String format, Object... args) {
        super(String.format(format, args));
    }
}
