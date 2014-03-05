package games.objects;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/20/14.
 */
public class World {
    private boolean isBgColor = false;
    private Color bgcolor = Color.white;
    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    private String worldname = "<untitled>";
    private Image image;
    private ArrayList<Object> info = new ArrayList<Object>();
    private ArrayList<Image> costumes = new ArrayList<Image>();
    private int costumeindex = 0;
    public World(Image background, String worldname){
        isBgColor = false;
        this.costumes.add(background);
    }
    public World(Color bgcolor,String worldname){
        isBgColor = true;
        this.bgcolor = bgcolor;
    }
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
    public void relayerSpriteTo(Sprite s,int to){
        if (sprites.size() <= to)return;
        sprites.remove(s);
        sprites.add(to,s);
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
    public int getCostumeindex() {
        return costumeindex;
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
