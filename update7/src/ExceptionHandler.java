import com.games.utils.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Nikita on 3/29/14.
 */
public class ExceptionHandler {
    public static void showLog(){
        JTextArea area = new JTextArea();
        String date = String.valueOf(Calendar.MONTH) + String.valueOf(Calendar.DAY_OF_MONTH) + String.valueOf(Calendar.YEAR);
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save");
        fileChooser.setSelectedFile(new File("errorlog"+date+".txt"));
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.getVerticalScrollBar().setValue(0);
        scrollPane.setPreferredSize(new Dimension(470, 350));
        JButton savelog = new JButton("Save error log");
        savelog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showSaveDialog(null);
                PrintWriter writer = null;
                try {
                    writer = new PrintWriter(fileChooser.getSelectedFile());
                    if (!fileChooser.getSelectedFile().exists()){
                        fileChooser.getSelectedFile().createNewFile();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                for (String i : Logger.getInstance().getLog()){
                    writer.println(i);
                }
                writer.flush();
                writer.close();
            }
        });
        area.append("A problem has occurred, if a crypt or file problem occurred,\n check the \"flappybird\" folder\n else save the error log and email it at\n" +
                "neketka@gmail.com\n" +
                "\n-------------------------------------Stacktrace-------------------------------------\n"
                + Util.toCarriageReturnString(Logger.getInstance().getLog()));
        JPanel p = new JPanel(new FlowLayout());
        p.add(scrollPane);
        p.add(savelog);
        JOptionPane.showMessageDialog(null, p);
        Logger.getInstance().clear();
    }
}
