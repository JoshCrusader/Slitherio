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
public class ClientGUI extends JPanel implements ActionListener, KeyListener,MouseMotionListener{
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
    public ClientGUI(){
        this.water = new WaterBG();
        this.setFocusable(true);
        this.grabFocus();
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
        repaint();
        
    }
    
    
    boolean candraw = false;
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(50.0/world.mySnake.size*0.5,50.0/world.mySnake.size*0.5);
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
        for(int i = 0; i < world.foods.size();i++){
            food eat = world.foods.get(i);
            g.fillOval(eat.x, eat.y, eat.sizex, eat.sizey);
        }
        //PAINT THY ENEMIES AND THY SINS
        for(int i = 0; i < world.enemies.size();i++){
            Snake kalaban = world.enemies.get(i);
            for(int f = 0; f < kalaban.tail.size();f++){
                g2d.fillOval(kalaban.tail.get(f)[0], kalaban.tail.get(f)[1], (int)kalaban.size, (int)kalaban.size);
            }
            g2d.fillOval(kalaban.x, kalaban.y, (int)kalaban.size, (int)kalaban.size);
        }
        //PAINT THY SELF AND THY GLORY
        g.setColor(Color.white);
        for(int i = world.mySnake.tail.size()-1; i >=0;i--){
            g2d.fillOval(world.mySnake.tail.get(i)[0], world.mySnake.tail.get(i)[1], (int)world.mySnake.size, (int)world.mySnake.size);
        }
        g.setColor(Color.white);
        g2d.fillOval(world.mySnake.x, world.mySnake.y, (int)(world.mySnake.size), (int)(world.mySnake.size));
        g.setColor(Color.darkGray);
        g2d.drawOval(world.mySnake.x, world.mySnake.y, (int)(world.mySnake.size), (int)(world.mySnake.size));
    }
    int cangrow = 0;
    
    public void actionPerformed(ActionEvent e){
        water.updateme();
        double degree = Math.toDegrees(Math.atan2(distx,disty));
        
        
        for(int i = 0; i < world.foods.size();i++){
            food pagkain = world.foods.get(i);
            double l1 = Math.abs((world.mySnake.x+world.mySnake.size/2)-(pagkain.x+pagkain.sizex/2));
            double l2 = Math.abs((world.mySnake.y+world.mySnake.size/2)-(pagkain.y+pagkain.sizey/2));
            if(Math.sqrt(l1*l1+l2*l2)<world.mySnake.size/2) {
                world.mySnake.size+= pagkain.nutrition;
                    for(int addtail = 0; addtail < pagkain.baby; addtail++){
                    int[] newTail = {world.mySnake.tail.get(world.mySnake.tail.size()-1)[0],world.mySnake.tail.get(world.mySnake.tail.size()-1)[1]};
                    world.mySnake.tail.add(newTail);
                    }
                world.foods.remove(pagkain);
                
            }
        }
        adjustSnake(degree);
        double adistx = Math.abs(7500-(world.mySnake.x+ratiox+world.mySnake.size/2));
        double adisty = Math.abs(7500-(world.mySnake.y+ratioy+world.mySnake.size/2));
        if(Math.sqrt(adistx*adistx+adisty*adisty) <=7500){
        world.mySnake.x+=((ratiox));
        world.mySnake.y+=((ratioy));
        }
        repaint();
    }
    
    public void adjustSnake(double degree){
        
        for(int i = world.mySnake.tail.size()-1; i >=1;i--){
            world.mySnake.tail.get(i)[0] = world.mySnake.tail.get(i-1)[0];
            world.mySnake.tail.get(i)[1] = world.mySnake.tail.get(i-1)[1];
            
        }
        world.mySnake.tail.get(0)[0] = world.mySnake.x;
        world.mySnake.tail.get(0)[1] = world.mySnake.y;
        double centerx = ((world.mySnake.x)+world.mySnake.size/2);//Math.round(x+ratiox+world.mySnake.size/2);
        double centery = ((world.mySnake.y)+world.mySnake.size/2);//Math.round(world.mySnake.y+ratioy+world.mySnake.size/2);
        double tailcx = (world.mySnake.tail.get(0)[0]+world.mySnake.size/2);//Math.round(world.mySnake.tail.get(0)[2]+world.mySnake.size/2);
        double tailcy = (world.mySnake.tail.get(0)[1]+world.mySnake.size/2);//Math.round(world.mySnake.tail.get(0)[3]+world.mySnake.size/2);
        double newx = centerx+(tailcx-centerx)*(world.mySnake.size/10.0)-(world.mySnake.size/2);
        double newy = centery+(tailcy-centery)*(world.mySnake.size/10.0)-(world.mySnake.size/2);
        world.mySnake.tail.get(0)[0] = (int)newx;
        world.mySnake.tail.get(0)[1] = (int)newy;
        
       if(shrink && world.mySnake.tail.size() > 9){
           world.foods.add(new food(world.mySnake.tail.get(world.mySnake.tail.size()-1)[0]-2+(int)(world.mySnake.size/2),world.mySnake.tail.get(world.mySnake.tail.size()-1)[1]-2+(int)(world.mySnake.size/2),5,5,0.125,1,"sn"));
           world.mySnake.size-=0.125;
           world.mySnake.tail.remove(world.mySnake.tail.size()-1);
           ratiox = (15/3+(world.mySnake.size/50))*Math.sin(Math.toRadians(degree));//speed
           ratioy = (15/3+(world.mySnake.size/50))*Math.cos(Math.toRadians(degree)); 
        }
       else{
        ratiox = (10/3+(world.mySnake.size/50))*Math.sin(Math.toRadians(degree));//speed
        ratioy = (10/3+(world.mySnake.size/50))*Math.cos(Math.toRadians(degree));
        }
    }
    public void keyTyped(KeyEvent e) {
    }
    
    boolean shrink = false;
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            world.mySnake.x-=35;
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            world.mySnake.x+=35;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            world.mySnake.y+=35;
        }
        else if(e.getKeyCode() == KeyEvent.VK_UP){
            world.mySnake.y-=35;
        }
        else if(e.getKeyCode() == KeyEvent.VK_I){
            world.mySnake.size+=50;
            world.mySnake.x-=25;
            world.mySnake.y-=25;
        }
        else if(e.getKeyCode() == KeyEvent.VK_O && world.mySnake.size-50 >0){
            world.mySnake.size-=50;
            world.mySnake.x+=25;
            world.mySnake.y+=25;
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            shrink = true;
        }
    }
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            shrink = false;
        }
    }
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        frame.setSize(720,450);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        ClientGUI panel = new ClientGUI();
        panel.world = new World(new Snake(7500,7500,10,"RAWR"));
        for(int i = 0; i < 1530; i++){
            double degree = -360+Math.random()*360;
            double length = -7500+Math.random()*15000;
            int x = 7500+(int)(length*Math.sin(degree));
            int y = 7500+(int)(length*Math.cos(degree));
            panel.world.foods.add(new food(x,y,10,10,0.5,4,panel.world.serial+""));
            panel.world.serial++;
        }
        panel.camerax=panel.world.mySnake.x;
        panel.cameray=panel.world.mySnake.y;
        panel.t = new Timer(0,panel);
        panel.t.start();
        frame.add(panel);
        panel.updateUI();
    }
    
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getPoint().getX();
        mouseY = e.getPoint().getY();
        distx = ((camerax+(mouseX-360)*(world.mySnake.size/50))-world.mySnake.x);
        disty = ((cameray+(mouseY-225)*(world.mySnake.size/50))-world.mySnake.y);
    }
}
