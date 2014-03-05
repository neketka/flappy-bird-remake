package games.utils;

/**
 * Created by Nikita on 2/15/14.
 */
import games.objects.Sprite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.text.AttributedCharacterIterator;
import javax.swing.ImageIcon;

public class ImageUtils {
    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

    public static Image rotate(BufferedImage img,int b,int a, int angle) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg =new BufferedImage(w, h,img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.rotate(Math.toRadians(angle), w/2, h/2);
        g.drawImage(img, null, 0, 0);
        return dimg;
    }

    public static BufferedImage horizontalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getType());
        Graphics2D g = dimg.createGraphics();
        /**
         * img - the specified image to be drawn. This method does nothing if
         * img is null. dx1 - the x coordinate of the first corner of the
         * destination rectangle. dy1 - the y coordinate of the first corner of
         * the destination rectangle. dx2 - the x coordinate of the second
         * corner of the destination rectangle. dy2 - the y coordinate of the
         * second corner of the destination rectangle. sx1 - the x coordinate of
         * the first corner of the source rectangle. sy1 - the y coordinate of
         * the first corner of the source rectangle. sx2 - the x coordinate of
         * the second corner of the source rectangle. sy2 - the y coordinate of
         * the second corner of the source rectangle. observer - object to be
         * notified as more of the image is scaled and converted.
         *
         */
        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
        g.dispose();
        return dimg;
    }

    /**
     * This method flips the image vertically
     * @param img --> BufferedImage object to be flipped
     * @return
     */
    public static BufferedImage verticalflip(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(w, h, img.getColorModel()
                .getTransparency());
        Graphics2D g = dimg.createGraphics();
        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
        g.dispose();
        return dimg;
    }
    public static Image drawStringOnImage(Image image,String string,int x, int y){
        BufferedImage image1 = toBufferedImage(image);
        Graphics2D g = image1.createGraphics();
        g.drawString(string,x,y);
        return image1;
    }
}
