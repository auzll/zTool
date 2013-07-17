/**
 * https://github.com/auzll/zTool
 */
package z.tool.entity.json;

/**
 * @author auzll
 *
 */
public interface JsonFactoryInterface {
    JsonObject newJsonObject();

    JsonObject parseObject(String text);

    JsonArray newJsonArray();

    JsonArray parseArray(String text);
}
