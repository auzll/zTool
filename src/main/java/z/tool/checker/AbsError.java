/**
 * https://github.com/auzll/zTool
 */
package z.tool.checker;

import java.util.Map;

import z.tool.entity.interfaces.ErrorTip;

import com.google.common.collect.Maps;

class AbsError extends RuntimeException {
    
    /** */
    private static final long serialVersionUID = 1384838746221312014L;
    
    private Map<Object, Object> attachMap;
    
    private ErrorTip errorTip;
    
    private Map<Object, Object> newOrGetAttachMap() {
        if (null == attachMap) {
            attachMap = Maps.newHashMap();
        }
        return attachMap;
    }
    
    public AbsError attach(Object key, Object value) {
        if (null == key || null == value) {
            throw new IllegalArgumentException("key or value is null");
        }
        newOrGetAttachMap().put(key, value);
        return this;
    }
    
    public Object getAttach(Object key) {
        if (null == key) {
            throw new IllegalArgumentException("key is null");
        }
        if (null == attachMap) {
            return null;
        }
        return attachMap.get(key);
    }

    AbsError(String message) {
        super(message);
    }

    public ErrorTip getErrorTip() {
        return errorTip;
    }

    protected final void setErrorTip(ErrorTip errorTip) {
        this.errorTip = errorTip;
    }
    
    
}
