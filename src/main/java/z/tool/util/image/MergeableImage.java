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

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author auzll
 * @since 2013-7-20 上午11:41:06
 */
public final class MergeableImage implements Mergeable {
    private File file;
    private int x;
    private int y;

    private MergeableImage(File file, int x, int y) {
        this.file = file;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D graphics2d) throws IOException {
        graphics2d.drawImage(ImageIO.read(file), x, y, null);
    }

    public static MergeableImage of(File file, int x, int y) {
        return new MergeableImage(file, x, y);
    }
}
