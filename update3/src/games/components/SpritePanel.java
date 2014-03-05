package games.components;

import games.objects.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/15/14.
 */
public abstract class SpritePanel extends JPanel {
    private boolean isBgColor = false;
    private Color bgcolor = Color.white;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    public SpritePanel(Image background, boolean withPaint){
        isBgColor = false;
        costumes.add(background);
        if (withPaint){
            this.update();
        }
    }
    public SpritePanel(Color bgcolor, boolean withPaint){
        isBgColor = true;
        this.bgcolor = bgcolor;
        if (withPaint){
            this.repaint();
        }
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
        if (isBgColor){
            graphics.setColor(bgcolor);
            graphics.fillRect(0,0,this.getWidth(),this.getHeight());
        }
        else {
            graphics.drawImage(costumes.get(costumeindex),0,0,this.getWidth(),this.getHeight(),null);
        }
        for (Sprite s1:sprites){
            if (s1.isVisible()){
                graphics.drawImage(s1.getImage(),(int)s1.getLocation().getX(),(int)s1.getLocation().getY(),(int)s1.getSize().getWidth(),(int)s1.getSize().getHeight(),null);
            }
        }
        g.drawImage(image,0,0,null);
    }
    public void addSprites(Sprite[] sprites){
        for (Sprite sprite: sprites){
            this.sprites.add(sprite);
        }
    }
    public void destroySpriteByName(String sprites1){
        for (int i = 0;i<sprites.size();i++){
            Sprite spritei = sprites.get(i);
            if (spritei.getName() == sprites1){
                sprites.remove(i);
            }
        }
    }
    public void destroySpriteByObject(Sprite sprite){
        for (Sprite sprite1:sprites){
            if (sprite1 == sprite) {
                sprites.remove(sprite);
                return;
            }
        }
    }
    public void destroyAllSprites(){
        sprites.clear();
    }
    public void setBackground(Color bg){
        this.bgcolor = bg;
        isBgColor = true;
    }
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }
    public Color getBgcolor(){
        return bgcolor;
    }
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
    }
    //Costume data
    public void addCostume(Image costume){
        costumes.add(costume);
    }
    public void setCostume(int costumenum){
        this.costumeindex = costumenum;
        isBgColor = false;
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
    public void relayerSpriteTo(Sprite s,int to){
        if (sprites.size() <= to)return;
        sprites.remove(s);
        sprites.add(to,s);
    }
    public ArrayList<Image> getCostumes() {
        return costumes;
    }
    public int getCostumeindex() {
        return costumeindex;
    }
    private ArrayList<Image> costumes = new ArrayList<Image>();
    private int costumeindex = 0;
}
