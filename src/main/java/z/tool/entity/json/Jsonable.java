/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.json;



/**
 * @author auzll
 *
 */
public interface Jsonable<T> {
    /**
     * @return 返回JSONObject
     */
    JsonObject toJson();
    
    /**
     * 从jsonObject加载数据至当前对象
     */
    T loadFromJson(JsonObject json);
}
