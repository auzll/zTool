/**
 * http://auzll.iteye.com
 */
package z.tool.util.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
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

    public void draw(BufferedImage buffered) throws IOException {
        Graphics graphics = buffered.getGraphics();

        if (null != font) {
            graphics.setFont(font);
        }
        if (null != color) {
            graphics.setColor(color);
        }

        graphics.drawString(text, x, y);
    }

    public static MergeableText of(String text, Font font, Color color, int x,
            int y) {
        return new MergeableText(text, font, color, x, y);
    }
}
