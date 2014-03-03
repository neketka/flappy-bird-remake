import javax.swing.*;
import java.io.IOException;

/**
 * Created by Nikita on 2/20/14.
 */
public class Program {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new gameWindow();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
