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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600; // Width of the panel
    static final int SCREEN_HEIGHT = 600; // Height of the panel    
    static final int UNIT_SIZE = 25; // Each unit size
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // Total no. of units in the game
    static int DELAY = 75; // Delay in the movement of the snake (Default Medium Mode)
    final static int x[] = new int[GAME_UNITS];
    final static int y[] = new int[GAME_UNITS];
    static int bodyParts;
    static int itemsEaten;
    static String username;
    char direction;
    JCheckBox checkBox;
    JTextField nameField;
    JButton startButton;
    JButton NextButton;
    JRadioButton easyButton;
    JRadioButton mediumButton;
    JRadioButton hardButton;
    JPanel startContainer;
    JPanel detailsContainer;
    JPanel radioContainer;
    boolean running;
    boolean played;
    boolean assist;
    Item item;
    GameDesign gameDesign;
    SnakeColors flair;
    Timer timer;
    
    //Constructor
    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());    
        this.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
        SnakeColorsObject();
        ItemObject();
        GameDesignObject();
        name();
    }
    // SnakeColors Object
    public void SnakeColorsObject(){
        this.setVisible(true);
        flair = new SnakeColors();
        this.add(flair.colorContainer()); 
    }
    // Item Object
    public void ItemObject(){
        item = new Item();
    }
    // GameDesign Object
    public void GameDesignObject(){
        gameDesign = new GameDesign();
    }

    //Creating Name Method
    private void name(){
        this.add(gameDesign.displayText("Welcome ")); //Displays Welcome
        nameField = new JTextField(15); //Takes in the UserName
        
        JLabel nameLabel = new JLabel("Enter your Name: "); //Prompt Label 
        NextButton = new JButton("Next");
        GameDesign.standardType(nameLabel);
        GameDesign.standardType(NextButton);
        NextButton.setBorderPainted(false);
        NextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){ 
                username = nameField.getText();
                if (!username.isEmpty()){
                    detailsContainer.setVisible(false);
                    flair.setRadioButtonsVisible(false);
                    gameDesign.textLabel.setText("Welcome "+username+"!"); // Renames with the UserName
                    createStartButton();
                }
            }
        });
        detailsContainer = new JPanel();
        detailsContainer.setForeground(Color.red);
        detailsContainer.setBackground(Color.black);
        detailsContainer.add(nameLabel);
        detailsContainer.add(nameField);
        detailsContainer.add(NextButton);
        this.add(detailsContainer);
    }
    //Create "START" button
    private void createStartButton(){
        startButton = new JButton();
        startButton.setText("START GAME");
        GameDesign.standardType(startButton,25);
        startButton.setBorderPainted(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
            }
        });
        assistiveMode();
        difficultyMode();
    }
    //Checkbox for Assistive Mode Method
    private void assistiveMode(){
        checkBox = new JCheckBox();
        checkBox.setText("Assistive Mode");
        checkBox.setFocusable(false);
        GameDesign.standardType(checkBox);
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(checkBox.isSelected()){assist = true;}
                else{assist = false;}
            }});
        startContainer = new JPanel(new GridLayout(2,1));
        startContainer.add(startButton);
        startContainer.add(checkBox);
        this.add(startContainer);        
    }
    // Difficulty Mode Method
    private void difficultyMode() {
        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium");
        mediumButton.setForeground(Color.red);
        mediumButton.setSelected(true);
        hardButton = new JRadioButton("Hard");

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        GameDesign.standardType(group);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 100;}});
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 75;}});

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 50;}});

        radioContainer = new JPanel(new GridLayout(3,1));
        radioContainer.add(easyButton);
        radioContainer.add(mediumButton);
        radioContainer.add(hardButton);
        this.add(radioContainer);
    }
    //Start Game Method
    public void startGame(){
        bodyParts = 6;
        itemsEaten = 0;
        direction = 'R';

        // Initialize snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        //Start of the game
        item.New(); // Creates new item
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
        for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            //X-Axis
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            //Y-Axis
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE );
        }
    }
    //Draw Method
    public void draw(Graphics g){
        if(running){
            if(assist){gridLines(g);} // Show Grid Lines
            startContainer.setVisible(!running);
            gameDesign.textLabel.setVisible(!running);
            radioContainer.setVisible(!running);
            item.Design(g, UNIT_SIZE);
            for(int i = 0; i < bodyParts; i++){
                if(i==0){
                    flair.checkHeadColor(g);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    flair.checkColor(g);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } 
        else if(played) {
            gameOver(g);
            if(!running){
                gameDesign.textLabel.setText("Game Over!");
                gameDesign.textLabel.setVisible(true);
            }    
        }     
        if(played || running){showScore(g);} 
    }
    //Show Score Method
    public void showScore(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Consolas",Font.BOLD,20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+itemsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+itemsEaten))/2, g.getFont().getSize());
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
    //Check Collisions Method
    public void checkCollisions(){
        for(int i = bodyParts;i>0;i--){
            //Checks if the head colided with the body
            if((x[0] == x[i]) && (y[0] == y[i]) ){
                running = false;
                played = true;
            }
            // Checks if head touches left border || right border || top border || bottom border
            if(x[0] < 0 || x[0] > SCREEN_WIDTH ||  y[0] < 0 || y[0] > SCREEN_HEIGHT){
                running = false;
                played = true;
            }
        }
        //Stop the Timer , if game it's not running
        if(!running){
            timer.stop();
        }
    }
    //Game Over Method
    public void gameOver(Graphics g){
        //Game-Over text
        startButton.setText("Re-START");
        startContainer.setVisible(!running);
        radioContainer.setVisible(!running);
    }   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            item.Check();
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
// GamePanel Design Completed }{Made by Husain Rupawalla}
//Took around 7 days to complete from scratch {Signing off on 10th Jan 2024}