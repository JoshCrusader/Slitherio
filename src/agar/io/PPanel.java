/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agar.io;

/**
 *
 * @author drjeoffreycruzada
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.Timer;
import java.util.*;
public class PPanel extends JPanel implements ActionListener, KeyListener {
       Timer t;
    
    int sizex = 15000;
    int sizey = 15000;
    int offsetx = 0;
    int offsety = 0;
    boolean firstTime = true;
    boolean cangrow = false;
    boolean canshrink = false;
    int[][][][] coords = new int[50][50][3][8];
    public PPanel(){
        
        
        this.setSize(sizex,sizey);
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(this);
        t = new Timer(50,this);
        t.start();
       
    }
    
    
    int Colora1 = (int)(Math.random()*255);
    int Colora2 = (int)(Math.random()*255);
    int Colora3 = (int)(Math.random()*255);
    int Colorb1 = (int)(Math.random()*255);
    int Colorb2 = (int)(Math.random()*255);
    int Colorb3 = (int)(Math.random()*255);
    int toDiv = 45;
    double initialSpeed = 70;
    int toMult = 40;
    double speed = 0;
    public void paint(Graphics g){
        super.paint(g);
        if(speed == 1){
            Colora1 = Colorb1;
            Colora2 = Colorb2;
            Colora3 = Colorb3;
            Colorb1 = (int)(150+Math.random()*105);
            Colorb2 = (int)(150+Math.random()*105);
            Colorb3 = (int)(15+Math.random()*105);
        }
        for(int i = 0; i < toDiv; i++){
            for(int x = 0; x < toDiv; x++){
                
                int x1 = x*sizex/toDiv-20,
                    y1 = i*sizey/toDiv-20,
                    x2 = x*sizex/toDiv-20,
                    y2 = i*sizey/toDiv+sizey/toDiv-20,
                    x3 = x*sizex/toDiv+sizex/toDiv-20,
                    y3 = i*sizey/toDiv-20,
                    x4 = x*sizex/toDiv + sizex/toDiv-20,
                    y4 = i*sizey/toDiv+ sizey/toDiv-20;
                if(firstTime){
                    coords[i][x][0][0] = x1;
                    coords[i][x][0][1] = y1;
                    coords[i][x][0][2] = x2;
                    coords[i][x][0][3] = y2;
                    coords[i][x][0][4] = x3;
                    coords[i][x][0][5] = y3;
                    coords[i][x][0][6] = x4;
                    coords[i][x][0][7] = y4;
                    
                    coords[i][x][2][0] = x1;
                    coords[i][x][2][1] = y1;
                    coords[i][x][2][2] = x2;
                    coords[i][x][2][3] = y2;
                    coords[i][x][2][4] = x3;
                    coords[i][x][2][5] = y3;
                    coords[i][x][2][6] = x4;
                    coords[i][x][2][7] = y4;
                }
                if(speed == 1){
                    coords[i][x][1][0] = x1 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][1] = y1 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][2] = x2 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][3] = y2 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][4] = x3 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][5] = y3 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][6] = x4 + (-toMult)+(int)(Math.random()*toMult*2);
                    coords[i][x][1][7] = y4 + (-toMult)+(int)(Math.random()*toMult*2);
                }
                    x1 = (int)(coords[i][x][0][0] + ((double)(coords[i][x][1][0] - coords[i][x][0][0]))*(speed/initialSpeed));
                    y1 = (int)(coords[i][x][0][1] + ((double)(coords[i][x][1][1] - coords[i][x][0][1]))*(speed/initialSpeed));
                    x2 = (int)(coords[i][x][0][2] + ((double)(coords[i][x][1][2] - coords[i][x][0][2]))*(speed/initialSpeed));
                    y2 = (int)(coords[i][x][0][3] + ((double)(coords[i][x][1][3] - coords[i][x][0][3]))*(speed/initialSpeed));
                    x3 = (int)(coords[i][x][0][4] + ((double)(coords[i][x][1][4] - coords[i][x][0][4]))*(speed/initialSpeed));
                    y3 = (int)(coords[i][x][0][5] + ((double)(coords[i][x][1][5] - coords[i][x][0][5]))*(speed/initialSpeed));
                    x4 = (int)(coords[i][x][0][6] + ((double)(coords[i][x][1][6] - coords[i][x][0][6]))*(speed/initialSpeed));
                    y4 = (int)(coords[i][x][0][7] + ((double)(coords[i][x][1][7] - coords[i][x][0][7]))*(speed/initialSpeed));
                if(x == 0 && i == 0){
                    /*
                   x1 += (int)(Math.random()*toMult);
                   y1 += (int)(Math.random()*toMult);
                   x2 += (int)(Math.random()*toMult);
                   y2 += (int)(Math.random()*toMult);
                   x3 += (int)(Math.random()*toMult);
                   y3 += (int)(Math.random()*toMult);
                   x4 += (int)(Math.random()*toMult);
                   y4 += (int)(Math.random()*toMult);
                    */
                }
                else if(i == 0 && x > 0){
                   x1 = coords[i][x-1][2][4];
                   y1 = coords[i][x-1][2][5];
                   x2 = coords[i][x-1][2][6];
                   y2 = coords[i][x-1][2][7];
                   
                   /*
                   x3 += (int)(Math.random()*toMult);
                   y3 += (int)(Math.random()*toMult);
                   x4 += (int)(Math.random()*toMult);
                   y4 += (int)(Math.random()*toMult);
                    */
                }
                else if(i > 0 && x == 0){
                    x1 = coords[i-1][x][2][2];
                    y1 = coords[i-1][x][2][3];
                    x3 = coords[i-1][x][2][6];
                    y3 = coords[i-1][x][2][7];
                    /*
                    x2 += (int)(Math.random()*toMult);
                    y2 += (int)(Math.random()*toMult);
                    x4 += (int)(Math.random()*toMult);
                    y4 += (int)(Math.random()*toMult);
                    */
                }
                else{
                    x1 = coords[i][x-1][2][4];
                    y1 = coords[i][x-1][2][5];
                    x2 = coords[i][x-1][2][6];
                    y2 = coords[i][x-1][2][7];
                    x3 = coords[i-1][x][2][6];
                    y3 = coords[i-1][x][2][7];
                    /*
                    x4 += (int)(Math.random()*toMult);
                    y4 += (int)(Math.random()*toMult);
                    */
                    
                }
                int[] coordinates = {x1,y1,x2,y2,x3,y3, x4,y4};
                
                if(speed == initialSpeed)
                coords[i][x][0] = coordinates;
                
                coords[i][x][2] = coordinates;
                int Ca1 = (int)(Colora1+((double)Colorb1-Colora1)*(speed/initialSpeed));
                int Ca2 = (int)(Colora2+((double)Colorb2-Colora2)*(speed/initialSpeed));
                int Ca3 = (int)(Colora3+((double)Colorb3-Colora3)*(speed/initialSpeed));
                
                //g.setColor(new Color(Ca1,Ca2,Ca3));
                g.setColor(new Color(0,0,55));
                paintSquare(g,coordinates);
                g.setColor(Color.gray);
                //g.setColor(Color.black);
                drawSquare(g,coordinates);
                
            }
        }
        firstTime = false;
    }
    
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if(!cangrow && e.getKeyCode() == KeyEvent.VK_I){
            cangrow = true;
        }
        if(!canshrink && e.getKeyCode() == KeyEvent.VK_O){
            canshrink = true;
        }
        if(KeyEvent.VK_DOWN == e.getKeyCode()){
            offsety +=50;
        }
        else if(KeyEvent.VK_UP == e.getKeyCode()){
            offsety -=50;
        }
        else if(KeyEvent.VK_RIGHT == e.getKeyCode()){
            offsetx +=50;
        }
        else if(KeyEvent.VK_LEFT == e.getKeyCode()){
            offsetx -=50;
        }
    }
    public void keyReleased(KeyEvent e) {
        if(cangrow && e.getKeyCode() == KeyEvent.VK_I){
            cangrow = false;
        }
        if(canshrink && e.getKeyCode() == KeyEvent.VK_O){
            canshrink = false;
        }   
    }

    public void paintSquare(Graphics g, int[] coordinates){
        int[] pointsx = {coordinates[0],coordinates[4],coordinates[2]};
        int[] pointsy = {coordinates[1],coordinates[5],coordinates[3]};
        g.fillPolygon(pointsx, pointsy, 3);
        int[] pointsx2 = {coordinates[4],coordinates[6],coordinates[2]};
        int[] pointsy2 = {coordinates[5],coordinates[7],coordinates[3]};
        g.fillPolygon(pointsx2, pointsy2, 3);
    }
    public void drawSquare(Graphics g, int[] coordinates){
        g.drawLine(coordinates[0], coordinates[1], coordinates[2], coordinates[3]);
        g.drawLine(coordinates[0], coordinates[1], coordinates[4], coordinates[5]);
        //g.drawLine(coordinates[0], coordinates[1], coordinates[6], coordinates[7]);
        g.drawLine(coordinates[2], coordinates[3], coordinates[4], coordinates[5]);
        g.drawLine(coordinates[6], coordinates[7], coordinates[4], coordinates[5]);
        g.drawLine(coordinates[2], coordinates[3], coordinates[6], coordinates[7]);
    }
    public void actionPerformed(ActionEvent e) {
       
        if(speed+1 >initialSpeed){
            speed = 1;
            //t.stop();
        }
        else{
            speed++;
        }
        if(cangrow){
            sizex += 25;
            sizey += 25;
        }
        else if(canshrink){
            sizex -= 25;
            sizey -= 25;
        }
        repaint();
    }
}
