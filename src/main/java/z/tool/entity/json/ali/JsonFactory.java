/**
 * https://github.com/auzll/
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
