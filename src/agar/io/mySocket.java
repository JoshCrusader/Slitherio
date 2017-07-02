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
import java.net.Socket;
public class mySocket {
    Socket socket;
    String sn;
    public mySocket(Socket socket, String sn){
        this.socket = socket;
        this.sn = sn;
    }
}
