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
public class food {
    int x = 0;
    int y = 0;
    int sizex = 0;
    int sizey = 0;
    double nutrition;
    int baby;
    String sn;
    public food(int x, int y,int sizex, int sizey, double nutrition,  int baby, String sn){
        this.x = x;
        this.y = y;
        this.sizex = sizex;
        this.sizey = sizey;
        this.nutrition = nutrition;
        this.baby= baby;
        this.sn = sn;
    }
}
