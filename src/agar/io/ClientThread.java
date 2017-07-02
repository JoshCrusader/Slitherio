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
public class ClientThread extends Thread{
    Socket client;
    String sn;
    InputStreamReader is;
    Scanner sc;
    World world;
    double ratiox;
    double ratioy;
    public ClientThread(String ip, int port){
        try{
            client = new Socket(ip,port);/*
            OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
            PrintWriter pw = new PrintWriter(os);
            pw.println("@"+sn+"1");
            */
            
        }
        catch(Exception v){}
    }
    
    public void run(){
        while(client == null){}
        try{
            is = new InputStreamReader(client.getInputStream());
            Scanner sc = new Scanner(is);
            String message = "";
            while(!message.equals("...")){
                while(!sc.hasNext());
                message = sc.nextLine();
                if(message.charAt(0) == 'C'){
                    int x = 0;
                    int y = 0;
                    String sn  = "";
                    int reserve = 1;
                    int counter = 1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    x = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    y = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    sn = message.substring(reserve,counter);
                    this.sn = sn;
                    world = new World(new Snake(x,y,10,sn));

                }
                else if(message.charAt(0) == 'F'){
                    int x;
                    int y;
                    int size;
                    double nutrition;
                    int baby;
                    String sn;
                    int reserve = 1;
                    x = Integer.parseInt(message.substring(reserve,message.indexOf('#')));
                    y = Integer.parseInt(message.substring(message.indexOf('#')+1,message.indexOf('*')));
                    size = Integer.parseInt(message.substring(message.indexOf('*')+1,message.indexOf('!')));
                    nutrition = Double.parseDouble(message.substring(message.indexOf('!')+1,message.indexOf('%')));
                    baby = Integer.parseInt(message.substring(message.indexOf('%')+1,message.indexOf('^')));
                    sn = message.substring(message.indexOf('^')+1);
                    world.foods.add(new food(x, y,size, size, nutrition,baby, sn));
                    
                }
                else if(message.charAt(0) == 'O'){
                    System.out.println(message);
                    int x;
                    int y;
                    double size;
                    double nutrition;
                    int baby;
                    String sn;
                    int reserve = 1;
                    x = Integer.parseInt(message.substring(reserve,message.indexOf('#')));
                    y = Integer.parseInt(message.substring(message.indexOf('#')+1,message.indexOf('*')));
                    size = Double.parseDouble(message.substring(message.indexOf('*')+1,message.indexOf('!')));
                    sn =message.substring(message.indexOf('!')+1);
                    world.enemies.add(new Snake(x, y,size,sn));
                    
                }
            }
            
            ClientInterface CI = new ClientInterface(this);
            while(true){
                while(!sc.hasNext());
                message = sc.nextLine();
                if(message.charAt(0) == 'M'){
                    int x = 0;
                    int y = 0;
                    String sn  = "";
                    int reserve = 1;
                    int counter = 1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    x = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    y = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    sn = message.substring(reserve,counter);
                    world.enemies.add(new Snake(x,y,10,sn));
                }
                else if(message.charAt(0) == 'G'){
                    int x = 0;
                    int y = 0;
                    String sn  = "";
                    int reserve = 1;
                    int counter = 1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    x = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    y = Integer.parseInt(message.substring(reserve,counter));
                    reserve = counter+1;
                    counter = counter+1;
                    while(message.charAt(counter) != '!'){
                        counter++;
                    }
                    sn = message.substring(reserve,counter);
                    if(sn.equals(this.sn)){
                        updateSnake(world.mySnake,x,y);
                    }
                    else{
                        for(int i = 0; i < world.enemies.size();i++){
                            if(world.enemies.get(i).sn.equals(sn)){
                                updateSnake(world.enemies.get(i),x,y);
                            }
                        }
                    }
                }
                else if(message.charAt(0) == 'E'){
                String sn1 = "0";
                String sn2 = "0";
                int reserve = 1;
                int counter = 1;
                while(message.charAt(counter) != '!'){
                    counter++;
                }
                sn1 = message.substring(reserve,counter);
                reserve = counter+1;
                counter = counter+1;
                while(message.charAt(counter) != '!'){
                    counter++;
                }
                sn2 = message.substring(reserve,counter);
                reserve = counter+1;
                counter = counter+1;
                for(int i = 0;  i< world.foods.size();i++){
                    if(world.foods.get(i).sn.equals(sn2)){
                        food pagkain = world.foods.get(i);
                        if(sn1.equals(this.sn)){
                            world.mySnake.size+=pagkain.nutrition;
                            for(int addtail = 0; addtail < pagkain.baby; addtail++){
                                int[] newTail = {world.mySnake.tail.get(world.mySnake.tail.size()-1)[0],world.mySnake.tail.get(world.mySnake.tail.size()-1)[1]};
                                world.mySnake.tail.add(newTail);
                            }
                        }
                        else{
                            for(int f = 0; f < world.enemies.size();f++){
                                Snake kalaban = world.enemies.get(f);
                                if(kalaban.sn.equals(sn1)){
                                    kalaban.size+=pagkain.nutrition;
                                    for(int addtail = 0; addtail < pagkain.baby; addtail++){
                                        int[] newTail = {kalaban.tail.get(kalaban.tail.size()-1)[0],kalaban.tail.get(kalaban.tail.size()-1)[1]};
                                        kalaban.tail.add(newTail);
                                    }
                                }
                            }
                        }
                        world.foods.remove(i);
                    }
                }
                
                
            }
                else if(message.charAt(0) == 'B'){
                    int x;
                    int y;
                    int size;
                    double nutrition;
                    int baby;
                    String sn;
                    String sn2;
                    int reserve = 1;
                    x = Integer.parseInt(message.substring(reserve,message.indexOf('#')));
                    y = Integer.parseInt(message.substring(message.indexOf('#')+1,message.indexOf('*')));
                    size = Integer.parseInt(message.substring(message.indexOf('*')+1,message.indexOf('!')));
                    nutrition = Double.parseDouble(message.substring(message.indexOf('!')+1,message.indexOf('%')));
                    baby = Integer.parseInt(message.substring(message.indexOf('%')+1,message.indexOf('^')));
                    sn = message.substring(message.indexOf('^')+1,message.indexOf('$'));
                    sn2 = message.substring(message.indexOf('$')+1);
                    world.foods.add(new food(x, y,size, size, nutrition,baby, sn));
                    if(sn2.equals(this.sn)){
                        world.mySnake.size-=0.125;
                        world.mySnake.tail.remove(world.mySnake.tail.size()-1);
                    }
                    else{
                        for(int i = 0; i < world.enemies.size();i++){
                            Snake kalaban = world.enemies.get(i);
                            if(sn2.equals(kalaban.sn)){
                                kalaban.size-=0.125;
                                kalaban.tail.remove(kalaban.tail.size()-1);
                            }
                        }
                    }
                }
                else if(message.charAt(0) == 'P'){
                    int x;
                    int y;
                    int size;
                    double nutrition;
                    int baby;
                    String sn;
                    int reserve = 1;
                    x = Integer.parseInt(message.substring(reserve,message.indexOf('#')));
                    y = Integer.parseInt(message.substring(message.indexOf('#')+1,message.indexOf('*')));
                    size = Integer.parseInt(message.substring(message.indexOf('*')+1,message.indexOf('!')));
                    nutrition = Double.parseDouble(message.substring(message.indexOf('!')+1,message.indexOf('%')));
                    baby = Integer.parseInt(message.substring(message.indexOf('%')+1,message.indexOf('^')));
                    sn = message.substring(message.indexOf('^')+1);
                    world.foods.add(new food(x, y,size, size, nutrition,baby, sn));
                }
                else if(message.charAt(0) == '>'){
                    String sn = message.substring(1);
                    if(sn.equals(this.sn)){
                        System.exit(0);
                    }else{
                        for(int i = 0; i < world.enemies.size();i++){
                            if(world.enemies.get(i).sn.equals(sn)){
                                world.enemies.remove(world.enemies.get(i));
                            }
                        }
                    }
                }
                else{
                    System.out.println(message);
                }
            }
        }
        catch(Exception f){System.out.println(f);}
    }
    public void updateSnake(Snake mySnake,int x,int y){
        double degree = Math.toDegrees(Math.atan2(mySnake.directionx,mySnake.directiony));
        
        
        adjustSnake(degree,mySnake);
        mySnake.x=x;
        mySnake.y=y;
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
        
    }
    
    public void sendMessage(String msg){
        try{
        OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
        PrintWriter pw = new PrintWriter(os);
        pw.println(msg);
        pw.flush();
        }
        catch(Exception e){
            
        }
    }
}
