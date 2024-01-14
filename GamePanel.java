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
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
    static final int SCREEN_WIDTH = 600; // Width of the panel
    static final int SCREEN_HEIGHT = 600; // Height of the panel    
    static final int UNIT_SIZE = 25; // Each unit size
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // Total no. of units in the game
    final static int x[] = new int[GAME_UNITS];
    final static int y[] = new int[GAME_UNITS];
    static int bodyParts;
    static String username;
    boolean running;
    boolean played;
    boolean assist;
    char direction;
    GameDesign gameDesign;
    GameElements gameElements;
    Item item;
    SnakeColors snakeColors;
    Timer timer;
    
    //Constructor
    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());    
        this.setLayout(new FlowLayout(FlowLayout.CENTER,100,100));
        GameInitializer();
    }
    // SnakeColors Object
    public void SnakeColorsObject(){
        snakeColors = new SnakeColors();
        this.add(snakeColors.colorContainer()); 
    }
    // Item Object
    public void ItemObject(){
        item = new Item();
    }
    // GameDesign Object
    public void GameDesignObject(){
        gameDesign = new GameDesign();
    }
    // GameElements Object
    public void GameElements(){
        gameElements = new GameElements();
        this.add(gameDesign.displayText("Welcome ")); //Displays Welcome
        this.add(gameElements.createNamePanel());
    }

    //Initializes all the object
    public void GameInitializer(){
        GameDesignObject();
        GameElements();
        clickNext();
        ItemObject();
        SnakeColorsObject();
    }

    //Creating Name Method
    private void clickNext(){
        gameElements.nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){ 
                username = gameElements.nameField.getText();
                if (!username.isEmpty()){
                    gameElements.namePanel.setVisible(false);
                    snakeColors.colorPanel.setVisible(false);
                    gameDesign.textLabel.setText("Welcome "+username+"!"); // Renames with the UserName
                    gameDesign.textLabel.setForeground(Color.pink);
                    gameDesign.textLabel.setFont(new Font("Arial",Font.BOLD,gameDesign.SIZE("Welcome "+username+"!")));
                    gameElements.createStartButton();
                    clickStart();
                }
            }
        });
    }
    
    private void clickStart(){
        this.add(gameElements.difficultyMode());
        gameElements.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
            }
        });
        this.add(gameElements.createAssistiveMode());
        clickAssist();
    }

    private void clickAssist(){
        gameElements.assistCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(gameElements.assistCheckBox.isSelected()){assist = true;}
                else{assist = false;}
            }});      
    }

    public void startGame(){

        bodyParts = 6;
        Item.Eaten = 0;
        direction = 'R';

        // Initialize snake position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }

        //Start of the game
        item.New(); // Creates new item
        running = true;
        gameElements.startContainer.setVisible(!running); // Hide the "START" Button
        timer = new Timer(GameElements.DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g){
        if(running){
            if(assist){GameDesign.gridLines(g);} // Show Grid Lines
            gameDesign.textLabel.setVisible(!running);
            gameElements.startContainer.setVisible(!running);
            gameElements.radioContainer.setVisible(!running);
            item.Design(g, UNIT_SIZE);
            for(int i = 0; i < bodyParts; i++){
                if(i==0){
                    snakeColors.checkHeadColor(g);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    snakeColors.checkBodyColor(g);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        } 
        else if(played) {
            gameOver();
            if(!running){
                gameDesign.textLabel.setText("Game Over!");
                gameDesign.textLabel.setFont(new Font("Consolas",Font.BOLD,SCREEN_WIDTH/10));
                gameDesign.textLabel.setVisible(true);
            }    
        }     
        if(played || running){showScore(g);} 
    }

    public void showScore(Graphics g){

        GameDesign.standardType(g);
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+ Item.Eaten, (GamePanel.SCREEN_WIDTH - metrics.stringWidth("Score: "+Item.Eaten))/2, g.getFont().getSize());
        
    }

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

    public void checkCollisions(){
        for(int i = bodyParts;i>0;i--){
            //Checks if the head colided with the body
            if((x[0] == x[i]) && (y[0] == y[i]) ){
                running = false;
                played = true;
            }
        }

        // Checks if head touches left border || right border || top border || bottom border
        if(x[0] < 0 || x[0] > SCREEN_WIDTH ||  y[0] < 0 || y[0] > SCREEN_HEIGHT){
            running = false;
            played = true;
        }

        //Stop the Timer , if game is not running
        if(!running){timer.stop();}
    }

    public void gameOver(){
        gameElements.startButton.setText("Re-START");
        gameElements.startContainer.setVisible(!running);
        gameElements.radioContainer.setVisible(!running);
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