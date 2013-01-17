/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.interfaces;

import net.sf.json.JSONObject;

/**
 * IJson
 */
public interface IJson<T> {
    /**
     * @return 返回JSONObject
     */
    JSONObject toJson();
    
    /**
     * 从jsonObject加载数据至当前对象
     */
    T loadFromJson(JSONObject jsonObject);
}
