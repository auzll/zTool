/**
 * 
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
