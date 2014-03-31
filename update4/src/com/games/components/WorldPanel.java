package com.games.components;

import com.games.objects.Sprite;
import com.games.objects.World;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Nikita on 2/15/14.
 */
public abstract class WorldPanel extends JPanel {
    private boolean stR = false;
    int oWidth = 0;
    int oHeight = 0;
    int nWidth = 0;
    int nHeight = 0;
    public WorldPanel(World world,boolean stretchOnResize){
        this.world = world;
        stR = stretchOnResize;
        oWidth = getWidth();
        oHeight = getHeight();
    }
    public void setSize(Dimension d){
        super.setSize(d);
        if (oWidth == 0&&oHeight ==0){
            oWidth = (int)d.getWidth();
            oHeight = (int)d.getHeight();
        }else if (stR&&oWidth != d.getWidth()&&oHeight != d.getHeight()){
            nWidth = oWidth - (int)d.getWidth();
            nHeight = oHeight - (int)d.getHeight();
        }
    }
    public void setSize(int width,int height){
        super.setSize(width,height);
        if (oWidth == 0&&oHeight ==0){
            oWidth = width;
            oHeight = height;
        }else if (stR&&oWidth != width&&oHeight != height){
            nWidth = oWidth - width;
            nHeight = oHeight - height;
        }
    }
    protected abstract void beforeUpdate();
    protected abstract void onUpdate(Graphics2D g);
    protected abstract void afterUpdate();
    public void update(){
        beforeUpdate();
        paint(getGraphics());
        afterUpdate();
    }
    @Override
    public void paint(Graphics g) {
        BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        if (world.isBgColor()){
            graphics.setColor(world.getBgcolor());
            graphics.fillRect(0,0,this.getWidth(),this.getHeight());
        }
        else {
            graphics.drawImage(world.getCostumes().get(world.getCostumeindex()),0,0,this.getWidth(),this.getHeight(),null);
        }
        for (Sprite s1:world.getSprites()){
            if (s1.isVisible()){
                graphics.drawImage(s1.getImage(),(int)s1.getLocation().getX(),(int)s1.getLocation().getY(),(int)s1.getSize().getWidth()+nWidth,(int)s1.getSize().getHeight()+nHeight,null);
            }
        }
        onUpdate(graphics);
        g.drawImage(image,0,0,null);
    }
    private World world = null;
    public void setWorld(World world) {
        this.world = world;
    }
    public World getWorld(){
        return world;
    }
}
