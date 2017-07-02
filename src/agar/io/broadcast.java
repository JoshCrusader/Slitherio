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
public class broadcast extends Thread {
    mySocket client;
    InputStreamReader is;
    Scanner sc;
    ServerThread st;
    public broadcast(mySocket cs, ServerThread st){
        client = cs;
        this.st = st;
        try{
        is = new InputStreamReader(client.socket.getInputStream());
        sc = new Scanner(is);
        }
        catch(Exception v){}
    }
    public void run(){
        while(client == null){}
        while(true){
            while(!sc.hasNext()){}
            String message = sc.nextLine();
            
            if(message.charAt(0) == 'I'){
                String sn;
                double distx;
                double disty;
                int reserve = 1;
                int counter = 1;
                while(message.charAt(counter) != '!'){
                    counter++;
                }
                sn = message.substring(reserve,counter);
                reserve = counter+1;
                counter = counter+1;
                while(message.charAt(counter) != '!'){
                    counter++;
                }
                distx = Double.parseDouble(message.substring(reserve,counter));
                reserve = counter+1;
                counter = counter+1;
                while(message.charAt(counter) != '!'){
                    counter++;
                }
                disty = Double.parseDouble(message.substring(reserve,counter));
                for(int i = 0; i < st.world.enemies.size();i++){
                    if(st.world.enemies.get(i).sn.equals(sn)){
                    st.world.enemies.get(i).directionx = distx;
                    st.world.enemies.get(i).directiony = disty;
                    }
                }
            }
            else if(message.charAt(0) == 'S'){
                String sn;
                int reserve = 1;
                int counter = 1;
                sn = message.substring(1,message.indexOf('!'));
                boolean truth = Boolean.parseBoolean(message.substring(message.indexOf('!')+1));
                for(int i = 0; i < st.world.enemies.size();i++){
                    if(st.world.enemies.get(i).sn.equals(sn)){
                    st.world.enemies.get(i).shrink = truth;
                    }
                }
            }
            /*
            for(int i = 0; i < st.clients.size();i++){
                Socket eClient = st.clients.get(i).socket;
                try{
                    OutputStreamWriter os = new OutputStreamWriter(eClient.getOutputStream());
                    PrintWriter pw = new PrintWriter(os);
                    pw.println(message);
                    pw.flush();
                }
                catch(Exception f){}
            }
            */
        }
    }
}
