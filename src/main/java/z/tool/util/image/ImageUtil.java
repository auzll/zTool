/**
 * http://auzll.iteye.com
 */
package z.tool.util.image;

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
import z.tool.entity.enums.Error;

/**
 * 图片工具类
 * @author auzll
 */
public final class ImageUtil {
    private static final Logger LOG = Logger.getLogger(ImageUtil.class);
    
    /**
     * 合并图像(被合并的资源可以是图片或文字)
     */
    public static void mergeResource(File srcImageFile, ImageType destType, File destImageFile, Mergeable mergeable, Mergeable... mergeables) {
        mergeResource(srcImageFile, destType, destImageFile, 0, 0, mergeable, mergeables);
    }
    
    /**
     * 合并图像(被合并的资源可以是图片或文字)
     */
    public static void mergeResource(File srcImageFile, ImageType destType, File destImageFile, int newHeight, int newWidth, Mergeable mergeable, Mergeable... mergeables) {
        if (null == srcImageFile || !srcImageFile.exists()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:resize,srcImageFile:" 
                        + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                        + ",descr:file not found");
            }
            throw new LogicError("合并的主文件不存在");
        }
        
        if (null == destType) {
            throw new LogicError("未指定合并图片类型");
        }
        
        if (null == destImageFile) {
            throw new LogicError("合并的目标文件为空");
        }
        
        try {
            Image srcImage = ImageIO.read(srcImageFile);
            
            // 原始图片大小
            int srcImageWidth = srcImage.getWidth(null);
            int srcImageHeight = srcImage.getHeight(null);
            
            // 若未指定合并后的大小，则使用原图大小
            if (0 == newWidth || 0 == newHeight) {
                newWidth = srcImageWidth;
                newHeight = srcImageHeight;
            }
            
            BufferedImage distImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            
            // 绘制新图
            distImage.getGraphics().drawImage(srcImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            
            // 绘制待合并的资源(可以是图片或文字)
            mergeable.draw(distImage);
            
            // 绘制待合并的资源(可以是图片或文字)
            if (null != mergeables && mergeables.length > 0) {
                for (Mergeable d : mergeables) {
                    d.draw(distImage);
                }
            }
            
            // 输出到文件流
            OutputStream os = null;
            try {
                os = new FileOutputStream(destImageFile);
                ImageIO.write(distImage, destType.name(), os);
                os.flush();
            } finally {
                IOUtils.closeQuietly(os);
            }
        } catch (IOException e) {
            LOG.error("method:mergeResource,srcImageFile:" + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                    + ",destImageFile:"  + (null != destImageFile ? destImageFile.getAbsolutePath() : null)
                    + ",destType:"  + destType
                    + ",newHeight:"  + newHeight
                    + ",newWidth:"  + newWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        }
    }
    
    /**
     * 等比例缩放图片
     */
    public static void resize(File srcImageFile, ImageType destType, File destImageFile, int maxNewHeight, int maxNewWidth) {
        if (null == srcImageFile || !srcImageFile.exists()) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("method:resize,srcImageFile:" 
                        + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                        + ",descr:file not found");
            }
            throw new LogicError("缩放的源文件不存在");
        }
        
        if (null == destType) {
            throw new LogicError("未指定缩放图片类型");
        }
        
        if (null == destImageFile) {
            throw new LogicError("缩放的目标文件为空");
        }
        
        if (maxNewHeight < 1 | maxNewWidth < 1) {
            throw new LogicError("缩放尺寸错误，不能小于0");
        }
        
        try {
            Image srcImage = ImageIO.read(srcImageFile);
            
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
                os = new FileOutputStream(destImageFile);
                ImageIO.write(distImage, destType.name(), os);
                os.flush();
            } finally {
                IOUtils.closeQuietly(os);
            }
        } catch (IOException e) {
            LOG.error("method:resize,srcImageFile:" + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                    + ",destImageFile:" + (null != destImageFile ? destImageFile.getAbsolutePath() : null)
                    + ",destType:" + destType
                    + ",maxNewHeight:" + maxNewHeight
                    + ",maxNewWidth:" + maxNewWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        }
    }
}
