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
package z.tool.util.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;

/**
 * @author auzll
 * @since 2013-7-20 上午11:41:39
 */
public final class MergeableText implements Mergeable {
    private String text;
    private Font font;
    private Color color;
    private int x;
    private int y;

    private MergeableText(String text, Font font, Color color, int x, int y) {
        this.text = text;
        this.font = font;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D graphics2d) throws IOException {
        if (null != font) {
            graphics2d.setFont(font);
        }
        if (null != color) {
            graphics2d.setColor(color);
        }

        graphics2d.drawString(text, x, y);
    }

    public static MergeableText of(String text, Font font, Color color, int x,
            int y) {
        return new MergeableText(text, font, color, x, y);
    }
}
