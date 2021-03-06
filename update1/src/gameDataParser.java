import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.*;

/**
 * Created by Nikita on 2/28/14.
 */
public class gameDataParser {
    final String hscore = "#HIGHSCORE ";
    final String remstore = "#REMOVESTORE ";
    final String ulktstore = "#STOREUNLOCK ";
    final String activebuy = "#ACTIVEBUY ";
    final String activegm = "#ACTIVEGMDE ";
    final String chosenbrdpth = "#BIRDPATH ";
    final String coinss = "#COINS ";
    String[] steitems = {"2 Coins per 1","3 coins per 1"};
    int[] steitemsval = {50,100};
    //file vals
    int highScore = 0;
    int coins = 0;
    String birdpath;
    int gamemode = 0;
    ArrayList<Integer> removedstore = new ArrayList<Integer>();
    ArrayList<Integer> unlocked = new ArrayList<Integer>();
    ArrayList<Integer> selectedbuy = new ArrayList<Integer>();
    public void getFile(){
        Scanner getter = new Scanner(this.getClass().getResourceAsStream("gamefo.txt"));
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
            else if (i.startsWith(remstore)){
                String ctable = i.replace(remstore,"");
                removedstore.add(Integer.decode(ctable));
            }
            else if (i.startsWith(ulktstore)){
                String ctable = i.replace(ulktstore,"");
                unlocked.add(Integer.decode(ctable));
            }
            else if (i.startsWith(activebuy)){
                String ctable = i.replace(activebuy,"");
                selectedbuy.add(Integer.decode(ctable));
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
        }
    }

    public ArrayList<Integer> getUnlocked() {
        return unlocked;
    }

    public ArrayList<Integer> getRemovedstore() {
        return removedstore;
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

    public String[] getStoreitems() {
        return steitems;
    }

    public int[] getStoreCosts() {
        return steitemsval;
    }
}
