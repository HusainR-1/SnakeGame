import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JButton;
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
    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction;
    JButton startButton;
    boolean running = false;
    boolean played = false;
    Timer timer;
    Random random;

    //Constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        createStartButton();
    }
    //Create "START" button
    private void createStartButton(){
        startButton = new JButton();
        startButton.setText("START GAME");
        startButton.setFont(new Font(Font.SANS_SERIF,Font.ITALIC,25));
        startButton.setBorderPainted(false);
        startButton.setForeground(Color.red);
        startButton.setBackground(Color.black);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
            }
        });
        //Using Flow Layout for this pannel
        setLayout(new FlowLayout(FlowLayout.CENTER,0,SCREEN_HEIGHT/2));
        //Adding the Start button to this pannel
        this.add(startButton); 
    }
    //Start Game Method
    public void startGame(){
        bodyParts = 6;
        applesEaten = 0;
        direction = 'R';

        // Initialize snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        //Start of the game
        newApple();
        running = true;
        startButton.setVisible(false); // Hide the "START" buttin
        timer = new Timer(DELAY, this);
        timer.start();
    }
    //Paint Component Method
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    //Grid Lines Method
    public void gridLines(Graphics g){
        //Drawing Grid Lines 
            //X-Axis
            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            }
            //Y-Axis
            for (int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++){
                g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE );
            }
    }
    //Draw Method
    public void draw(Graphics g){
        if (!running && !played){displayText(g,"Welcome!");}
        if(running){
            //Shows Grid Lines
            gridLines(g);
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
                    //For Random Colors (Rainbow Effect)
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Shows the score while the game is running
            showScore(g);  
        } 
        else if(played){
            gameOver(g);
        }     
    }
    //Show Score Method
    public void showScore(Graphics g){
        g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
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
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    //Check Collisions Method
    public void checkCollisions(){
        for(int i = bodyParts;i>0;i--){
            //Checks if the head colided with the body
            if((x[0] == x[i]) && (y[0] == y[i]) ){
                //Works as a gameOver setup
                running = false;
                played = true;
            }
        }
        //checks if head touches left border
        if(x[0] < 0){
            running = false;
            played = true;
        }
        //checks if head touches right border
        if(x[0] > SCREEN_WIDTH){
            running = false;
             played = true;
        }
        //checks if head touches top border
        if(y[0] < 0){
            running = false;
             played = true;
        }
        //checks if head touches bottom border
        if(y[0] > SCREEN_HEIGHT){
            running = false;
            played = true;
        }  
        //Stop the Timer , if game it's not running
        if(!running){
            timer.stop();
        }
    }
    //Display Text Method
    public void displayText(Graphics g,String textToDisplay){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(textToDisplay, (SCREEN_WIDTH - metrics.stringWidth(textToDisplay))/2, SCREEN_HEIGHT/3);
    }
    //Game Over Method
    public void gameOver(Graphics g){
        //Showing the score after game
        showScore(g);
        //Game-Over text
        displayText(g,"Game Over!");
        startButton.setText("Re-START");
        startButton.setVisible(true);
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
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: //Going Left
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT: //Going Right
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:  //Going Up
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN: //Going Down
                    if(direction != 'U'){
                        direction = 'D';
                    }
            
                default:
                    break;
            }
        }
    }
    
    
}
