/**
 * http://auzll.iteye.com
 */
package z.tool.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import z.tool.checker.HumanNeededError;
import z.tool.checker.LogicError;

/**
 * 图片工具类
 * @author auzll
 */
public final class ImageUtil {
    private static final Logger LOG = Logger.getLogger(ImageUtil.class);
    
    /**
     * 从{@link javax.imageio.spi.ImageReaderWriterSpi}的实现类中整理出来所有支持的图片类型
     */
    public static enum ImageType {
        JPEG, PNG, GIF, WBMP
    }
    
    /**
     * 等比例缩放图片(Jpeg)
     */
    public static void resizeAsJpeg(File srcFile, File destFile, int maxNewHeight, int maxNewWidth) {
        resize(srcFile, ImageType.JPEG, destFile, maxNewHeight, maxNewWidth);
    }
    
    /**
     * 等比例缩放图片(Png)
     */
    public static void resizeAsPng(File srcFile, File destFile, int maxNewHeight, int maxNewWidth) {
        resize(srcFile, ImageType.PNG, destFile, maxNewHeight, maxNewWidth);
    }
    
    /**
     * 等比例缩放图片(Gif)
     */
    public static void resizeAsGif(File srcFile, File destFile, int maxNewHeight, int maxNewWidth) {
        resize(srcFile, ImageType.GIF, destFile, maxNewHeight, maxNewWidth);
    }
    
    /**
     * 等比例缩放图片(Wbmp)
     */
    public static void resizeAsWbmp(File srcFile, File destFile, int maxNewHeight, int maxNewWidth) {
        resize(srcFile, ImageType.WBMP, destFile, maxNewHeight, maxNewWidth);
    }
    
    /**
     * 等比例缩放图片
     */
    public static void resize(File srcFile, ImageType destType, File destFile, int maxNewHeight, int maxNewWidth) {
        if (null == srcFile || !srcFile.exists()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:resize,srcFile:" 
                        + (null != srcFile ? srcFile.getAbsolutePath() : null)
                        + ",descr:file not found");
            }
            throw new LogicError("缩放的源文件不存在");
        }
        
        if (null == destType) {
            throw new LogicError("未指定缩放图片类型");
        }
        
        if (null == destFile) {
            throw new LogicError("缩放的目标文件为空");
        }
        
        if (maxNewHeight < 1 | maxNewWidth < 1) {
            throw new LogicError("缩放尺寸错误，不能小于0");
        }
        
        try {
            Image srcImage = ImageIO.read(srcFile);
            
            // 原始图片大小
            int srcImageWidth = srcImage.getWidth(null);
            int srcImageHeight = srcImage.getHeight(null);
            
            // 原始宽高超过设定的最大宽高
            if (srcImageWidth > maxNewWidth || srcImageHeight > maxNewHeight) {
                
                // 计算缩放大小
                if (srcImageWidth >= srcImageHeight) {
                    // 原始图片的宽度大于高度，需要缩小缩放后的最大高度
                    maxNewHeight = (int) Math.round((srcImageHeight * maxNewWidth * 1.0 / srcImageWidth));
                } else {
                    // 否则，缩小缩放后的最大宽度
                    maxNewWidth = (int) Math.round((srcImageWidth * maxNewHeight * 1.0 / srcImageHeight));
                }
                
            }
            
            BufferedImage distImage = new BufferedImage(maxNewWidth, maxNewHeight, BufferedImage.TYPE_INT_RGB);
            
            // 绘制新图
            distImage.getGraphics().drawImage(srcImage.getScaledInstance(maxNewWidth, maxNewHeight, Image.SCALE_SMOOTH), 0, 0, null);
            
            // 输出到文件流
            OutputStream os = null;
            try {
                os = new FileOutputStream(destFile);
                ImageIO.write(distImage, destType.name(), os);
                os.flush();
            } finally {
                IOUtils.closeQuietly(os);
            }
        } catch (IOException e) {
            LOG.error("method:resize,srcFile:" + (null != srcFile ? srcFile.getAbsolutePath() : null)
                    + ",destFile:"  + (null != destFile ? destFile.getAbsolutePath() : null)
                    + ",destType:"  + destType
                    + ",maxNewHeight:"  + maxNewHeight
                    + ",maxNewWidth:"  + maxNewWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError("图片缩放失败");
        }
    }
}
