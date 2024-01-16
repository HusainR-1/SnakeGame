import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    static boolean end;
    boolean running;
    boolean played;
    boolean assist;
    char direction;
    JPanel subPanel;
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
        this.setLayout(new FlowLayout(FlowLayout.CENTER,200,150));
        GameInitializer();
    }
    //Initializes all the object
    public void GameInitializer(){
        GameDesignObject();
        GameElements();
        SnakeColorsObject();
        clickNext();
    }
    // GameDesign Object
    public void GameDesignObject(){
        gameDesign = new GameDesign();
    }
    // GameElements Object
    public void GameElements(){
        gameElements = new GameElements();
    }
    private void clickNext(){
        this.add(gameDesign.displayText("Welcome ")); //Displays Welcome
        this.add(gameElements.createNamePanel());
        gameElements.nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){ 
                username = gameElements.nameField.getText();
                if (!username.isEmpty()){
                    username = "Welcome "+username+"!";
                    clickStart();
                    
                    gameElements.namePanel.setVisible(false);
                    gameDesign.textLabel.setText(username); // Renames with the UserName
                    gameDesign.textLabel.setFont(new Font("Ink Free",Font.BOLD,gameDesign.SIZE(username)));
                }
            }
        });
    }
    private void clickStart(){
        this.setLayout(new FlowLayout(FlowLayout.CENTER,90,60));
        this.add(MakeSubPanel());
        clickAssist();
        showDifficultyPanel();
        showColorPanel();
        gameElements.startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                gameDesign.textLabel.setVisible(false);
                subPanel.setVisible(false);
                elementsVisibility(false);
                end = false; 
                startGame();
            }
        });
    }
    private void clickAssist(){
        gameElements.assistCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(gameElements.assistCheckBox.isSelected()){assist = true;}
                else{assist = false;}
            }});      
        }
    // SnakeColors Object
    public void SnakeColorsObject(){
        snakeColors = new SnakeColors();
        
    }
    public JPanel MakeSubPanel(){
        subPanel = new JPanel(new GridLayout(3,1));
        subPanel.add(gameElements.createAssistiveMode());
        subPanel.add(snakeColors.makeColorPanel());
        subPanel.add(gameElements.makeDifficultyPanel());

        GameDesign.standardType(subPanel);

        return subPanel;
    }
    public void showColorPanel(){
        snakeColors.showColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (snakeColors.colorPanel.isVisible()){
                    snakeColors.colorPanel.setVisible(false);}
                    else{snakeColors.colorPanel.setVisible(true);}
                }});
                ItemObject();
            }
            
    public void showDifficultyPanel(){
        gameElements.showDifficultyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (gameElements.difficultyPanel.isVisible()){
                gameElements.difficultyPanel.setVisible(false);}
                else{gameElements.difficultyPanel.setVisible(true);}
            }
        });
    }
    // Item Object
    public void ItemObject(){
        item = new Item();
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
        timer = new Timer(GameElements.DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void elementsVisibility(boolean visibility){
        gameElements.difficultyPanel.setVisible(visibility);
        snakeColors.colorPanel.setVisible(visibility);
    }

    
    public void draw(Graphics g){
        if(running){
            if(assist){GameDesign.gridLines(g);} // Show Grid Lines
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
            gameOver(); // Makes the options visible again
            gameDesign.textLabel.setText("Game Over!");
            gameDesign.textLabel.setFont(new Font("Ink Free",Font.BOLD,SCREEN_WIDTH/10));
            gameDesign.textLabel.setVisible(true);
        }     
        if(played || running){showScore(g);} 
    }

    public void showScore(Graphics g){
        GameDesign.standardType(g);
        g.drawString("Score: "+ Item.Eaten,UNIT_SIZE - 16,UNIT_SIZE);
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
        if (!end){
            gameElements.startButton.setText("Re-START");
            subPanel.setVisible(!running);
            elementsVisibility(false);
            end = true;
        }
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
//Worked on it for more 6 days to increase efficiency {Signing off again on 16th Jan 2024}