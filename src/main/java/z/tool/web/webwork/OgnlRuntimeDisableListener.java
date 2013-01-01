/**
 * https://github.com/auzll/zTool
 */
package z.tool.web.webwork;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ognl.OgnlRuntime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import z.tool.util.LogUtil;

/**
 * @author auzll
 */
public final class OgnlRuntimeDisableListener implements ServletContextListener {
    private static final Log LOG = LogFactory.getLog(OgnlRuntimeDisableListener.class);
    
    public static final String OGNL_RUNTIME_DISABLE_KEY = "ognlRuntimeDisableClasses";

    public void contextDestroyed(ServletContextEvent event) {}

    public void contextInitialized(ServletContextEvent event) {
        StringBuilder logBuff = LogUtil.beginLog("contextInitialized");
        try {
            String disableClasses = event.getServletContext().getInitParameter(OGNL_RUNTIME_DISABLE_KEY);
            LogUtil.appendLog(logBuff, "disableClasses", disableClasses);
            
            if (null != disableClasses) {
                String[] classes = disableClasses.split(",");
                for (String className : classes) {
                    Class<?> clazz = Class.forName(className);
                    OgnlRuntime.setMethodAccessor(clazz, NoAccessor.INSTANCE);
                }
            }
            
            LOG.info(LogUtil.finishLog(logBuff));
            
        } catch (Exception e) {
            LogUtil.appendLog(logBuff, "errorMsg", e.getMessage());
            LOG.error(LogUtil.finishLog(logBuff), e);
            throw new RuntimeException("Fail to run the OgnlRuntimeDisableListener");
        }
        
    }

}
