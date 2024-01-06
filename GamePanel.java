import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75; 
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    //Constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    //Start Game Method
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    //Paint Component Method
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    //Draw Method
    public void draw(Graphics g){
        //Drawing Grid Lines 
        //X-Axis
        for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
        }
        //Y-Axis
        for (int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE );
        }
        //Apple figures on the Screen
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for(int i = 0; i < bodyParts; i++){
            if(i==0){
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else{
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

            }
        }

    }
    //New Apple Method
    public void newApple(){
        //Creating random co-ordinates for the position of the Apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    //Move Method   
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction){
            case 'U': //UP
                y[0] = y[0]-UNIT_SIZE;
                break;
            case 'D'://DOWN
                y[0] = y[0]+UNIT_SIZE;
                break;
            case 'L'://LEFT
                x[0] = x[0]-UNIT_SIZE;
                break;
            case 'R'://RIGHT
                x[0] = x[0]+UNIT_SIZE;
                break;
        }
    }
    //Check Apple Method
    public void checkApple(){

    }
    //Check Collisions Method
    public void checkCollisions(){
        for(int i = bodyParts;i>0;i--){
            //Checks if the head colided with the body
            if((x[0] == x[i]) && (y[0] == y[i]) ){
                //Works as a gameOver setup
                running = false;
            }
        }
        //checks if head touches left border
        if(x[0] < 0){
            running = false;
        }
        //checks if head touches right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        //checks if head touches top border
        if(y[0] < 0){
            running = false;
        }
        //checks if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }  
        //Stop the Timer , if game it's not running
        if(!running){
            timer.stop();
        }
    }
    //Game Over Method
    public void gameOver(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Checks if the game is running
        if(running){
            move();
            checkApple();
            checkCollisions();
        }  
        repaint();
    }
    //'My Key Adapter' Interclass 
    public class MyKeyAdapter extends KeyAdapter{
        //Key Pressed Method (Over-ridden)
        @Override
        public void keyPressed(KeyEvent e){
            
        }
    }
    
    
}
