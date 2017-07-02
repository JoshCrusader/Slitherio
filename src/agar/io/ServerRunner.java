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
import javax.swing.Timer;
import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class ServerRunner extends Thread implements ActionListener {
    ServerThread st;
    Timer t;
    double ratiox;
    double ratioy;
    public ServerRunner(ServerThread st){
        this.st = st;
        t = new Timer(100,this);
        t.start();
    }
    
    public void actionPerformed(ActionEvent e){
        for(int i = 0; i < st.world.enemies.size();i++){
            updateSnake(st.world.enemies.get(i));
        }
    }
    
    
    public void updateSnake(Snake mySnake){
        double degree = Math.toDegrees(Math.atan2(mySnake.directionx,mySnake.directiony));
        
        
        for(int i = 0; i < st.world.foods.size();i++){
            food pagkain = st.world.foods.get(i);
            double l1 = Math.abs((mySnake.x+mySnake.size/2)-(pagkain.x+pagkain.sizex/2));
            double l2 = Math.abs((mySnake.y+mySnake.size/2)-(pagkain.y+pagkain.sizey/2));
            if(Math.sqrt(l1*l1+l2*l2)<mySnake.size/2) {
                mySnake.size+= pagkain.nutrition;
                    for(int addtail = 0; addtail < pagkain.baby; addtail++){
                    int[] newTail = {mySnake.tail.get(mySnake.tail.size()-1)[0],mySnake.tail.get(mySnake.tail.size()-1)[1]};
                    mySnake.tail.add(newTail);
                    }
                broadcastMessage("E"+mySnake.sn+"!"+pagkain.sn+"!");
                st.world.foods.remove(pagkain);
            }
        }
        for(int i = 0; i < st.world.enemies.size();i++){
            if(!st.world.enemies.get(i).sn.equals(mySnake.sn)){
                Snake kalaban = st.world.enemies.get(i);
                for(int z = 0; z < kalaban.tail.size();z++){
                    int x1=mySnake.x;
                    int x2=kalaban.tail.get(z)[0];
                    int y1=mySnake.y;
                    int y2=kalaban.tail.get(z)[1];
                    double size1=mySnake.size;
                    double size2=kalaban.size;
                    double range1 = Math.abs((x1+size1/2)-(x2+size2/2));
                    double range2 = Math.abs((y1+size1/2)-(y2+size2/2));
                    double range = Math.sqrt(range1*range1+range2*range2);
                    if(range <= size1/2+size2/2){
                        
                        DIE(mySnake);
                        break;
                    }
                }
            }
        }
        adjustSnake(degree,mySnake);
        double adistx = Math.abs(7500-(mySnake.x+ratiox+mySnake.size/2));
        double adisty = Math.abs(7500-(mySnake.y+ratioy+mySnake.size/2));
        if(Math.sqrt(adistx*adistx+adisty*adisty) <=7500){
        mySnake.x+=((ratiox));
        mySnake.y+=((ratioy));
        }
        broadcastMessage("G"+mySnake.x+"!"+mySnake.y+"!"+mySnake.sn+"!");
    }
    public void adjustSnake(double degree,Snake mySnake){
        
        for(int i = mySnake.tail.size()-1; i >=1;i--){
            mySnake.tail.get(i)[0] = mySnake.tail.get(i-1)[0];
            mySnake.tail.get(i)[1] = mySnake.tail.get(i-1)[1];
            
        }
        mySnake.tail.get(0)[0] = mySnake.x;
        mySnake.tail.get(0)[1] = mySnake.y;
        double centerx = ((mySnake.x)+mySnake.size/2);//Math.round(x+ratiox+world.mySnake.size/2);
        double centery = ((mySnake.y)+mySnake.size/2);//Math.round(world.mySnake.y+ratioy+world.mySnake.size/2);
        double tailcx = (mySnake.tail.get(0)[0]+mySnake.size/2);//Math.round(world.mySnake.tail.get(0)[2]+world.mySnake.size/2);
        double tailcy = (mySnake.tail.get(0)[1]+mySnake.size/2);//Math.round(world.mySnake.tail.get(0)[3]+world.mySnake.size/2);
        double newx = centerx+(tailcx-centerx)*(mySnake.size/10.0)-(mySnake.size/2);
        double newy = centery+(tailcy-centery)*(mySnake.size/10.0)-(mySnake.size/2);
        mySnake.tail.get(0)[0] = (int)newx;
        mySnake.tail.get(0)[1] = (int)newy;
        
       if(mySnake.shrink && mySnake.tail.size() > 9){
           broadcastMessage("B"+(mySnake.tail.get(mySnake.tail.size()-1)[0]-2+(int)(mySnake.size/2))+"#"+(mySnake.tail.get(mySnake.tail.size()-1)[1]-2+(int)(mySnake.size/2))+"*"+5+"!"+0.125+"%"+1+"^"+st.world.serial+"$"+mySnake.sn);
          
           st.world.foods.add(new food(mySnake.tail.get(mySnake.tail.size()-1)[0]-2+(int)(mySnake.size/2),mySnake.tail.get(mySnake.tail.size()-1)[1]-2+(int)(mySnake.size/2),5,5,0.125,1,st.world.serial+""));
           st.world.serial++;
           mySnake.size-=0.125;
           mySnake.tail.remove(mySnake.tail.size()-1);
           ratiox = (15/3+(mySnake.size/50))*Math.sin(Math.toRadians(degree));//speed
           ratioy = (15/3+(mySnake.size/50))*Math.cos(Math.toRadians(degree)); 
        }
       else{
        ratiox = (10/3+(mySnake.size/50))*Math.sin(Math.toRadians(degree));//speed
        ratioy = (10/3+(mySnake.size/50))*Math.cos(Math.toRadians(degree));
        }
    }
    public void DIE(Snake mySnake){
       broadcastMessage(">"+mySnake.sn);
        for(int i =0; i < mySnake.tail.size();i++){
           int[] tali = mySnake.tail.get(i);
           broadcastMessage("P"+(tali[0]+(int)(mySnake.size/2)-(int)(mySnake.size/4))+"#"+(tali[1]+(int)(mySnake.size/2)-(int)(mySnake.size/4))+"*"+(int)(mySnake.size/2)+"!"+0.125+"%"+1+"^"+st.world.serial);
          
           st.world.foods.add(new food(tali[0]+(int)(mySnake.size/2)-(int)(mySnake.size/4),tali[1]+(int)(mySnake.size/2)-(int)(mySnake.size/4),(int)(mySnake.size/2),(int)(mySnake.size/2),0.125,1,st.world.serial+""));
           st.world.serial++;

        }
        st.world.enemies.remove(mySnake);
    }
    
    public void broadcastMessage(String message){
        for(int i = 0; i < st.clients.size();i++){
            Socket client = st.clients.get(i).socket;
            try{
                OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
                PrintWriter pw = new PrintWriter(os);
                pw.println(message);
                pw.flush();
            }
            catch(Exception f){}
        }
    }
}
