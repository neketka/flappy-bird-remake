import com.sun.xml.internal.fastinfoset.algorithm.HexadecimalEncodingAlgorithm;
import games.objects.Sprite;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.print.DocFlavor;
import javax.swing.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
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
    String[] steitems = {"2 coins per 1","3 coins per 1","Decreased coin spread"};
    int[] steitemsval = {150,250,400};
    String[] descitems = {"Double your coin value!\nRequirements:\nHigh score of at least 10,\n150 coins.",
            "Triple your coin value!\nRequirements:\nHigh score of at least 20,\n250 coins.",
    "Makes the coin spawn closer to you!\nRequirements\nHigh score of at least 50,\n400 coins."};
    int[] levelreq = {10,20,50};
    //file vals
    int highScore = 0;
    int coins = 0;
    String birdpath = "bird/bird.png";
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
        Scanner scanner = new Scanner(new File(dataFolder+"\\flappybird\\gamefo.txt"));
        ArrayList<String> file = new ArrayList<String>();
        while (scanner.hasNextLine()){
            file.add(scanner.nextLine());
        }
        ArrayList<String> dfile = new ArrayList<String>();
        for(String i : file){
            try {
                dfile.add(decrypt(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parse(dfile);
    }
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
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
    private static final byte[] keyValue =
            new byte[] { 'T', 'h', 'e', 'B', 'e', 's', 't',
                    'S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
    public static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, "AES");
        return key;
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
