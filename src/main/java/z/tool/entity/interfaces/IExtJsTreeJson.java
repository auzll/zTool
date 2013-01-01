/**
 * https://github.com/auzll/zTool
 * 
 * since 2012-12-26 上午07:37:54
 */
package z.tool.entity.interfaces;

import net.sf.json.JSONObject;

/**
 * @author auzll
 */
public interface IExtJsTreeJson {
    /**
     * @return 返回Extjs的TreeStore专用的JSONObject
     */
    JSONObject toExtjsTreeJson();
}
