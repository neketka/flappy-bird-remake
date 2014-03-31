import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.swing.*;
import java.io.*;
import java.security.Key;
import java.util.ArrayList;

/**
 * Created by Nikita on 3/2/14.
 */
public class gameDataExporter{
    final String hscore = "#HIGHSCORE ";
    final String ulktstore = "#STOREUNLOCK ";
    final String activegm = "#ACTIVEGMDE ";
    final String chosenbrdpth = "#BIRDPATH ";
    final String coinss = "#COINS ";
    final String allbirds = "#BIRD ";
    final String usr = "#USER ";
    public void composeExport(int highscore,ArrayList<Integer> storeunlocks,int activegamemode,String birdpath,int coins,ArrayList<String> birdpaths) throws IOException {
        ArrayList<String> file = new ArrayList<String>();
        file.add(hscore+"0x"+Integer.toHexString(highscore));
        for (int i : storeunlocks){
            file.add(ulktstore+"0x"+Integer.toHexString(i));
        }
        file.add(activegm+"0x"+Integer.toHexString(activegamemode));
        file.add(chosenbrdpth+birdpath);
        file.add(coinss+"0x"+Integer.toHexString(coins));
        for (String i : birdpaths){
            file.add(allbirds+i);
        }
        file.add(usr+System.getProperty("user.name"));
        encode(file);
    }
    public void encode(ArrayList<String> file) throws IOException {
        String dataFolder;
        if (!System.getProperty("os.name").toLowerCase().startsWith("windows")){
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Choose the folder where the flappybird folder is or will be");
            chooser.showOpenDialog(null);
            dataFolder = chooser.getSelectedFile().getPath();
        }
        else {
            dataFolder = System.getenv("APPDATA");
        }
        FileOutputStream stream = new FileOutputStream(new File(dataFolder+"\\flappybird\\gamefo.txt"));
        PrintWriter writer = new PrintWriter(stream);
        for(String i : file){
            try {
                writer.println(encrypt(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer.close();
        stream.close();
    }
    public String encrypt(String Data) throws Exception {
        Key key = gameDataParser.generateKey();
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
}
