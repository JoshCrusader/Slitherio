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
import java.util.*;
public class Snake {
    
    int x = 7500;
    int y = 7500;
    double size = 10;
    ArrayList<int[]> tail = new ArrayList<>();
    String sn;
    double directionx = 7500;
    double directiony = 7500;
    Boolean shrink = false;
    
    Color color = Color.white;
    public Snake(int x, int y, double size, String name){
        this.x=x;
        this.y=y;
        this.size=size;
        this.sn = name;
        
        for(int i = 0; i < size; i++){
            int[] coords = {x,y};
            tail.add(coords);
        }
        
    }
    public void changeColor(String c){
        if(c.equals("w")){
            color = Color.white;
        }
    }
}
