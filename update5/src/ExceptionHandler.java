import javax.swing.*;
import java.awt.*;

/**
 * Created by Nikita on 3/29/14.
 */
public class ExceptionHandler {
    public static void UseException(Exception e){
        e.printStackTrace();
        StringBuilder stacktrace = new StringBuilder();
        for(StackTraceElement i : e.getStackTrace()){
            stacktrace.append("\n");
            stacktrace.append(i.toString());
        }
        JTextArea area = new JTextArea();
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(450, 350));
        area.append("A problem has occurred, if a crypt or file problem occurred,\n check the \"flappybird\" folder" +
                "\n-------------------------------------Stacktrace-------------------------------------\n" + e.toString()
                 + "\n" + stacktrace.toString());
        JOptionPane.showMessageDialog(null, scrollPane);
    }
}
