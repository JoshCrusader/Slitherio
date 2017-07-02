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
public class ServerThread extends Thread{
    ServerSocket ss;
    ArrayList<mySocket> clients = new ArrayList<>();
    int sn = 0;
    World world = new World(new Snake(100,100,0,""));
    public ServerThread(){
        for(int i = 0; i < 1530; i++){
            double degree = -360+Math.random()*720;
            double length = -7500+Math.random()*15000;
            int x = 7500+(int)(length*Math.sin(degree));
            int y = 7500+(int)(length*Math.cos(degree));
            world.foods.add(new food(x,y,10,10,0.5,4,world.serial+""));
            world.serial++;
        }
    }
    
    public void run(){
       try{
            Socket former = null;
            ss = new ServerSocket(6969);
            ServerRunner sr = new ServerRunner(this);
            sr.start();
            Socket client = null;
            while(true){
                client = ss.accept();
                welcomeToTheClub(client);
                
            }
        }
        catch(Exception e){} 
    }
    //C - this is your snake client
    //M - guys make a new snake for this client
    public void welcomeToTheClub(Socket client){
        mySocket ms = new mySocket(client,sn+"");
        double degree = -360+Math.random()*720;
        double length = -7500+Math.random()*15000;
        int myx = 7500+(int)(length*Math.sin(degree));
        int myy = 7500+(int)(length*Math.cos(degree));
        try{
            OutputStreamWriter os = new OutputStreamWriter(client.getOutputStream());
            PrintWriter pw = new PrintWriter(os);
            String data = "C"+myx+"!"+myy+"!"+sn+"!";
            pw.println(data);
            for(int i = 0; i < world.foods.size();i++){
                food pagkain = world.foods.get(i);
                pw.println("F"+pagkain.x+"#"+pagkain.y+"*"+pagkain.sizex+"!"+pagkain.nutrition+"%"+pagkain.baby+"^"+pagkain.sn);
            }
            for(int i = 0; i < world.enemies.size();i++){
                Snake pagkain = world.enemies.get(i);
                pw.println("O"+pagkain.x+"#"+pagkain.y+"*"+pagkain.size+"!"+pagkain.sn);
            }
            pw.println("...");
            pw.flush();
            
        }
        catch(Exception f){}
        
        world.enemies.add(new Snake(myx,myy,10,sn+""));
        for(int i = 0; i < clients.size();i++){
            Socket eClient = clients.get(i).socket;
            try{
                OutputStreamWriter os = new OutputStreamWriter(eClient.getOutputStream());
                PrintWriter pw = new PrintWriter(os);
                String data = "M"+myx+"!"+myy+"!"+sn+"!";
                pw.println(data);
                pw.flush(); 
            }
            catch(Exception f){}
        }
        broadcast bc = new broadcast(ms,this);
        bc.start();
        
        clients.add(ms);
        sn++;
    }
}
