package games.objects;

import games.utils.ImageUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
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

    private Image image;

    private Image rotimage;

    private int degrees = 0;

    private String name = "[untitled]";

    private boolean visible = true;

    private ArrayList<Object> info = new ArrayList<Object>();

    public Sprite(int x, int y, int width, int height, int degrees, Image iimage, String name, boolean isVisible) throws IOException{
        r = new Rectangle(x,y,width,height);
        costumes.add(iimage);
        this.image = costumes.get(costumeindex).getScaledInstance(width,height,Image.SCALE_SMOOTH);
        setDegrees(degrees);
        this.name = name;
        this.visible = isVisible;
    }

    public Image getImage(){
        return rotimage;
    }

    public Sprite(Rectangle r, int degrees, Image iimage, String name, boolean isVisible) throws IOException{
        this.r = r;
        costumes.add(iimage);
        this.image = costumes.get(costumeindex).getScaledInstance((int) r.getWidth(), (int) r.getHeight(), Image.SCALE_SMOOTH);
        setDegrees(degrees);
        this.name = name;
        this.visible = isVisible;
    }

    public void setSize(int height,int width) {
        r.setSize(width,height);
        image = costumes.get(costumeindex).getScaledInstance((int)r.getWidth(),(int)r.getHeight(),Image.SCALE_SMOOTH);
    }

    public void setSize(Dimension d) {
        r.setSize(d);
        image = costumes.get(costumeindex).getScaledInstance((int) r.getWidth(), (int) r.getHeight(), Image.SCALE_SMOOTH);
    }

    public void setLocation(int x, int y){
        r.setLocation(x,y);
    }

    public void setLocation(Point p){
        r.setLocation(p);
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
        this.image = costumes.get(costumeindex).getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
    }

    public void setProperties(int x, int y, int width, int height){
        r.setLocation(x,y);
        r.setSize(width, height);
        this.image = costumes.get(costumeindex).getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
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
    public static boolean isOnStage(Sprite s,JPanel panel){
        Rectangle r = new Rectangle(0,0,panel.getWidth(),panel.getHeight());
        return s.getProperties().intersects(r);
    }
    public static boolean isOnTopEdge(Sprite s,JPanel panel){
        Rectangle r = new Rectangle(panel.getWidth()/2,0,panel.getWidth(),1);
        return s.getProperties().intersects(r);
    }
    public static boolean isOnBottomEdge(Sprite s,JPanel panel){
        Rectangle r = new Rectangle(panel.getWidth()/2,panel.getHeight(),panel.getWidth(),1);
        return s.getProperties().intersects(r);
    }
    public static boolean isOnLeftEdge(Sprite s,JPanel panel){
        Rectangle r = new Rectangle(0,panel.getHeight()/2,1,panel.getHeight());
        return s.getProperties().intersects(r);
    }
    public static boolean isOnRightEdge(Sprite s,JPanel panel){
        Rectangle r = new Rectangle(panel.getWidth(),panel.getHeight()/2,1,panel.getHeight());
        return s.getProperties().intersects(r);
    }
    //Costume data
    public void addCostume(Image costume){
        costumes.add(costume);
    }
    public void setCostume(int costumenum){
        this.costumeindex = costumenum;
        this.image = costumes.get(costumeindex).getScaledInstance((int)r.getWidth(), (int)r.getHeight(), Image.SCALE_SMOOTH);
        setDegrees(degrees);
    }
    public void addCostumes(Image[] costumes){
        for(Image i : costumes){
            this.costumes.add(i);
        }
    }
    public void removeCostume(int index){
        if (costumes.size() == 1){
            return;
        }
        else {
            costumes.remove(index);
            if (costumes.size() == 1){
                costumeindex = 0;
            }
        }
    }
    public ArrayList<Image> getCostumes() {
        return costumes;
    }
    public int getCostumeindex() {
        return costumeindex;
    }
    private ArrayList<Image> costumes = new ArrayList<Image>();
    private int costumeindex = 0;

    public static final int horizontalFlip = 0x2F;

    public static final int verticalFlip = 0x3F;
}
