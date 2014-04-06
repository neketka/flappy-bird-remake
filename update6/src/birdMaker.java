import sun.misc.Hashing;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Nikita on 3/1/14.
 */
public class birdMaker {
    public static String getNewBird(Color color,String colorname) throws IOException {
        BufferedImage image = new BufferedImage(25,25,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, 25, 25);
        g.setColor(color);
        g.fillOval(0, 3, 24, 18);
        g.setColor(Color.white);
        g.fillOval(9, 4, 10, 8);
        g.setColor(Color.black);
        g.fillOval(13, 5, 5, 5);
        g.setColor(new Color(16,255,192));
        g.fillOval(19, 8, 6, 4);
        g.fillOval(19,12,6,4);
        g.setColor(Color.PINK);
        g.fillOval(-3, 9, 12, 7);
        BufferedImage bufferedImage = new BufferedImage(50,50,BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image,0,0,50,50,null);
        ImageIO.write(image,"png",new File(gameVars.dataFolder.getPath()+"/flappybird/birds/"+colorname+"_bird.png"));
        return colorname+"_bird.png";
    }
}
