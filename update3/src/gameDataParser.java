import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Created by Nikita on 2/28/14.
 */
public class gameDataParser {
    final String hscore = "#HIGHSCORE ";
    final String ulktstore = "#STOREUNLOCK ";
    final String activegm = "#ACTIVEGMDE ";
    final String chosenbrdpth = "#BIRDPATH ";
    final String coinss = "#COINS ";
    final String allbirds = "#BIRD ";
    String[] steitems = {"2 Coins per 1","3 coins per 1"};
    int[] steitemsval = {150,250};
    String[] descitems = {"Double your coin value!\nRequirements:\nHighScore of at least 10,\n150 coins.",
            "Triple your coin value!\nRequirements:\nHighScore of at least 20,\n250 coins."};
    int[] levelreq = {10,20};
    //file vals
    int highScore = 0;
    int coins = 0;
    String birdpath = "bird/bird.gif";
    int gamemode = 0;
    ArrayList<Integer> unlocked = new ArrayList<Integer>();
    ArrayList<Integer> selectedbuy = new ArrayList<Integer>();
    ArrayList<String> birds = new ArrayList<String>();
    public void getFile() throws IOException {
        String dataFolder = System.getenv("APPDATA");
        File g1 = new File(dataFolder+"\\flappybird");
        File g = new File(dataFolder+"\\flappybird\\gamefo.txt");
        File g2 = new File(dataFolder+"\\flappybird\\birds");
        if (g2.exists() == false){
            g2.mkdir();
        }
        if (g1.exists() == false){
            g1.mkdir();
            g.createNewFile();
        }
        if (g.exists() == false){
            g.createNewFile();
        }
        Scanner getter = new Scanner(new FileReader(dataFolder+"\\flappybird\\gamefo.txt"));
        ArrayList<String> file = new ArrayList<String>();
        while (getter.hasNextLine()){
            file.add(getter.nextLine());
        }
        getter.close();
        parse(file);
    }
    public void parse(ArrayList<String> list){
        for (String i : list){
            if (i.startsWith(hscore)){
                String ctable = i.replace(hscore,"");
                highScore = Integer.decode(ctable);
            }
            else if (i.startsWith(ulktstore)){
                String ctable = i.replace(ulktstore,"");
                unlocked.add(Integer.decode(ctable));
            }
            else if (i.startsWith(activegm)){
                String ctable = i.replace(activegm,"");
                gamemode = Integer.decode(ctable);
            }
            else if (i.startsWith(chosenbrdpth)){
                String ctable = i.replace(chosenbrdpth,"");
                birdpath = ctable;
            }
            else if (i.startsWith(coinss)){
                String ctable = i.replace(coinss,"");
                coins = Integer.decode(ctable);
            }
            else if (i.startsWith(allbirds)){
                String ctable = i.replace(allbirds,"");
                birds.add(ctable);
            }
        }
    }

    public ArrayList<Integer> getUnlocked() {
        return unlocked;
    }

    public ArrayList<Integer> getSelectedbuy() {
        return selectedbuy;
    }

    public int getGamemode() {
        return gamemode;
    }

    public String getBirdpath() {
        return birdpath;
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCoins() {
        return coins;
    }

    public ArrayList<String> getBirds() {
        return birds;
    }

    public String[] getStoreitems() {
        return steitems;
    }

    public int[] getStoreCosts() {
        return steitemsval;
    }

    public int[] getLevelRequrements() {
        return levelreq;
    }

    public String[] getDescriptions() {
        return descitems;
    }
}
