import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

    //Constructor
    GamePanel(){

    }
    //Start Game Method
    public void startGame(){

    }
    //Paint Component Method
    public void paintComponent(Graphics g){

    }
    //Draw Method
    public void draw(Graphics g){

    }
    //Move Method   
    public void move(){

    }
    //Check Apple Method
    public void checkApple(){

    }
    //Check Collisions Method
    public void checkCollisions(){

    }
    //Game Over Method
    public void gameOver(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    //'My Key Adapter' Interclass 
    public class MyKeyAdapter extends KeyAdapter{
        //Key Pressed Method (Over-ridden)
        @Override
        public void keyPressed(KeyEvent e){
            
        }
    }
    
    
}
