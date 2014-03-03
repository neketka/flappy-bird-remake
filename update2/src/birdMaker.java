import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nikita on 3/1/14.
 */
public class birdMaker {
    public static Image getNewBird(Color color){
        BufferedImage image = new BufferedImage(26,26,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.black);
        g.drawOval(0, 0, 24, 23);
        g.setColor(color);
        g.fillOval(0, 0,24,22);
        g.setColor(Color.white);
        g.fillOval(9,4,10,8);
        g.setColor(Color.black);
        g.fillOval(13,5,5,5);
        g.setColor(Color.red);
        g.fillOval(19,8,6,4);
        g.fillOval(19,12,6,4);
        g.setColor(Color.PINK);
        g.fillOval(-4,9,12,7);
        return (Image) image;
    }
}
