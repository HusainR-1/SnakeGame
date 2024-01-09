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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600; // Width of the panel
    static final int SCREEN_HEIGHT = 600; // Height of the panel    
    static final int UNIT_SIZE = 25; // Each unit size
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // Total no. of units in the game
    static final int DELAY = 75; // Delay in the movement of the snake
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts;
    int objectsEaten;
    int objectX;
    int objectY;
    char direction;
    JButton startButton;
    JCheckBox checkBox;
    JTextField nameField;
    JButton enterNameButton;
    boolean running = false;
    boolean played = false;
    boolean assist = false;
    String username;
    Timer timer;
    Random random;

    //Constructor
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.setLayout(new FlowLayout(FlowLayout.CENTER,SCREEN_WIDTH/10,275));
        name();
    }
    //Creating Name Method
    private void name(){
        nameField = new JTextField(15);
        
        JLabel nameLabel = new JLabel("Enter your Name: ");
        nameLabel.setForeground(Color.red);
        
        enterNameButton = new JButton("Next");
        enterNameButton.setForeground(Color.red);
        enterNameButton.setBackground(Color.black);
        enterNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                username = nameField.getText();
                if (!username.isEmpty()){
                    nameField.setVisible(played);
                    enterNameButton.setVisible(played);
                    nameLabel.setVisible(played);
                    repaint();
                    createStartButton();
                    assistiveMode();
                }
            }
        });
        
        this.add(nameLabel);
        this.add(nameField);
        this.add(enterNameButton);
    }
    //Checkbox for Assistive Mode Method
    private void assistiveMode(){
        checkBox = new JCheckBox();
        checkBox.setText("Assistive Mode");
        checkBox.setFocusable(false);
        checkBox.setFont(new Font("Consolas",Font.PLAIN,20));
        checkBox.setForeground(Color.red);
        checkBox.setBackground(Color.black);
        checkBox.setVisible(!running);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==checkBox){
                    if(checkBox.isSelected()){assist = true;}
                    else{assist = false;}
                }
            }
        });
        this.add(checkBox);
        
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
        //Adding the Start button to this pannel
        this.add(startButton); 
    }
    //Start Game Method
    public void startGame(){
        bodyParts = 6;
        objectsEaten = 0;
        direction = 'R';

        // Initialize snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        //Start of the game
        newObject();
        running = true;
        startButton.setVisible(!running); // Hide the "START" Button
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
        if (!running && !played){displayText(g,"Welcome ");}
        if(running){
            checkBox.setVisible(!running);
            if(assist){ gridLines(g);}//Shows Grid Lines
            //Object figures on the Screen
            g.setColor(Color.red);
            g.fillOval(objectX, objectY, UNIT_SIZE, UNIT_SIZE);
            
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
            g.drawString("Score: "+objectsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+objectsEaten))/2, g.getFont().getSize());
    }
    //New Object Method
    public void newObject(){
        //Creating random co-ordinates for the position of the Object
        objectX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        objectY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    //Move Method   
    public void move(){
        for(int i = bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //Controls of the Game
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
    //Check Object Method
    public void checkObject(){
        if((x[0]==objectX) && (y[0]==objectY)){
            bodyParts++;
            objectsEaten++;
            newObject();
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
        if(textToDisplay.startsWith("Welcome ") && username !=null){
            // If the text is "Welcome! " + username, display it differently
        g.drawString("Welcome " + username + "!", (SCREEN_WIDTH - metrics.stringWidth("Welcome " + username + "!")) / 2, SCREEN_HEIGHT / 3);
        }
        else {
            // Otherwise, display the text as usual
            g.drawString(textToDisplay, (SCREEN_WIDTH - metrics.stringWidth(textToDisplay)) / 2, SCREEN_HEIGHT / 3);
        }
    }
    //Game Over Method
    public void gameOver(Graphics g){
        //Showing the score after game
        showScore(g);
        //Game-Over text
        displayText(g,"Game Over!");
        startButton.setText("Re-START");
        startButton.setVisible(!running);
        checkBox.setVisible(!running);
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        //Checks if the game is running
        if(running){
            move();
            checkObject();
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
