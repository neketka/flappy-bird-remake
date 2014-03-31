import games.components.WorldPanel;
import games.objects.Sprite;
import games.objects.World;
import games.utils.RandomNumber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Nikita on 2/18/14.
 */
public class gameWindow implements ActionListener{
    gameDataParser parser = new gameDataParser();
    WorldPanel viewPort;
    World startworld;
    World world;
    World death;
    Image startt;
    Image mainworld;
    Image deathh;
    Image pipeup;
    Image pipedown;
    Image coinmage;
    Sprite bird;
    Sprite pd1;
    Sprite pd2;
    Sprite pd3;
    Sprite pu1;
    Sprite pu2;
    Sprite pu3;
    Sprite coin;
    JPanel controls;
    JButton play;
    JButton store;
    JButton options;
    JButton showinfo;
    JLabel scorelabel;
    JTextField score;
    JFrame frame;
    final int pipeDistance = 180;
    final int pipeMinHeight = -150;
    final int pipeMaxHeight = 1;
    final int pipeHeightDistance = 344;
    int coinink = 1;
    int highscore = 0;
    int coinses = 0;
    boolean rising = false;
    boolean playing = false;
    boolean p1hit = false;
    boolean p2hit = false;
    boolean p3hit = false;
    int scorenum = 0;
    int coinnum = 0;
    final int PipeX = 350;
    final int downPipeY = 345;
    void init() throws IOException{
        parser.getFile();
        highscore = parser.getHighScore();
        coinses = parser.getCoins();
        coinmage = ImageIO.read(this.getClass().getResource("world\\coin.png"));
        coin = new Sprite(PipeX,320,25,35,0,coinmage,"Coin",true);
        pipeup = ImageIO.read(this.getClass().getResource("world\\pipeup.png"));
        pipedown = ImageIO.read(this.getClass().getResource("world\\pipedown.png"));
        mainworld = ImageIO.read(this.getClass().getResource("world\\birdplay.png"));
        startt = ImageIO.read(this.getClass().getResource("world\\flappybirdstart.png"));
        deathh = ImageIO.read(this.getClass().getResource("world\\death.png"));
        pd1 = new Sprite(PipeX,downPipeY,pipedown.getWidth(null),pipedown.getHeight(null),0,pipedown,"Down Pipe",true);
        pd2 = new Sprite((int)pd1.getLocation().getX()+pipeDistance,(int)pd1.getLocation().getY(),(int)pd1.getSize().getWidth(),(int)pd1.getSize().getHeight(),pd1.getDegrees(),pd1.getImage(),pd1.getName(),true);
        pd3 = new Sprite((int)pd1.getLocation().getX()+pipeDistance*2,(int)pd1.getLocation().getY(),(int)pd1.getSize().getWidth(),(int)pd1.getSize().getHeight(),pd1.getDegrees(),pd1.getImage(),pd1.getName(),true);
        pu1 = new Sprite(PipeX,pipeMaxHeight,pipeup.getWidth(null),pipeup.getHeight(null),0,pipeup,"Up pipe",true);
        pu2 = new Sprite((int)pu1.getLocation().getX()+pipeDistance,(int)pu1.getLocation().getY(),(int)pu1.getSize().getWidth(),(int)pu1.getSize().getHeight(),pu1.getDegrees(),pu1.getImage(),pu1.getName(),true);
        pu3 = new Sprite((int)pu1.getLocation().getX()+pipeDistance*2,(int)pu1.getLocation().getY(),(int)pu1.getSize().getWidth(),(int)pu1.getSize().getHeight(),pu1.getDegrees(),pu1.getImage(),pu1.getName(),true);
        frame = new JFrame();
        world = new World(mainworld,"Main world");
        startworld = new World(startt,"Start world");
        death = new World(deathh,"Death world");
        viewPort = new WorldPanel(startworld, false) {
            @Override
            protected void onUpdate() {

            }

            @Override
            protected void afterUpdate() {

            }
        };
        viewPort.setFocusable(true);
        viewPort.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == 'i') {
                    showinfo.doClick();
                    return;
                }
                if (e.getKeyChar() == 'p') {
                    play.doClick();
                    return;
                }
                if (e.getKeyChar() == 'o') {
                    options.doClick();
                    return;
                }
                if (e.getKeyChar() == 's') {
                    store.doClick();
                    return;
                }
                if (e.getKeyChar() == ' ') {
                    if (playing == false) {
                        play.doClick();
                        return;
                    }
                    rising = true;
                    if (playing) {
                        final int[] count = {0};
                        final int[] countt = {11};
                        new Timer(10, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (count[0] == 5) {
                                    Timer sourcetimer = (Timer) e.getSource();
                                    sourcetimer.stop();
                                }
                                bird.setDegrees(315);
                                bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() - countt[0]);
                                count[0]++;
                            }
                        }).start();
                        while (count[0] == 5) ;
                    }
                    rising = false;
                }
            }
        });
        bird = new Sprite(290/2,400/2,25,25,0,ImageIO.read(this.getClass().getResourceAsStream(parser.getBirdpath())),"bird",true);
        controls = new JPanel();
        play = new JButton("Play!");
        play.addActionListener(this);
        showinfo = new JButton("Info");
        showinfo.addActionListener(this);
        store = new JButton("Store");
        store.addActionListener(this);
        options = new JButton("Options");
        options.addActionListener(this);
        controls.setLayout(new GridLayout(1, 5));
        controls.add(play);
        controls.add(store);
        controls.add(options);
        controls.add(showinfo);
        world.addSprite(bird);
        world.addSprite(pd1);
        world.addSprite(pd2);
        world.addSprite(pd3);
        world.addSprite(pu1);
        world.addSprite(pu2);
        world.addSprite(pu3);
        world.addSprite(coin);
        viewPort.setPreferredSize(new Dimension(350, 400));
        controls.setPreferredSize(new Dimension(350, 20));
        frame.setTitle("Flappy bird");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 470);
        frame.setLayout(new FlowLayout());
        frame.add(viewPort);
        frame.add(controls);
        frame.setVisible(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (frame.isVisible()){
                    viewPort.update();
                    try {
                        spriteCheck();
                        pipeCheck();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final int[] velocity = {1};
                ActionListener a = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (playing){
                            while (rising);
                            bird.setLocation((int)bird.getLocation().getX(),(int)bird.getLocation().getY() + velocity[0]);
                            if (rising){
                                velocity[0] =1;return;}
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+2);
                                }
                            }
                            if (rising){
                                velocity[0] =1;return;}
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() + velocity[0]);
                            if (rising){
                                velocity[0] =1;return;}
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+2);
                                }
                            }
                            if (rising){
                                velocity[0] =1;return;}
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() + velocity[0]);
                            if (rising){
                                velocity[0] =1;return;}
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+2);
                                }
                            }
                            if (rising){
                                velocity[0] =1;return;}
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() + velocity[0]);
                            if (rising){return;}
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+2);
                                }
                            }
                            if (rising){
                                velocity[0] =1;return;}
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() + velocity[0]);
                            if (rising){return;}
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+2);
                                }
                            }
                        }
                    }
                };
                Timer t = new Timer(40,a);
                t.start();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run(){
                while (frame.isVisible()){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
                    pipeMove();
                }
            }
        }).start();
    }
    public gameWindow() throws Exception {
        init();
    }
    public void doHide() throws InterruptedException{
        controls.setVisible(false);
        for (int i = 0;i<5;i++){
            frame.setSize(frame.getWidth(), frame.getHeight() - (4 * i));
            Thread.sleep(70);
        }
    }
    public void dead() throws Exception{
        coinses = coinses + coinnum;
        pd1.setLocation(PipeX,downPipeY);
        pd2.setLocation((int)pd1.getLocation().getX()+pipeDistance,(int)pd1.getLocation().getY());
        pd3.setLocation((int)pd2.getLocation().getX()+pipeDistance,(int)pd1.getLocation().getY());
        pu1.setLocation(PipeX,pipeMaxHeight);
        pu2.setLocation((int)pu1.getLocation().getX()+pipeDistance,(int)pu1.getLocation().getY());
        pu3.setLocation((int) pu2.getLocation().getX() + pipeDistance, (int) pu1.getLocation().getY());
        if (scorenum>highscore)highscore=scorenum;
        viewPort.setWorld(death);
        playing = false;
        bird.setLocation((int)bird.getLocation().getX(),viewPort.getHeight()/2);
        play.setEnabled(false);
        for (int i = 0;i<5;i++){
            frame.setSize(frame.getWidth(), frame.getHeight() + (4 * i));
            Thread.sleep(70);
        }
        rising = false;
        controls.setVisible(true);
        play.setEnabled(true);
    }
    public void spriteCheck() throws Exception {
        if (Sprite.isCollided(bird,pu1)||Sprite.isCollided(bird,pu2)||Sprite.isCollided(bird,pu3)||Sprite.isCollided(bird,pd1)||Sprite.isCollided(bird,pd2)||Sprite.isCollided(bird,pd3)||bird.getLocation().getY()>= viewPort.getHeight()||bird.getLocation().getY()<=0&&playing){
            dead();
        }
        if (Sprite.isCollided(bird,coin)){
            coinnum= coinnum + coinink;
            coin.setLocation(PipeX+pipeDistance+30,(int)RandomNumber.randomInt((long)bird.getLocation().getY()-30,(long)bird.getLocation().getY()+50));
        }
    }
    public void pipeCheck(){
        if (pu1.getLocation().getX() ==bird.getLocation().getX() && !p1hit){
            p1hit = true;
            scorenum++;
        }
        if (pu2.getLocation().getX() ==bird.getLocation().getX() && !p2hit){
            p2hit = true;
            scorenum++;
        }
        if (pu3.getLocation().getX() ==bird.getLocation().getX() && !p3hit){
            p3hit = true;
            scorenum++;
        }
        if (pu1.getLocation().getX() <=0&&pd1.getLocation().getX() <=0){
            p1hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + pipeHeightDistance;
            pu1.setLocation(PipeX+pipeDistance,puh);
            pd1.setLocation(PipeX+pipeDistance,pdh);
        }
        if (pu2.getLocation().getX() <=0&&pd2.getLocation().getX() <=0){
            p2hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + pipeHeightDistance;
            pu2.setLocation(PipeX+pipeDistance,puh);
            pd2.setLocation(PipeX+pipeDistance,pdh);
        }
        if (pu3.getLocation().getX() <=0&&pd3.getLocation().getX() <=0){
            p3hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + pipeHeightDistance;
            pu3.setLocation(PipeX+pipeDistance,puh);
            pd3.setLocation(PipeX+pipeDistance,pdh);
        }
        if (coin.getLocation().getX() <=0){
            coin.setLocation(PipeX+pipeDistance+50,(int)RandomNumber.randomInt((long)bird.getLocation().getY()-30,(long)bird.getLocation().getY()+30));
        }
    }
    public void pipeMove(){
        if (playing){
            pu1.setLocation((int)pu1.getLocation().getX()-5,(int)pu1.getLocation().getY());
            pd1.setLocation((int)pd1.getLocation().getX()-5,(int)pd1.getLocation().getY());
            pu2.setLocation((int)pu2.getLocation().getX()-5,(int)pu2.getLocation().getY());
            pd2.setLocation((int)pd2.getLocation().getX()-5,(int)pd2.getLocation().getY());
            pu3.setLocation((int)pu3.getLocation().getX()-5,(int)pu3.getLocation().getY());
            pd3.setLocation((int)pd3.getLocation().getX()-5,(int)pd3.getLocation().getY());
            coin.setLocation((int)coin.getLocation().getX()-5,(int)coin.getLocation().getY());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play){
            coinnum = 0;
            scorenum = 0;
            try {
                doHide();
                viewPort.setWorld(world);
                playing = true;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        else if (e.getSource() == store){
            JOptionPane.showMessageDialog(null,"No store available yet!");
        }

        else if (e.getSource() == options){
            JOptionPane.showMessageDialog(null,"No options available yet!");
        }

        else if (e.getSource() == showinfo){
            JOptionPane.showMessageDialog(null,"Score: "+scorenum+"\nHighscore: "+highscore+"\nCoins: "+coinnum+"\nSession coins: "+coinses);
        }
    }
}
