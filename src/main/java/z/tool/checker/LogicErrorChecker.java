/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

public final class LogicErrorChecker extends Checker {

    @Override
    AbsError newBaseError(String message) {
        return new LogicError(message);
    }

}
