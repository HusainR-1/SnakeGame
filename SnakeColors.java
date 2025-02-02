import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class SnakeColors implements ActionListener {
    private static Random random = new Random();
    JButton showColorButton;
    JRadioButton rainbowButton;
    JRadioButton greenButton;
    JRadioButton blackwhiteButton;
    JRadioButton blueButton;
    JPanel colorPanel;
    JPanel viewColorPanel;
    public String snakeColor = "green";
    //Random Colors (Rainbow Effect)
    public static void setRainbowColor(Graphics g){
        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
    }

    //Black and White Maestro
    public static void setBlackWhite(Graphics g){
        int randomColor = random.nextInt(255);
        g.setColor(new Color(randomColor,randomColor,randomColor));
    }

    //Green Color
    public static void setGreen(Graphics g){
        g.setColor(new Color(45,180,0));
    }

    //Blue Color
    public static void setBlue(Graphics g){
        g.setColor(new Color(0,45,180));
    }

    //Making a Color Container
    public void colorContainer(){

        rainbowButton = new JRadioButton("Rainbow");
        greenButton = new JRadioButton("Green");
        blackwhiteButton = new JRadioButton("Black & White");
        blueButton = new JRadioButton("Blue");

        rainbowButton.setFocusable(false);
        greenButton.setFocusable(false);
        blackwhiteButton.setFocusable(false);
        blueButton.setFocusable(false);

        greenButton.setSelected(true);
        
        ButtonGroup group = new ButtonGroup();
        group.add(rainbowButton);
        group.add(greenButton);
        group.add(blackwhiteButton);
        group.add(blueButton);

        GameDesign.standardType(group);

        rainbowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "rainbow";}});
        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "green";}});
        blackwhiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "B&W";}});
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "blue";}});
        
        colorPanel = new JPanel(new GridLayout(2,2));
        colorPanel.add(rainbowButton);
        colorPanel.add(greenButton);
        colorPanel.add(blueButton);
        colorPanel.add(blackwhiteButton);

        colorPanel.setVisible(false);

    }

    //Checks Color Method
    public  void checkBodyColor(Graphics g){
        if(snakeColor == "rainbow"){setRainbowColor(g);}
        else if (snakeColor == "B&W"){setBlackWhite(g);}
        else if (snakeColor == "blue"){setBlue(g);}
        else {setGreen(g);}
    }

    public void checkHeadColor(Graphics g){
        if(snakeColor == "blue" ){g.setColor(Color.blue);}
        else if(snakeColor == "B&W"){g.setColor(Color.white);}
        else{g.setColor(Color.green);}
    }

    public JPanel makeColorPanel(){
        showColorButton = new JButton("COLORS");
        showColorButton.setBorderPainted(false);
        showColorButton.setFocusable(false);
        

        GameDesign.standardType(showColorButton);

        colorContainer();

        viewColorPanel = new JPanel(new GridLayout(2,1));
        viewColorPanel.add(showColorButton);
        viewColorPanel.add(colorPanel);

        GameDesign.standardType(viewColorPanel);

        return viewColorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {}   
}

