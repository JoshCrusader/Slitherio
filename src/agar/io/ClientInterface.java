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
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
public class ClientInterface extends JPanel implements ActionListener, KeyListener,MouseMotionListener{
    Timer t;
    double mouseX = 0;
    double mouseY = 0;
    double distx = 0;
    double disty = 0;
    double ratiox = 0;
    double ratioy = 0;
    double camerax = 100;
    double cameray = 100;
    World world;
    WaterBG water;
    ClientThread myThread;
    public ClientInterface(ClientThread ct){
        
        JFrame frame = new JFrame();
        frame.setSize(1440,900);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        this.water = new WaterBG();
        this.setFocusable(true);
        this.grabFocus();
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
        this.myThread = ct;
        world = myThread.world;
        camerax = world.mySnake.x;
        cameray = world.mySnake.y;
        t = new Timer(100,this);
        t.start();
        frame.add(this);
        repaint();
        
    }
    
    
    boolean candraw = false;
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(50.0/world.mySnake.size,50.0/world.mySnake.size);
        double translatex = 720/(50.0/world.mySnake.size)-world.mySnake.x-world.mySnake.size/2;
        double translatey = 450/(50.0/world.mySnake.size)-world.mySnake.y-world.mySnake.size/2;
        if((double)(world.mySnake.x+world.mySnake.size/2)-world.mySnake.size*(15) <=0|| (double)(world.mySnake.x+world.mySnake.size/2)+world.mySnake.size*(15) >= 15000){
        if((double)(world.mySnake.x+world.mySnake.size/2)+world.mySnake.size*(15) >= 15000){
            camerax = (15000-world.mySnake.size*15);
            translatex = 720/(50.0/world.mySnake.size)-(15000-world.mySnake.size*15);}
        else if((double)(world.mySnake.x+world.mySnake.size/2)-world.mySnake.size*(15) <=0){camerax = (world.mySnake.size*15);
            translatex = 720/(50.0/world.mySnake.size)-(world.mySnake.size*15);}
        }
        else{
            camerax = world.mySnake.x;
        }
        if(((double)(world.mySnake.y+world.mySnake.size/2)+world.mySnake.size*(10.0) >= 15000) || ((double)(world.mySnake.y+world.mySnake.size/2)-world.mySnake.size*(10.0)<= 0)){
        if((double)(world.mySnake.y+world.mySnake.size/2)+world.mySnake.size*(10.0) >= 15000){cameray = (15000-world.mySnake.size*10);
            translatey = 450/(50.0/world.mySnake.size)-(15000-world.mySnake.size*10);}
        else if((double)(world.mySnake.y+world.mySnake.size/2)-world.mySnake.size*(10.0)<= 0){cameray = (world.mySnake.size*10);
            translatey = 450/(50.0/world.mySnake.size)-(world.mySnake.size*10);}
        }
        else{
            cameray = world.mySnake.y;
        }
        g2d.translate(translatex,translatey);
        water.paintThis(g);
        g2d.setStroke(new BasicStroke(50));
          g.setColor(Color.orange);
        g.drawOval(0, 0, 15000, 15000);
        g2d.setStroke(new BasicStroke());
        g.setColor(Color.blue);
        for(int i = 0; i < myThread.world.foods.size();i++){
            food eat = myThread.world.foods.get(i);
            g.fillOval(eat.x, eat.y, eat.sizex, eat.sizey);
        }
        //PAINT THY ENEMIES AND THY SINS
        
        g2d.setColor(Color.darkGray);
        for(int i = 0; i < world.enemies.size();i++){
            Snake kalaban = world.enemies.get(i);
            for(int f = 0; f < kalaban.tail.size();f++){
                g2d.fillOval(kalaban.tail.get(f)[0]-(int)(kalaban.size*0.05), kalaban.tail.get(f)[1]-(int)(kalaban.size*0.05), (int)(kalaban.size*1.1), (int)(kalaban.size*1.1));
            }
            
        }
        for(int i = 0; i < world.enemies.size();i++){
            Snake kalaban = world.enemies.get(i);
            for(int f = kalaban.tail.size()-1; f>=0;f--){
                g2d.setColor(kalaban.color);
                g2d.fillOval(kalaban.tail.get(f)[0], kalaban.tail.get(f)[1], (int)kalaban.size, (int)kalaban.size);
                g2d.setColor(Color.black);
                g2d.drawOval(kalaban.tail.get(f)[0], kalaban.tail.get(f)[1], (int)kalaban.size, (int)kalaban.size);
            }
            g2d.setColor(kalaban.color);
            g2d.fillOval(kalaban.x, kalaban.y, (int)kalaban.size, (int)kalaban.size);
            g2d.setColor(Color.black);
            g2d.drawOval(kalaban.x, kalaban.y, (int)kalaban.size, (int)kalaban.size);
        }
        //PAINT THY SELF AND THY GLORY
        g.setColor(Color.darkGray);
        for(int i = world.mySnake.tail.size()-1; i >=0;i--){
            g2d.fillOval(world.mySnake.tail.get(i)[0]-(int)(world.mySnake.size*0.1), world.mySnake.tail.get(i)[1]-(int)(world.mySnake.size*0.1), (int)(world.mySnake.size*1.2), (int)(world.mySnake.size*1.2));
        }
        g.setColor(world.mySnake.color);
        for(int i = world.mySnake.tail.size()-1; i >=0;i--){
            g.setColor(world.mySnake.color);
            g2d.fillOval(world.mySnake.tail.get(i)[0], world.mySnake.tail.get(i)[1], (int)world.mySnake.size, (int)world.mySnake.size);
            g.setColor(Color.black);
            g2d.drawOval(world.mySnake.tail.get(i)[0], world.mySnake.tail.get(i)[1], (int)world.mySnake.size, (int)world.mySnake.size);
        
        }
        g.setColor(world.mySnake.color);
        g2d.fillOval(world.mySnake.x, world.mySnake.y, (int)(world.mySnake.size), (int)(world.mySnake.size));
        g.setColor(Color.black);
        g2d.drawOval(world.mySnake.x, world.mySnake.y, (int)(world.mySnake.size), (int)(world.mySnake.size));
    }
    int cangrow = 0;
    
    public void actionPerformed(ActionEvent e){
        water.updateme();
        repaint();
    }
    
    public void keyTyped(KeyEvent e) {
    }
    
    boolean shrink = false;
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            //shrink = true;
            myThread.sendMessage("S"+myThread.sn+"!"+"true");
        }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            myThread.sendMessage("S"+myThread.sn+"!"+"false");
            //shrink = false;
        }
    }
    
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getPoint().getX();
        mouseY = e.getPoint().getY();
        distx = ((camerax+(mouseX-720)*(world.mySnake.size/50))-world.mySnake.x);
        disty = ((cameray+(mouseY-450)*(world.mySnake.size/50))-world.mySnake.y);
        myThread.sendMessage("I"+myThread.sn+"!"+distx+"!"+disty+"!");
        
    }
}
