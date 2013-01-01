/**
 * https://github.com/auzll/zTool
 */
package z.tool.util;

import java.util.HashMap;

public class ParamMap extends HashMap<String, Object> {
    private static final long serialVersionUID = -6762737479054111128L;

    public ParamMap() {}
    
    public ParamMap(String key, Object value) {
        super.put(key, value);
    }
    
    /**
     * 添加新的参数和值
     * @param key 参数名称
     * @param value 值
     * @return 返回ParamMap本身
     */
    public ParamMap add(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
