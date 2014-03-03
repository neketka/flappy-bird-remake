package games.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Nikita on 2/9/14.
 */
public abstract class GameLogic{
    private boolean Continue = true;
    private javax.swing.Timer timer;
    private int millisecondstime = 0;
    private void stopy() {
        Continue = false;
    }
    private void starty(){
        new Thread(gameThread).start();
    }
    public void start(){
        starty();
    }
    public void stop(){
        stopy();
    }
    private Runnable gameThread = new Runnable() {
        @Override
        public void run() {
            final boolean[] notGoOn = {true};
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    intro();
                    notGoOn[0] = false;
                }
            });
            while (notGoOn[0]);
            timer = new javax.swing.Timer(1,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    millisecondstime++;
                }
            });
            timer.setActionCommand("1ms");
            timer.start();
            while (Continue){
                render(millisecondstime);
                logic(millisecondstime);
                if (!Continue){
                    break;
                }
            }
            end(millisecondstime);
            millisecondstime = 0;
            timer.stop();
        }
    };
    protected boolean intro(){
        //Introduction for game (always returns false)
        return false;
    }
    protected void render(int millitime){
        //Rendering in the game
    }
    protected void logic(int millitime){
        //Game logic
    }
    protected void end(int millitime){
        //Game wrap up
    }
}
