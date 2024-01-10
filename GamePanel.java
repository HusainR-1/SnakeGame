import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
    public static final int SCREEN_WIDTH = 600; // Width of the panel
    public static final int SCREEN_HEIGHT = 600; // Height of the panel    
    static final int UNIT_SIZE = 25; // Each unit size
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // Total no. of units in the game
    int DELAY = 75; // Delay in the movement of the snake
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
    JLabel textLabel;
    JPanel startContainer;
    JPanel detailsContainer;
    JPanel radioPanel;
    JButton enterNameButton;
    JRadioButton easyButton;
    JRadioButton mediumButton;
    JRadioButton hardButton;
    boolean running = false;
    boolean played = false;
    boolean assist = false;
    String username;
    Timer timer;
    Random random;

    //Constructor
    GamePanel(){
        random = new Random();
        //this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());    
        //this.setLayout(new GridLayout(10,2));
        this.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
        name();
    }
    //Creating Name Method
    private void name(){
        displayText("Welcome ");
        nameField = new JTextField(15);
        
        JLabel nameLabel = new JLabel("Enter your Name: ");
        nameLabel.setForeground(Color.red);
        nameLabel.setFont(new Font("Consolas",Font.BOLD,20));
        enterNameButton = new JButton("Next");
        enterNameButton.setForeground(Color.red);
        enterNameButton.setBackground(Color.black);
        enterNameButton.setBorderPainted(false);
        enterNameButton.setFont(new Font("Consolas",Font.BOLD,20));
        enterNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                username = nameField.getText();
                if (!username.isEmpty()){
                    textLabel.setVisible(false);
                    detailsContainer.setVisible(false);
                    repaint();
                    displayText("Welcome ");
                    createStartButton();
                    assistiveMode();
                    difficultyMode();
                }
            }
        });
        detailsContainer = new JPanel();
        detailsContainer.setForeground(Color.red);
        detailsContainer.setBackground(Color.black);
        detailsContainer.add(nameLabel);
        detailsContainer.add(nameField);
        detailsContainer.add(enterNameButton);
        this.add(detailsContainer);
    }
    //Checkbox for Assistive Mode Method
    private void assistiveMode(){
        checkBox = new JCheckBox();
        checkBox.setText("Assistive Mode");
        checkBox.setFocusable(false);
        checkBox.setFont(new Font("Consolas",Font.PLAIN,20));
        checkBox.setForeground(Color.red);
        checkBox.setBackground(Color.black);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(e.getSource()==checkBox){
                    if(checkBox.isSelected()){assist = true;}
                    else{assist = false;}
                }
            }
        });
        startContainer = new JPanel(new GridLayout(1,2));
        startContainer.add(startButton);
        startContainer.add(checkBox);
        this.add(startContainer);        
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
    }
    // Difficulty Mode Method
    private void difficultyMode() {
        easyButton = new JRadioButton("Easy");
        easyButton.setForeground(Color.red);
        easyButton.setBackground(Color.black);
        mediumButton = new JRadioButton("Medium");
        mediumButton.setForeground(Color.red);
        mediumButton.setBackground(Color.black);
        mediumButton.setSelected(true);
        hardButton = new JRadioButton("Hard");
        hardButton.setForeground(Color.red);
        hardButton.setBackground(Color.black);

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 100;
                System.out.println("You selected Easy difficulty!");
            }
        });
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 75;
                System.out.println("You selected Medium difficulty!");
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DELAY = 50;
                System.out.println("You selected Hard difficulty!");
            }
        });
        radioPanel = new JPanel(new GridLayout(1,3));
        radioPanel.add(easyButton);
        radioPanel.add(mediumButton);
        radioPanel.add(hardButton);
        this.add(radioPanel);
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
        startContainer.setVisible(!running); // Hide the "START" Button
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
        if(running){
            startContainer.setVisible(!running);
            textLabel.setVisible(!running);
            radioPanel.setVisible(!running);
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
        else if(played) {
            //textLabel.setVisible(false);
            gameOver(g);
            if(!running){
                textLabel.setText("Game Over!");
                textLabel.setVisible(true);
            }    
        }     
    }
    //Show Score Method
    public void showScore(Graphics g){
        g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,20));
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
    public void displayText(String textToDisplay){
        textLabel = new JLabel();
        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.red);
        textLabel.setFont(new Font("Ink Free",Font.BOLD,SCREEN_WIDTH/textToDisplay.length()));
        this.add(textLabel);
        // If the text is "Welcome! " + username, display it differently
        if(textToDisplay.startsWith("Welcome ") && username !=null){
            textToDisplay+=username+"!";
        }
        textLabel.setText(textToDisplay);// Otherwise, display the text as usual
    }
    //Game Over Method
    public void gameOver(Graphics g){
        //Showing the score after game
        showScore(g);
        //Game-Over text
        startButton.setText("Re-START");
        startContainer.setVisible(!running);
        radioPanel.setVisible(!running);
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
