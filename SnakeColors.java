import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class SnakeColors implements ActionListener {
    private static Random random = new Random();
    JRadioButton rainbowButton;
    JRadioButton standardButton;
    JRadioButton blackwhiteButton;
    JRadioButton blueButton;
    JPanel colorPanel;
    public String snakeColor = "green";
    //For Random Colors (Rainbow Effect)
    public static void setRainbowColor(Graphics g){
        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
    }

    //Black and White Maestro
    public static void setBlackWhite(Graphics g){
        int randomColor = random.nextInt(255);
        g.setColor(new Color(randomColor,randomColor,randomColor));
    }

    //Standard Green Color
    public static void setStandard(Graphics g){
        g.setColor(new Color(45,180,0));
    }

    //Blue Color
    public static void setBlue(Graphics g){
        g.setColor(new Color(0,45,180));
    }

    //Making a Color Container
    public JPanel colorContainer(){
        rainbowButton = new JRadioButton("Rainbow");
        standardButton = new JRadioButton("Green");
        standardButton.setSelected(true);
        blackwhiteButton = new JRadioButton("Black & White");
        blueButton = new JRadioButton("Blue");
        
        ButtonGroup group = new ButtonGroup();
        group.add(rainbowButton);
        group.add(standardButton);
        group.add(blackwhiteButton);
        group.add(blueButton);

        GameDesign.standardType(group);

        rainbowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "rainbow";System.out.println(snakeColor);}});
        standardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "green";System.out.println(snakeColor);}});
        blackwhiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "B&W";System.out.println(snakeColor);}});
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {snakeColor = "blue";System.out.println(snakeColor);}});
        
        colorPanel = new JPanel(new GridLayout(4,1));
        colorPanel.add(rainbowButton);
        colorPanel.add(standardButton);
        colorPanel.add(blackwhiteButton);
        colorPanel.add(blueButton);

        return colorPanel;
    }

    public void setRadioButtonsVisible(boolean visible) {
        colorPanel.setVisible(visible);
    }

    //Checks Color Method
    public  void checkColor(Graphics g){
        if(snakeColor == "rainbow"){setRainbowColor(g);}
        else if (snakeColor == "B&W"){setBlackWhite(g);}
        else if (snakeColor == "blue"){setBlue(g);}
        else {setStandard(g);}
    }
    @Override
    public void actionPerformed(ActionEvent e) {}   
}

