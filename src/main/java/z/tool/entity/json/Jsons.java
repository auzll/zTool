/**
 * 
 */
package z.tool.entity.json;

import z.tool.entity.json.ali.JsonArrayImpl;
import z.tool.entity.json.ali.JsonObjectImpl;

/**
 * @author auzll
 *
 */
public final class Jsons {
    private Jsons() {}
    
    public static JsonObject newJsonObject() {
        return new JsonObjectImpl();
    }
    
    public static JsonObject parseObject(String text) {
        return JsonObjectImpl.parseObject(text);
    }
    
    public static JsonArray newJsonArray() {
        return new JsonArrayImpl();
    }

    public static JsonArray parseArray(String text) {
        return JsonArrayImpl.parseArray(text);
    }
    
}
