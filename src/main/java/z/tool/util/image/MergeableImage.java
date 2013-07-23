/**
 * http://auzll.iteye.com
 */
package z.tool.util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * @author auzll
 * @since 2013-7-20 上午11:41:06
 */
public final class MergeableImage implements Mergeable {
    private final InputStream inputStream;
    private final File file;
    private final int x;
    private final int y;

    private MergeableImage(InputStream inputStream, File file, int x, int y) {
        this.inputStream = inputStream;
        this.file = file;
        this.x = x;
        this.y = y;
    }

    public void draw(BufferedImage buffered) throws IOException {
        if (null != inputStream) {
            buffered.getGraphics().drawImage(ImageIO.read(inputStream), x, y, null);
        } else if (null != file) {
            buffered.getGraphics().drawImage(ImageIO.read(file), x, y, null);
        }
    }
    
    public static MergeableImage of(InputStream inputStream, int x, int y) {
        return new MergeableImage(inputStream, null, x, y);
    }
    
    public static MergeableImage of(File file, int x, int y) {
        return new MergeableImage(null, file, x, y);
    }
}
