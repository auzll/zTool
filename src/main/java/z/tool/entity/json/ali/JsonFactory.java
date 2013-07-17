/**
 * 
 */
package z.tool.entity.json.ali;

import z.tool.entity.json.JsonArray;
import z.tool.entity.json.JsonFactoryInterface;
import z.tool.entity.json.JsonObject;

/**
 * @author auzll
 *
 */
public class JsonFactory implements JsonFactoryInterface {

    public JsonObject newJsonObject() {
        return new JsonObjectImpl();
    }

    public JsonObject parseObject(String text) {
        return JsonObjectImpl.parseObject(text);
    }

    public JsonArray newJsonArray() {
        return new JsonArrayImpl();
    }

    public JsonArray parseArray(String text) {
        return JsonArrayImpl.parseArray(text);
    }

}
