package agar.io;

/**
 *
 * @author drjeoffreycruzada
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.Timer;
import java.net.InetAddress;
import java.util.*;
public class MainMenu  extends JPanel implements ActionListener, MouseMotionListener,KeyListener,MouseListener {
    Timer t;
    MenuBG bg = new MenuBG();
    JFrame frame;
    boolean frame1 = true;
    boolean choose1 = false;
    boolean choose2 = false;
    boolean choose3 = false;
    boolean choose4 = false;
    boolean choose5 = false;
    
    
    boolean enabled = true;
    String newString = "localhost";
    
    int animSlide1 = 1440;
    int animSlide2 = 1440;
    int animSlide3 = 1440;
    public MainMenu(){
        frame = new JFrame();
        frame.setSize(1440,900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        JPanel cont = new JPanel();
        cont.setLayout(null);
        setBounds(0,0,1500,900);
        cont.add(this);
        frame.add(cont);
        this.setFocusable(true);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.grabFocus();
        t = new Timer(100,this);
        t.start();
        /*try{
            //newString = InetAddress.getLocalHost().getHostAddress();
        }
        catch(Exception v){System.out.println(v);}*/
    }
    
    public void paint(Graphics g){
        bg.paintThis(g);
        paintFrame1(g);
        paintFrame2(g);
        
    }
    
    public void paintFrame1(Graphics g){
        if(!choose1)
        g.setColor(Color.blue);
        else
        g.setColor(new Color(50,50,255));
        g.fillRect(470-(1440-animSlide1), 400, 500, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("CONNECT", 470-(1440-animSlide1), 490);
        
        if(!choose2)
        g.setColor(Color.blue);
        else
        g.setColor(new Color(50,50,255));
        g.fillRect(470-(1440-animSlide2), 550, 500, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("SET UP!", 510-(1440-animSlide2), 640);
        
        if(!choose3)
        g.setColor(Color.red);
        else
        g.setColor(new Color(255,50,50));
        g.fillRect(470-(1440-animSlide3), 700, 500, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("Exit", 620-(1440-animSlide3), 790);
    }
    public void paintFrame2(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(470+animSlide1, 400, 500, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("ENTER IP", 470+animSlide1, 490);
        
        drawBox(g,newString);
        
        if(!choose4)
        g.setColor(Color.red);
        else
        g.setColor(new Color(255,50,50));
        g.fillRect(330+animSlide3, 700, 380, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("Cancel", 330+animSlide3, 790);
        
        if(!choose5)
        g.setColor(Color.green);
        else
        g.setColor(new Color(50,255,50));
        g.fillRect(720+animSlide3, 700, 380, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString("Connect", 720+animSlide3, 790);
    }
    public void actionPerformed(ActionEvent e){
        bg.updateme();
        repaint();
        if(frame1){
            if(animSlide1+288 <= 1440){
                animSlide1+=288;
            }
            if(animSlide2+288 <= 1440 && animSlide1 >= 576){
                animSlide2+=288;
            }
            if(animSlide3+288 <= 1440 && animSlide2 >= 576){
                animSlide3+=288;
            }
        }
        else{
            if(animSlide1-288 >= 0){
                animSlide1-=288;
            }
            if(animSlide2-288 >= 0 && animSlide1 <= 864){
                animSlide2-=288;
            }
            if(animSlide3-288 >= 0 && animSlide2 <= 864){
                animSlide3-=288;
            }
        }
    }
    
    public void drawBox(Graphics g,String s){
        if(!choose2)
        g.setColor(Color.gray);
        else
        g.setColor(new Color(125,125,125));
        g.fillRect(330+animSlide2, 550, 770, 100);
        g.setFont(new Font("ARIAL",100,100));
        g.setColor(Color.white);
        g.drawString(s, 340+animSlide2, 640);
    }
    public static void main(String[] args) {
        
        MainMenu panel = new MainMenu();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e){
        double pointx = e.getPoint().getX();
        double pointy = e.getPoint().getY();
        if(pointx >= 470 && pointy >= 400 && pointx<970 && pointy <=500){
            choose1 = true;choose2=false;choose3=false;choose4=false;choose5=false;
        }
        else if(pointx >= 470 && pointy >= 550 && pointx<970 && pointy <=650){
            choose1 = false;choose2=true;choose3=false;choose4=false;choose5=false;
        }
        else if(pointx >= 470 && pointy >= 700 && pointx<970 && pointy <=800 && frame1){
            choose1 = false;choose2=false;choose3=true;choose4=false;choose5=false;
        }
        //g.fillRect(330+animSlide3, 700, 380, 100);
        else if(pointx >=330 && pointy >= 700 && pointx<710 && pointy <=800 && !frame1){
            choose1 = false;choose2=false;choose3=true;choose4=true;choose5=false;
        }
        //g.fillRect(720+animSlide3, 700, 380, 100);
        else if(pointx >= 720 && pointy >= 700 && pointx<970 && pointy <=800 && !frame1){
            choose1 = false;choose2=false;choose3=true;choose4=false;choose5=true;
        }
        else{
            choose1 = false;choose2=false;choose3=false;choose4=false;choose5=false;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
        if(!frame1&&animSlide3 ==0){
            if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
                newString += e.getKeyChar();
            }
            else if(newString.length() >0){
                newString = newString.substring(0,newString.length()-1);
            }
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        double mousex = e.getPoint().getX();
        double mousey = e.getPoint().getY();
        if(!frame1&&animSlide3 ==0){
            if(mousex>= 330 && mousex <= 1100 && mousey >=550 && mousey <= 650){
                newString = "";
            }
            else if(mousex >= 470 && mousey >= 550 && mousex<970 && mousey <=650){
            }
            //g.fillRect(330+animSlide3, 700, 380, 100);
            else if(mousex >= 330 && mousex <= 710 && mousey >= 700 && mousey<=800){
                frame1= true;
            }
            else if(mousex >= 720 && mousey >= 700 && mousex<970 && mousey <=800&&enabled){
                enabled = false;
            ClientThread ct = new ClientThread(newString,6969);
                ct.start();
                frame.dispose();
        }
        }
        else if(frame1&&animSlide3 ==1440){
            if(mousex >= 470 && mousex <= 870 && mousey >= 400 && mousey<=500){
                frame1 = false;
            }
            else if(mousex >= 470 && mousey >= 550 && mousex<970 && mousey <=650&& enabled){
                enabled = false;
                System.out.println("yeaaa");
                frame.dispose();
                ServerThread meh = new ServerThread();
                meh.start();
                ClientThread ct = new ClientThread("localhost",6969);
                ct.start();
                
            }
        }
    }
        //470-(1440-animSlide1), 400, 500, 100)
    
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}