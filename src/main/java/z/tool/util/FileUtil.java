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
package z.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.IOUtils;

/**
 * 文件工具类
 * @author auzll
 */
public final class FileUtil {
    
    /**
     * 复制文件
     */
    public static void copy(String src, String dest) throws IOException {
        copy(new File(src), new File(dest));
    }
    
    /**
     * 复制文件
     */
    public static void copy(File src, File dest) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            
            copy(fis, fos);
            
        } finally {
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(fos);
        }
    }
    
    /**
     * 复制文件
     */
    public static void copy(FileInputStream fis, FileOutputStream fos) throws IOException {
        FileChannel channel = fis.getChannel();
        channel.transferTo(0, channel.size(), fos.getChannel());
    }
}
