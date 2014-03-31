import javax.swing.*;
import javax.swing.text.StringContent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nikita on 2/20/14.
 */
public class Program {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new gameWindow();
                } catch (Exception e) {
                    ExceptionHandler.UseException(e);
                }
            }
        });
    }
}
