/**
 * https://github.com/auzll/zTool
 */
package z.tool.web.webwork;

import java.util.Map;

import ognl.MethodAccessor;
import ognl.MethodFailedException;

/**
 * @author auzll
 */
@SuppressWarnings("rawtypes") 
public enum NoAccessor implements MethodAccessor {
    INSTANCE;

    public Object callMethod(Map context, Object target, String methodName,
            Object[] args)
            throws MethodFailedException {
        throw new MethodFailedException(target, methodName);
    }

    public Object callStaticMethod(Map context, Class targetClass,
            String methodName, Object[] args) throws MethodFailedException {
        throw new MethodFailedException(targetClass, methodName);
    }

}
