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
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import z.tool.checker.HumanNeededError;
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
    public static void mergeResource(int bufferedImageType, File srcImageFile, ImageType destType, File destImageFile, Mergeable mergeable, Mergeable... mergeables) {
        mergeResource(bufferedImageType, srcImageFile, destType, destImageFile, 0, 0, mergeable, mergeables);
    }
    
    /**
     * 合并图像(被合并的资源可以是图片或文字)
     */
    public static void mergeResource(int bufferedImageType, File srcImageFile, ImageType destType, File destImageFile, int newWidth, int newHeight, Mergeable mergeable, Mergeable... mergeables) {
        if (null == srcImageFile || !srcImageFile.exists()) {
            throw new IllegalArgumentException("srcImageFile is null or not exist");
        }
        
        if (null == destImageFile) {
            throw new IllegalArgumentException("destImageFile is null");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcImageFile);
            outputStream = new FileOutputStream(destImageFile);
            mergeResource(bufferedImageType, inputStream, destType, outputStream, newHeight, newWidth, mergeable, mergeables);
        } catch (IOException e) {
            LOG.error("method:mergeResource,srcImageFile:" + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                    + ",destImageFile:" + (null != destImageFile ? destImageFile.getAbsolutePath() : null)
                    + ",destType:" + destType
                    + ",newWidth:" + newWidth
                    + ",newHeight:" + newHeight
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
    
    /**
     * 合并图像(被合并的资源可以是图片或文字)
     */
    public static void mergeResource(int bufferedImageType, InputStream inputStream, ImageType destType, OutputStream outputStream, Mergeable mergeable, Mergeable... mergeables) {
        mergeResource(bufferedImageType, inputStream, destType, outputStream, 0, 0, mergeable, mergeables);
    }
    
    /**
     * 合并图像(被合并的资源可以是图片或文字)
     */
    public static void mergeResource(int bufferedImageType, InputStream inputStream, ImageType destType, OutputStream outputStream, int newHeight, int newWidth, Mergeable mergeable, Mergeable... mergeables) {
        if (null == inputStream) {
            throw new IllegalArgumentException("inputStream is null");
        }
        
        if (null == destType) {
            throw new IllegalArgumentException("destType is null");
        }
        
        if (null == outputStream) {
            throw new IllegalArgumentException("outputStream is null");
        }
        
        try {
            Image srcImage = ImageIO.read(inputStream);
            
            // 原始图片大小
            int srcImageWidth = srcImage.getWidth(null);
            int srcImageHeight = srcImage.getHeight(null);
            
            // 若未指定合并后的大小，则使用原图大小
            if (0 == newWidth || 0 == newHeight) {
                newWidth = srcImageWidth;
                newHeight = srcImageHeight;
            }
            
            BufferedImage distImage = new BufferedImage(newWidth, newHeight, bufferedImageType);
            
            // 绘制新图
            Graphics2D graphics2d = distImage.createGraphics();
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            graphics2d.drawImage(srcImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
            
            // 绘制待合并的资源(可以是图片或文字)
            mergeable.draw(graphics2d);
            
            // 绘制待合并的资源(可以是图片或文字)
            if (null != mergeables && mergeables.length > 0) {
                for (Mergeable d : mergeables) {
                    d.draw(graphics2d);
                }
            }
            
            // 输出到文件流
            ImageIO.write(distImage, destType.name(), outputStream);
        } catch (IOException e) {
            LOG.error("method:mergeResource,destType:"  + destType
                    + ",newHeight:"  + newHeight
                    + ",newWidth:"  + newWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        }
    }
    
    /**
     * 等比例缩放图片
     */
    public static void resize(File srcImageFile, ImageType destType, File destImageFile, int maxNewWidth, int maxNewHeight) {
        if (null == srcImageFile || !srcImageFile.exists()) {
            throw new IllegalArgumentException("srcImageFile is null or not exist");
        }
        
        if (null == destImageFile) {
            throw new IllegalArgumentException("destImageFile is null");
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(srcImageFile);
            outputStream = new FileOutputStream(destImageFile);
            resize(inputStream, destType, outputStream, maxNewWidth, maxNewHeight);
        } catch (IOException e) {
            LOG.error("method:resize,srcImageFile:" + (null != srcImageFile ? srcImageFile.getAbsolutePath() : null)
                    + ",destImageFile:" + (null != destImageFile ? destImageFile.getAbsolutePath() : null)
                    + ",destType:" + destType
                    + ",maxNewHeight:" + maxNewHeight
                    + ",maxNewWidth:" + maxNewWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
    
    /**
     * 等比例缩放图片
     */
    public static void resize(InputStream inputStream, ImageType destType, OutputStream outputStream, int maxNewWidth, int maxNewHeight) {
        if (null == inputStream) {
            throw new IllegalArgumentException("inputStream is null");
        }
        
        if (null == destType) {
            throw new IllegalArgumentException("destType is null");
        }
        
        if (null == outputStream) {
            throw new IllegalArgumentException("outputStream is null");
        }
        
        try {
            Image srcImage = ImageIO.read(inputStream);
            
            // 原始图片大小
            int srcImageWidth = srcImage.getWidth(null);
            int srcImageHeight = srcImage.getHeight(null);
            
            if (0 == maxNewWidth || 0 == maxNewHeight 
                    || (srcImageWidth <= maxNewWidth && srcImageHeight <= maxNewHeight)) {
                maxNewWidth = srcImageWidth;
                maxNewHeight = srcImageHeight;
            } else {
                // 原始宽高超过设定的最大宽高
                // 计算缩放大小
                if (srcImageWidth >= srcImageHeight) {
                    // 原始图片的宽度大于高度，需要缩小缩放后的最大高度
                    maxNewHeight = (int) Math.round((srcImageHeight * maxNewWidth * 1.0 / srcImageWidth));
                } else {
                    // 否则，缩小缩放后的最大宽度
                    maxNewWidth = (int) Math.round((srcImageWidth * maxNewHeight * 1.0 / srcImageHeight));
                }
            }
            
            BufferedImage distImage = new BufferedImage(maxNewWidth, maxNewHeight, BufferedImage.TYPE_INT_ARGB_PRE);
            
            Graphics2D graphics2d = distImage.createGraphics();
            graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 绘制新图
            graphics2d.drawImage(srcImage.getScaledInstance(maxNewWidth, maxNewHeight, Image.SCALE_SMOOTH), 0, 0, null);
            
            // 输出到文件流
            ImageIO.write(distImage, destType.name(), outputStream);
        } catch (IOException e) {
            LOG.error("method:resize,destType:" + destType
                    + ",maxNewHeight:" + maxNewHeight
                    + ",maxNewWidth:" + maxNewWidth
                    + ",errorMsg:" + e.getMessage(), e);
            throw new HumanNeededError(Error.IO_ERROR);
        }
    }
}
