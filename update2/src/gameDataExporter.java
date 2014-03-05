import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Nikita on 3/2/14.
 */
public class gameDataExporter{
    final String hscore = "#HIGHSCORE ";
    final String ulktstore = "#STOREUNLOCK ";
    final String activebuy = "#ACTIVEBUY ";
    final String activegm = "#ACTIVEGMDE ";
    final String chosenbrdpth = "#BIRDPATH ";
    final String coinss = "#COINS ";
    final String allbirds = "#BIRD ";
    public void composeExport(int highscore,ArrayList<Integer> storeunlocks,ArrayList<Integer> activebuys,int activegamemode,String birdpath,int coins,ArrayList<String> birdpaths){
        ArrayList<String> file = new ArrayList<String>();
        file.add(hscore+"0x"+Integer.toHexString(highscore));
        for (int i : storeunlocks){
            file.add(ulktstore+"0x"+Integer.toHexString(i));
        }
        for (int i : activebuys){
            file.add(activebuy+"0x"+Integer.toHexString(i));
        }
        file.add(activegm+"0x"+Integer.toHexString(activegamemode));
        file.add(chosenbrdpth+birdpath);
        file.add(coinss+"0x"+Integer.toHexString(coins));
        for (String i : birdpaths){
            file.add(allbirds+i);
        }
        writeToFile(file);
    }
    public void writeToFile(ArrayList<String> file){
        PrintWriter writer = null;
        try {
            String dataFolder = System.getenv("APPDATA");
            writer = new PrintWriter(dataFolder+"\\flappybird\\gamefo.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
            e.printStackTrace();
        }
        for (String i : file){
            writer.println(i);
        }
        writer.close();
    }
}
