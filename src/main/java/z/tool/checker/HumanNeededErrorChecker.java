/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

public final class HumanNeededErrorChecker extends Checker {

    @Override
    AbsError newBaseError(String message) {
        return new HumanNeededError(message);
    }

}
