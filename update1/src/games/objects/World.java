package games.objects;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/20/14.
 */
public class World {
    private boolean isBgColor = false;
    private Image bg = null;
    private Color bgcolor = Color.white;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private String worldname = "<untitled>";
    private Image image;
    private ArrayList<Object> info = new ArrayList<Object>();
    public World(Image background, String worldname){
        isBgColor = false;
        this.bg = background;
    }
    public World(Color bgcolor,String worldname){
        isBgColor = true;
        this.bgcolor = bgcolor;
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
    public void setBackground(Image bg){
        this.bg = bg;
    }
    public void setBackground(Color bg){
        this.bgcolor = bg;
    }
    public ArrayList<Sprite> getSprites(){
        return sprites;
    }
    public Color getBgcolor(){
        return bgcolor;
    }
    public Image getBgImage(){
        return bg;
    }
    public boolean isBgColor() {
        return isBgColor;
    }
    public void setWorldName(String worldname){
        this.worldname = worldname;
    }
    public String getWorldname(){
        return worldname;
    }
    public void addSprite(Sprite sprite){
        sprites.add(sprite);
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
}
