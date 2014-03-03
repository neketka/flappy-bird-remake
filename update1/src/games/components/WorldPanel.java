package games.components;

import games.objects.Sprite;
import games.objects.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/15/14.
 */
public abstract class WorldPanel extends JPanel {
    public WorldPanel(World world, boolean withPaint){
        this.world = world;
        if (withPaint){
            this.update();
        }
    }
    public WorldPanel(){
    }
    protected abstract void onUpdate();
    protected abstract void afterUpdate();
    public void update(){
        onUpdate();
        paint(getGraphics());
        afterUpdate();
    }
    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        if (world.isBgColor()){
            graphics.setColor(world.getBgcolor());
            graphics.fillRect(0,0,this.getWidth(),this.getHeight());
        }
        else {
            graphics.drawImage(world.getBgImage(),0,0,this.getWidth(),this.getHeight(),null);
        }
        for (Sprite s1:world.getSprites()){
            if (s1.isVisible()){
                graphics.drawImage(s1.getImage(),(int)s1.getLocation().getX(),(int)s1.getLocation().getY(),(int)s1.getSize().getWidth(),(int)s1.getSize().getHeight(),null);
            }
        }
        g.drawImage(image,0,0,null);
    }
    private World world = null;
    public void setWorld(World world) {
        this.world = world;
    }
    public World getWorld(){
        return world;
    }
}
