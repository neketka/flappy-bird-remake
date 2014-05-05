import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.io.*;
import java.security.Key;
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
    final String usr = "#USER ";
    String[] steitems = {"2 coins per 1","3 coins per 1","Decreased coin spread","Pipe X distance increase","Pipe Y distance increase","Green bird","Blue bird","Extreme mode"};
    int[] steitemsval = {150,200,250,300,400,50,50,10};
    String[] descitems = {"Double your coin value!",
            "Triple your coin value!",
    "Makes the coin spawn closer to you!",
    "Increase the distance between the pipes!",
    "Increase the distance between the top and bottom pipes!",
    "Get a green bird as an option!",
    "Get a blue bird as an option!","Fast-paced gamemode with smaller jumps, faster pipes,\nand more gravity pull."};
    int[] levelreq = {10,20,40,30,60,10,10,10};
    //file values
    int highScore = 0;
    int coins = 0;
    String birdpath = "bird/bird.gif";
    int gamemode = 0;
    ArrayList<Integer> unlocked = new ArrayList<Integer>();
    ArrayList<Integer> selectedbuy = new ArrayList<Integer>();
    ArrayList<String> birds = new ArrayList<String>();
    public void getFile() throws IOException {
        if (!System.getProperty("os.name").toLowerCase().startsWith("windows")){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Choose the folder where the flappybird folder is or will be");
            chooser.showOpenDialog(null);
            gameVars.dataFolder = chooser.getSelectedFile();
        }
        else {
            gameVars.dataFolder = new File(System.getenv("APPDATA"));
        }
        File g1 = new File(gameVars.dataFolder.getPath()+"/flappybird");
        File g = new File(gameVars.dataFolder.getPath()+"/flappybird/gamefo.txt");
        File g2 = new File(gameVars.dataFolder.getPath()+"/flappybird/birds");
        if (!g2.exists()){
            g2.mkdir();
        }
        if (!g1.exists()){
            g1.mkdir();
            g.createNewFile();
        }
        if (!g.exists()){
            g.createNewFile();
        }
        Scanner scanner = new Scanner(new File(gameVars.dataFolder.getPath()+"/flappybird/gamefo.txt"));
        ArrayList<String> file = new ArrayList<String>();
        while (scanner.hasNextLine()){
            file.add(scanner.nextLine());
        }
        ArrayList<String> dfile = new ArrayList<String>();
        for(String i : file){
            try {
                dfile.add(decrypt(i));
            } catch (Exception e) {
                Logger.getInstance().log("For input \""+i+"\"");
                Logger.getInstance().log(e);
                ExceptionHandler.showLog();
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
        String cmdName = usr+System.getProperty("user.name");
        for (String i : list){
            if (i.startsWith(hscore) && list.contains(cmdName)){
                String ctable = i.replace(hscore,"");
                highScore = Integer.decode(ctable);
            }
            else if (i.startsWith(ulktstore) && list.contains(cmdName)){
                String ctable = i.replace(ulktstore,"");
                unlocked.add(Integer.decode(ctable));
            }
            else if (i.startsWith(activegm) && list.contains(cmdName)){
                String ctable = i.replace(activegm,"");
                gamemode = Integer.decode(ctable);
            }
            else if (i.startsWith(chosenbrdpth) && list.contains(cmdName)){
                String ctable = i.replace(chosenbrdpth,"");
                birdpath = ctable;
            }
            else if (i.startsWith(coinss) && list.contains(cmdName)){
                String ctable = i.replace(coinss,"");
                coins = Integer.decode(ctable);
            }
            else if (i.startsWith(allbirds) && list.contains(cmdName)){
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

    public String[] getEULA(){
        String[] eula =
                {
                "End user licence for flappybird remake made",
                "by neketka, cooleo911,and unknown",
                "I: You may not by any means modify, decompile,",
                "reverse engineer any of the code",
                "if issued by a JAR file. However this project",
                "is open-source so use the code provided",
                "by JAVA files. This project is protected by",
                "the Eclipse public license. Give credit.",
                "II: The provided save data is encrypted for",
                " authentication by any figure that it started",
                "with and will not work with other users.",
                " The game data will be erased if any incompatible",
                "game data is detected. This game data is not portable.",
                "III: We are not responsible for any damage",
                "inflicted by use of this product including but",
                "not limited to computer damage, home",
                "burn-down, injuries or death.",
                };
        return eula;
    }
}
