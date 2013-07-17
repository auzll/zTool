/**
 * https://github.com/auzll/zTool
 * 
 * since 2012-12-26 上午07:37:54
 */
package z.tool.entity.json;


/**
 * @author auzll
 */
public interface ExtJsJsonable {
    /**
     * @return 返回Extjs的TreeStore专用的JSONObject
     */
    JsonObject toExtjsTreeJson();
}
