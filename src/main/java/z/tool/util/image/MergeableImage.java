/**
 * http://auzll.iteye.com
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
