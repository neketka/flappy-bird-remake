import com.games.components.WorldPanel;
import com.games.objects.Sprite;
import com.games.objects.World;
import com.games.utils.ImageUtils;
import com.games.utils.RandomNumber;
import com.games.utils.Sound;
import com.games.utils.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Nikita on 2/18/14.
 */
public class gameWindow implements ActionListener{
    String username = System.getProperty("user.name");
    gameDataParser parser = new gameDataParser();
    gameDataExporter exporter = new gameDataExporter();
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
    Image extreme;
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
    JButton showeula;
    JLabel scorelabel;
    JTextField score;
    JFrame frame;
    final int pipeMinHeight = -150;
    final int pipeMaxHeight = 1;
    boolean rising = false;
    boolean playing = false;
    boolean p1hit = false;
    boolean p2hit = false;
    boolean p3hit = false;
    //Store begin
    JDialog storeMenu;
    JPanel lists;
    JList<String> storelist;
    JScrollPane stlister;
    DefaultListModel<String> stmodel = new DefaultListModel<String>();
    JList<String> bought;
    JScrollPane blister;
    DefaultListModel<String> bmodel = new DefaultListModel<String>();
    JPanel buttons;
    JTextField money;
    JButton buy;
    JButton use;
    //Store end
    final int PipeX = 350;
    final int downPipeY = 345;
    ArrayList<Integer> storeunlocks;
    ArrayList<String> birdpaths;
    String birdpath;
    void init() {
        lists = new JPanel(new GridLayout(1,2));
        lists.setSize(390, 240);
        lists.setPreferredSize(new Dimension(390, 240));
        buttons = new JPanel(new GridLayout(1,3));
        buttons.setSize(390, 30);
        buttons.setPreferredSize(new Dimension(390, 30));
        storeMenu = new JDialog(frame,"Store",true);
        storeMenu.setResizable(false);
        storeMenu.setSize(400, 305);
        storeMenu.setLayout(new FlowLayout());
        storelist = new JList<String>();
        stlister = new JScrollPane(storelist);
        bought = new JList<String>();
        blister = new JScrollPane(bought);
        bought.setSelectionModel(new DisabledItemSelectionModel());
        money = new JTextField("0 Coins");
        money.setEditable(false);
        buy = new JButton("Show description");
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> stitems = new ArrayList<String>(Arrays.asList(parser.getStoreitems()));
                int storeindex = stitems.indexOf(storelist.getSelectedValue());
                if (storeindex == -1) {
                    JOptionPane.showMessageDialog(null, "Choose something!");
                    return;
                }
                int cost = parser.getStoreCosts()[storeindex];
                int reqlevel = parser.getLevelRequrements()[storeindex];
                String description = parser.getDescriptions()[storeindex];
                int option = JOptionPane.showConfirmDialog(null, description + "\n" +
                        "\nRequirements:\n" +
                        "High score of at least " + reqlevel + ",\nand " +
                        cost + " coins." + "\n--------------------------------------------------------------\n                                    Buy?", "Description", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (option == JOptionPane.NO_OPTION) return;
                if (reqlevel > gameVars.highscore && cost > gameVars.coinses) {
                    JOptionPane.showMessageDialog(null, "Low funds and high score");
                    return;
                }
                if (reqlevel > gameVars.highscore) {
                    JOptionPane.showMessageDialog(null, "Low high score");
                    return;
                }
                if (cost > gameVars.coinses) {
                    JOptionPane.showMessageDialog(null, "Low funds");
                    return;
                }
                if (Util.isBetween(5,6,storeindex,true,true)){
                    switch (storeindex){
                        case 5:
                            try {
                                birdpaths.add(birdMaker.getNewBird(Color.green, "green"));
                            } catch (IOException e1) {
                                Logger.getInstance().log(e1);
                                ExceptionHandler.showLog();
                            }
                            break;
                        case 6:
                            try {
                                birdpaths.add(birdMaker.getNewBird(Color.blue,"blue"));
                            }  catch (IOException e1) {
                                Logger.getInstance().log(e1);
                                ExceptionHandler.showLog();
                            }
                    }
                    birdpath = birdpaths.get(birdpaths.size() -1);
                    try {
                        bird.replaceCostume(0,ImageIO.read(new File(birdpath)));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                bmodel.addElement(stmodel.get(storelist.getSelectedIndex()));
                stmodel.remove(storelist.getSelectedIndex());
                storeunlocks.add(storeindex);
                gameVars.coinses = gameVars.coinses - cost;
                money.setText(gameVars.coinses + " coins");
            }
        });
        lists.add(stlister);
        lists.add(blister);
        buttons.add(buy);
        buttons.add(money);
        storeMenu.add(lists);
        storeMenu.add(buttons);
        try {
            parser.getFile();
        } catch (IOException e) {
            Logger.getInstance().log(e);
            ExceptionHandler.showLog();
        }
        gameVars.highscore = parser.getHighScore();
        gameVars.coinses = parser.getCoins();
        gameVars.actgm = parser.getGamemode();
        storeunlocks = parser.getUnlocked();
        birdpath = parser.getBirdpath();
        birdpaths = parser.getBirds();
        try{
            coinmage = ImageUtils.makeColorTransparent(ImageIO.read(this.getClass().getResourceAsStream("world/coin.gif")),Color.white);
            coin = new Sprite(PipeX,320,25,35,0,coinmage,"Coin",true);
            pipeup = ImageUtils.makeColorTransparent(ImageIO.read(this.getClass().getResourceAsStream("world/pipeup.png")),Color.white);
            pipedown = ImageUtils.makeColorTransparent(ImageIO.read(this.getClass().getResourceAsStream("world/pipedown.png")),Color.white);
            mainworld = ImageIO.read(this.getClass().getResourceAsStream("world/birdplay.png"));
            startt = ImageIO.read(this.getClass().getResourceAsStream("world/flappybirdstart.png"));
            deathh = ImageIO.read(this.getClass().getResourceAsStream("world/death.png"));
            extreme = ImageIO.read(this.getClass().getResourceAsStream("world/extreme.png"));
        }catch (IOException e1){
            Logger.getInstance().log(e1);
            ExceptionHandler.showLog();
        }
        pd1 = new Sprite(PipeX,downPipeY,pipedown.getWidth(null),pipedown.getHeight(null),0,pipedown,"Down Pipe",true);
        pd2 = new Sprite((int)pd1.getLocation().getX()+gameVars.pipeDistance,(int)pd1.getLocation().getY(),(int)pd1.getSize().getWidth(),(int)pd1.getSize().getHeight(),pd1.getDegrees(),pd1.getCostumes().get(pd1.getCostumeindex()),pd1.getName(),true);
        pd3 = new Sprite((int)pd1.getLocation().getX()+gameVars.pipeDistance*2,(int)pd1.getLocation().getY(),(int)pd1.getSize().getWidth(),(int)pd1.getSize().getHeight(),pd1.getDegrees(),pd1.getCostumes().get(pd1.getCostumeindex()),pd1.getName(),true);
        pu1 = new Sprite(PipeX,pipeMaxHeight,pipeup.getWidth(null),pipeup.getHeight(null),0,pipeup,"Up pipe",true);
        pu2 = new Sprite((int)pu1.getLocation().getX()+gameVars.pipeDistance,(int)pu1.getLocation().getY(),(int)pu1.getSize().getWidth(),(int)pu1.getSize().getHeight(),pu1.getDegrees(),pu1.getCostumes().get(pu1.getCostumeindex()),pu1.getName(),true);
        pu3 = new Sprite((int)pu1.getLocation().getX()+gameVars.pipeDistance*2,(int)pu1.getLocation().getY(),(int)pu1.getSize().getWidth(),(int)pu1.getSize().getHeight(),pu1.getDegrees(),pu1.getCostumes().get(pu1.getCostumeindex()),pu1.getName(),true);
        frame = new JFrame();
        world = new World(mainworld,"Main world");
        startworld = new World(startt,"Start world");
        death = new World(deathh,"Death world");
        viewPort = new WorldPanel(startworld,false) {
            @Override
            protected void beforeUpdate() {
            }

            @Override
            protected void onUpdate(Graphics2D g) {
                if (playing){
                    g.setFont(new Font("Times New Roman", Font.ITALIC,36));
                    g.setColor(Color.blue);
                    g.drawString("Score: "+gameVars.scorenum,120,50);
                    g.drawString("Coins: "+gameVars.coinnum,120,75);
                }
                else {
                    g.setColor(Color.BLACK);
                    g.setFont(new Font("Times New Roman", Font.PLAIN,18));
                    g.drawString("High Score: "+gameVars.highscore,this.getWidth()/2-50,this.getHeight()/2+80);
                    g.drawString("Coins: "+gameVars.coinnum,this.getWidth()/2-50,this.getHeight()/2+95);
                    g.drawString("Score: "+gameVars.scorenum,this.getWidth()/2-50,this.getHeight()/2+110);
                }
            }

            @Override
            protected void afterUpdate() {
            }
        };
        viewPort.setFocusable(true);
        viewPort.addKeyListener(mainListener);
        try {
            bird = new Sprite(290/2,400/2,29,26,0,ImageUtils.makeColorTransparent(ImageIO.read(this.getClass().getResourceAsStream(birdpath)),Color.white),"bird",true);
        } catch (IOException e) {
            Logger.getInstance().log(e);
            ExceptionHandler.showLog();
        }
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    exporter.composeExport(gameVars.highscore,storeunlocks,gameVars.actgm,birdpath,gameVars.coinses,birdpaths);
                } catch (IOException e1) {
                    Logger.getInstance().log(e1);
                    ExceptionHandler.showLog();
                }
            }
        });
        viewPort.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (!playing) {
                    return;
                }
                rising = true;
                if (playing) {
                    gameVars.cc = gameVars.defcc;
                    final int[] count = {0};
                    final int[] by = {0};
                    final double[] byc = {gameVars.jumpstart};
                    final int[] target = {gameVars.jumptarget
                    };
                    new Timer(5, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            by[0] = (int) Math.floor(byc[0]);
                            if (count[0] == target[0]) {
                                Timer sourcetimer = (Timer) e.getSource();
                                sourcetimer.stop();
                            }
                            bird.setDegrees(315);
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() - by[0]);
                            byc[0] -= 0.3;
                            count[0]++;
                        }
                    }).start();
                    while (count[0] == target[0]) ;
                }
                rising = false;
            }
        });
        controls = new JPanel();
        play = new JButton("Play!");
        play.addActionListener(this);
        play.addKeyListener(mainListener);
        showinfo = new JButton("Info");
        showinfo.addActionListener(this);
        showinfo.addKeyListener(mainListener);
        store = new JButton("Store");
        store.addActionListener(this);
        store.addKeyListener(mainListener);
        showeula = new JButton("EULA");
        showeula.addActionListener(this);
        showeula.addKeyListener(mainListener);
        options = new JButton("Options");
        options.addActionListener(this);
        options.addKeyListener(mainListener);
        controls.setLayout(new GridLayout(1, 5));
        controls.add(play);
        controls.add(store);
        controls.add(options);
        controls.add(showeula);
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 470);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.add(viewPort);
        frame.add(controls);
        frame.setVisible(true);
        updateStore();
        updateGameMode();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (frame.isVisible()){
                    viewPort.update();
                    try {
                        spriteCheck();
                        pipeCheck();
                    } catch (Exception e) {
                        Logger.getInstance().log(e);
                        ExceptionHandler.showLog();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActionListener a = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (playing&&!rising){
                            bird.setLocation((int)bird.getLocation().getX(),(int)bird.getLocation().getY()+(int)Math.floor(gameVars.cc));
                            if (bird.getDegrees() != 90){
                                if (bird.getDegrees() == 360){
                                    bird.setDegrees(0);
                                }
                                else {
                                    bird.setDegrees(bird.getDegrees()+4);
                                }
                            }
                            gameVars.cc=gameVars.cc+0.7;
                        }
                    };
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
                    Logger.getInstance().log(e);
                    ExceptionHandler.showLog();
                }
                    pipeMove();
                }
            }
        }).start();
    }
    KeyAdapter mainListener = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            if (e.getKeyChar() == 'e'&&playing == false) {
                showeula.doClick();
                return;
            }
            if (e.getKeyChar() == 'p'&&playing == false) {
                play.doClick();
                return;
            }
            if (e.getKeyChar() == 'o'&&playing == false) {
                options.doClick();
                return;
            }
            if (e.getKeyChar() == 's'&&playing == false) {
                store.doClick();
                return;
            }
            if (e.getKeyChar() == ' ') {
                if (!playing) {
                    return;
                }
                rising = true;
                if (playing) {
                    gameVars.cc = gameVars.defcc;
                    final int[] count = {0};
                    final int[] by = {0};
                    final double[] byc = {gameVars.jumpstart};
                    final int[] target = {gameVars.jumptarget
                    };
                    new Timer(5, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            by[0] = (int) Math.floor(byc[0]);
                            if (count[0] == target[0]) {
                                Timer sourcetimer = (Timer) e.getSource();
                                sourcetimer.stop();
                            }
                            bird.setDegrees(315);
                            bird.setLocation((int) bird.getLocation().getX(), (int) bird.getLocation().getY() - by[0]);
                            byc[0] -= 0.3;
                            count[0]++;
                        }
                    }).start();
                    while (count[0] == target[0]) ;
                }
                rising = false;
            }
        }
    };
    public gameWindow(){
        init();
    }
    public void updateStore(){
        for (int i : storeunlocks){
            switch (i){
                case 0:
                    if (gameVars.coinink !=3) gameVars.coinink = 2;
                    break;
                case 1:
                    gameVars.coinink = 3;
                    break;
                case 2:
                    gameVars.coinspread = 10;
                    break;
                case 3:
                    gameVars.pipeDistance = 200;
                    break;
                case 4:
                    gameVars.pipeHeightDistance = 355;
                    break;
            }
        }
    }
    public void updateGameMode(){
        switch (gameVars.actgm){
            case 0:
                gameVars.pipeSpeed = 5;
                gameVars.defcc = 3;
                gameVars.jumpstart = 7.2;
                gameVars.jumptarget = 11;
                try {
                    world.addCostume(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("world/birdplay.png")));
                    world.removeCostume(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                gameVars.pipeSpeed = 7;
                gameVars.defcc = 10;
                gameVars.jumpstart = 6.8;
                gameVars.jumptarget = 20;
                try {
                    world.addCostume(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("world/extreme.png")));
                    world.removeCostume(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    public void doHide() throws InterruptedException{
        controls.setVisible(false);
        for (int i = 0;i<5;i++){
            frame.setSize(frame.getWidth(), frame.getHeight() - (4 * i));
            Thread.sleep(70);
        }
    }
    public void dead() throws Exception{
        gameVars.coinses = gameVars.coinses + gameVars.coinnum;
        pd1.setLocation(PipeX,downPipeY);
        pd2.setLocation((int)pd1.getLocation().getX()+gameVars.pipeDistance,(int)pd1.getLocation().getY());
        pd3.setLocation((int)pd2.getLocation().getX()+gameVars.pipeDistance,(int)pd1.getLocation().getY());
        pu1.setLocation(PipeX,pipeMaxHeight);
        pu2.setLocation((int)pu1.getLocation().getX()+gameVars.pipeDistance,(int)pu1.getLocation().getY());
        pu3.setLocation((int) pu2.getLocation().getX() + gameVars.pipeDistance, (int) pu1.getLocation().getY());
        if (gameVars.scorenum>gameVars.highscore)gameVars.highscore=gameVars.scorenum;
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
            gameVars.coinnum= gameVars.coinnum + gameVars.coinink;
            coin.setLocation(PipeX+gameVars.pipeDistance+30,(int)RandomNumber.randomInt((long)bird.getLocation().getY()-30,(long)bird.getLocation().getY()+50));
        }
    }
    public void pipeCheck(){
        if (pu1.getLocation().getX() ==bird.getLocation().getX() && !p1hit){
            p1hit = true;
            gameVars.scorenum++;
        }
        if (pu2.getLocation().getX() ==bird.getLocation().getX() && !p2hit){
            p2hit = true;
            gameVars.scorenum++;
        }
        if (pu3.getLocation().getX() ==bird.getLocation().getX() && !p3hit){
            p3hit = true;
            gameVars.scorenum++;
        }
        if (pu1.getLocation().getX() <=0&&pd1.getLocation().getX() <=0){
            p1hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + gameVars.pipeHeightDistance;
            pu1.setLocation((int)pu3.getLocation().getX()+gameVars.pipeDistance,puh);
            pd1.setLocation((int)pu3.getLocation().getX()+gameVars.pipeDistance,pdh);
        }
        if (pu2.getLocation().getX() <=0&&pd2.getLocation().getX() <=0){
            p2hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + gameVars.pipeHeightDistance;
            pu2.setLocation((int)pu1.getLocation().getX()+gameVars.pipeDistance,puh);
            pd2.setLocation((int)pu1.getLocation().getX()+gameVars.pipeDistance,pdh);
        }
        if (pu3.getLocation().getX() <=0&&pd3.getLocation().getX() <=0){
            p3hit = false;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + gameVars.pipeHeightDistance;
            pu3.setLocation((int)pu2.getLocation().getX()+gameVars.pipeDistance,puh);
            pd3.setLocation((int)pu2.getLocation().getX()+gameVars.pipeDistance,pdh);
        }
        if (coin.getLocation().getX() <=0){
            coin.setLocation(PipeX+gameVars.pipeDistance+50,(int)RandomNumber.randomInt((long)bird.getLocation().getY()-gameVars.coinspread,(long)bird.getLocation().getY()+gameVars.coinspread));
        }
    }
    public void pipeMove(){
        if (playing){
            pu1.setLocation((int)pu1.getLocation().getX()-gameVars.pipeSpeed,(int)pu1.getLocation().getY());
            pd1.setLocation((int)pd1.getLocation().getX()-gameVars.pipeSpeed,(int)pd1.getLocation().getY());
            pu2.setLocation((int)pu2.getLocation().getX()-gameVars.pipeSpeed,(int)pu2.getLocation().getY());
            pd2.setLocation((int)pd2.getLocation().getX()-gameVars.pipeSpeed,(int)pd2.getLocation().getY());
            pu3.setLocation((int)pu3.getLocation().getX()-gameVars.pipeSpeed,(int)pu3.getLocation().getY());
            pd3.setLocation((int)pd3.getLocation().getX()-gameVars.pipeSpeed,(int)pd3.getLocation().getY());
            coin.setLocation((int)coin.getLocation().getX()-gameVars.pipeSpeed,(int)coin.getLocation().getY());
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == play){
            gameVars.cc = 3;
            gameVars.coinnum = 0;
            gameVars.scorenum = 0;
            int puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            int pdh = puh + gameVars.pipeHeightDistance;
            pd1.setLocation((int)pd1.getLocation().getX(),pdh);
            pu1.setLocation((int)pu1.getLocation().getX(),puh);
            puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            pdh = puh + gameVars.pipeHeightDistance;
            pd2.setLocation((int)pd2.getLocation().getX(),pdh);
            pu2.setLocation((int)pu2.getLocation().getX(),puh);
            puh = (int)RandomNumber.randomInt((long)pipeMinHeight,(long)pipeMaxHeight,5);
            pdh = puh + gameVars.pipeHeightDistance;
            pd3.setLocation((int)pd3.getLocation().getX(),pdh);
            pu3.setLocation((int)pu3.getLocation().getX(),puh);
            try {
                doHide();
                if (viewPort.getWorld() == startworld){
                    pd1.setLocation(PipeX,(int)pd1.getLocation().getY());
                    pd2.setLocation((int)pd1.getLocation().getX()+gameVars.pipeDistance,(int)pd2.getLocation().getY());
                    pd3.setLocation((int)pd2.getLocation().getX()+gameVars.pipeDistance,(int)pd3.getLocation().getY());
                    pu1.setLocation(PipeX,(int)pu1.getLocation().getY());
                    pu2.setLocation((int)pu1.getLocation().getX()+gameVars.pipeDistance,(int)pu2.getLocation().getY());
                    pu3.setLocation((int) pu2.getLocation().getX() + gameVars.pipeDistance, (int) pu3.getLocation().getY());
                }
                viewPort.setWorld(world);
                playing = true;
            } catch (InterruptedException e1) {
                Logger.getInstance().log(e1);
                ExceptionHandler.showLog();
            }
        }

        else if (e.getSource() == store){
            stmodel.clear();
            bmodel.clear();
            money.setText(String.valueOf(gameVars.coinses)+" Coins");
            int iz = 0;
            for (String i : parser.getStoreitems()){
                if (!storeunlocks.contains(iz)){
                    stmodel.addElement(i);
                }
                iz++;
            }
            ArrayList<Integer> removes = new ArrayList<Integer>();
            for (int i : storeunlocks){
                bmodel.addElement("Bought " + parser.getStoreitems()[i]);
            }
            storelist.setModel(stmodel);
            bought.setModel(bmodel);
            storeMenu.setVisible(true);
            updateStore();
        }

        else if (e.getSource() == options){
            try {
                showOptions();
            } catch (IOException e1) {
                Logger.getInstance().log(e1);
                ExceptionHandler.showLog();
            }
        }

        else if (e.getSource() == showinfo){
            JOptionPane.showMessageDialog(null,"Score: "+gameVars.scorenum+"\nHighscore: "+gameVars.highscore+"\nCoins: "+gameVars.coinnum+"\nAll coins: "+gameVars.coinses);
        }

        else if (e.getSource() == showeula){
            JTextArea area = new JTextArea();
            area.setEditable(false);
            area.append("EULA");
            for (String i : parser.getEULA()){
                area.append("\n   "+i);
            }
            JScrollPane scrollPane = new JScrollPane(area);
            scrollPane.setPreferredSize(new Dimension(325, 250));
            JOptionPane.showMessageDialog(null,scrollPane,"End User Licence Agreement",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    class DisabledItemSelectionModel extends DefaultListSelectionModel {

        private static final long serialVersionUID = 1L;

        @Override
        public void setSelectionInterval(int index0, int index1) {
            super.setSelectionInterval(-1, -1);
        }
    }
    public void showOptions() throws IOException {
        JComboBox<String> gmde = new JComboBox<String>();
        gmde.addItem("Default");
        if (storeunlocks.contains(7)){
            gmde.addItem("Extreme");
        }
        gmde.setSelectedIndex(gameVars.actgm);
        ArrayList<File> ff = new ArrayList<>();
        ff.add(new File(this.getClass().getResource("bird/bird.gif").getFile()));
        for (String i : birdpaths){
            ff.add(new File(i));
        }
        String[] f = new String[ff.size()];
        for (int i = 0;i<ff.size();i++){
            f[i] = ff.get(i).getName();
        }
        JComboBox<String> birdslocs = new JComboBox<String>(f);
        birdslocs.setSelectedIndex(((ArrayList<String>)Util.toStringCollection(f)).indexOf(new File(birdpath).getName()));
        JDialog optionsWindow = new JDialog(frame,"Options",true);
        optionsWindow.setResizable(false);
        optionsWindow.setSize(200, 100);
        optionsWindow.setLayout(new GridLayout(2, 2));
        optionsWindow.add(new JLabel("Gamemode: "));
        optionsWindow.add(gmde);
        optionsWindow.add(new JLabel("Birds: "));
        optionsWindow.add(birdslocs);
        optionsWindow.setVisible(true);
        Image image;
        if (birdslocs.getSelectedIndex() == 0){
            image = ImageIO.read(this.getClass().getResource("bird/bird.gif"));
        }
        else  {
            image = ImageIO.read(new File((gameVars.dataFolder.getPath()+"/flappybird/birds/"+ birdslocs.getSelectedItem()).replace("\\","/")));
        }
        birdpath = (gameVars.dataFolder.getPath()+"/flappybird/birds/"+ birdslocs.getSelectedItem()).replace("\\","/");
        bird.replaceCostume(0,image);
        gameVars.actgm = gmde.getSelectedIndex();
        updateGameMode();
    }
}
