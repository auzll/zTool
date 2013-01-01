/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class ThreadUtil {
    private ThreadUtil() {}
    
    public static class ScheduledExecutorServiceHolder {
        public static final ScheduledExecutorService EXECUTOR = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }
    
    public static void destroy() {
        try {
            ScheduledExecutorServiceHolder.EXECUTOR.shutdown();
        } catch (Exception e) {
        }
    }
}
