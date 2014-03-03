package games.objects;

import games.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/9/14.
 */
public class Sprite {
    private Rectangle r;

    private Image originalimage;

    private Image image;

    private Image rotimage;

    private int degrees = 0;

    private String name = "[untitled]";

    private boolean visible = true;

    private ArrayList<Object> info = new ArrayList<Object>();

    public Sprite(int x, int y, int width, int height, int degrees, Image image, String name, boolean isVisible) throws IOException{
        r = new Rectangle(x,y,width,height);
        this.originalimage = image;
        this.image = originalimage.getScaledInstance(width,height,Image.SCALE_SMOOTH);
        setDegrees(degrees);
        this.name = name;
        this.visible = isVisible;
    }

    public Sprite(Rectangle r, int degrees, Image image, String name, boolean isVisible) throws IOException{
        this.r = r;
        this.originalimage = image;
        this.image = originalimage.getScaledInstance((int)r.getWidth(),(int)r.getHeight(),Image.SCALE_SMOOTH);
        setDegrees(degrees);
        this.name = name;
        this.visible = isVisible;
    }

    public void setSize(int height,int width) {
        r.setSize(width,height);
        image = originalimage.getScaledInstance((int)r.getWidth(),(int)r.getHeight(),Image.SCALE_SMOOTH);
    }

    public void setSize(Dimension d) {
        r.setSize(d);
        image = originalimage.getScaledInstance((int)r.getWidth(),(int)r.getHeight(),Image.SCALE_SMOOTH);
    }

    public void setLocation(int x, int y){
        r.setLocation(x,y);
    }

    public void setLocation(Point p){
        r.setLocation(p);
    }

    public Image getImage() {
        return rotimage;
    }

    public Dimension getSize(){
        return r.getSize();
    }

    public Point getLocation(){
        return r.getLocation();
    }

    public Rectangle getProperties(){
        return r;
    }

    public void setProperties(Rectangle r){
        this.r = r;
        this.image = originalimage.getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
    }

    public void setProperties(int x, int y, int width, int height){
        r.setLocation(x,y);
        r.setSize(width, height);
        this.image = originalimage.getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
    }

    public void setDegrees(int degrees) {
        rotimage = ImageUtils.rotate(ImageUtils.toBufferedImage(image), 0, 0, degrees);
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(Image image) {
        this.originalimage = image;
        this.image = originalimage.getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
        setDegrees(degrees);
    }

    public String getName() {
        return name;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public static boolean isCollided(Sprite s1,Sprite s2){
        if(s1.getProperties().intersects(s2.getProperties())&&s2.getProperties().intersects(s1.getProperties())){
            return true;
        }
        return false;
    }

    public void addInfo(Object info){
        this.info.add(info);
    }

    public ArrayList<Object> getInfo() {
        return info;
    }

    public void clearInfo() {
        this.info.clear();
    }
    public void flip(int flipType) throws Exception {
        if (flipType == horizontalFlip){
            image = ImageUtils.horizontalflip(ImageUtils.toBufferedImage(image));
        }
        else if (flipType == verticalFlip){
            image = ImageUtils.verticalflip(ImageUtils.toBufferedImage(image));
        }
        else {
            throw new Exception("Unknown type of flip");
        }
    }

    public static final int horizontalFlip = 0x2F;

    public static final int verticalFlip = 0x3F;
}
