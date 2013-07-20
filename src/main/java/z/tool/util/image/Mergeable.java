/**
 * http://auzll.iteye.com
 */
package z.tool.util.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 
 * @author auzll
 * @since 2013-7-20 上午11:40:38
 */
public interface Mergeable {
    void draw(BufferedImage buffered) throws IOException;
}
