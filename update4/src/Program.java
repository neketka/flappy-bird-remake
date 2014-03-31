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
                    StringBuilder stacktrace = new StringBuilder();
                    for(StackTraceElement i : e.getStackTrace()){
                        stacktrace.append("\n");
                        stacktrace.append(i.toString());
                    }
                    JOptionPane.showMessageDialog(null,"A problem has occured,\n if a crypt or file problem occured,\n check the \"flappybird\" folder\n-------------------------------------Stacktrace-------------------------------------\n"+e.toString()+": "+e.getMessage()+"\n"+stacktrace.toString());
                    e.printStackTrace();
                }
            }
        });
    }
}
