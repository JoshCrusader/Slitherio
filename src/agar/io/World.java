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
public class World {
    
    public ArrayList<Pints> paints = new ArrayList<>();
    int x = 1000;
    int y = 1000;
    int serial = 0;
    
    ArrayList<Snake> enemies = new ArrayList<>();
    Snake mySnake;
    
    //double myx = 7500;
    //double myy = 7500;
    
    ArrayList<food> foods = new ArrayList<>();
    public World(Snake s){
        mySnake = s;
        double degree = -360+Math.random()*720;
        double length = -7500+Math.random()*15000;
        //myx = 7500+(int)(length*Math.sin(degree));
        //myy = 7500+(int)(length*Math.cos(degree));
        /*
        for(int i = 0; i < 1530; i++){
            degree = -360+Math.random()*720;
            length = -7500+Math.random()*15000;
            int x = 7500+(int)(length*Math.sin(degree));
            int y = 7500+(int)(length*Math.cos(degree));
            foods.add(new food(x,y,10,10,0.5,4,serial+""));
            serial++;
        }
        */
        /*
        for(int i = 0; i < 9; i++){
            int[] newTail = {mySnake.x,mySnake.y};
            mySnake.tail.add(newTail);
        }
        */
        //mySnake.x = (int)myx;
        //mySnake.y = (int)myy;
    }
}
