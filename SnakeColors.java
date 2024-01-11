import java.awt.Color;
import java.awt.Font;
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
    String snakeColor = "green";
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
    public void colorContainer(Graphics g){
        rainbowButton = new JRadioButton("Rainbow");
        rainbowButton.setForeground(Color.red);
        rainbowButton.setBackground(Color.black);
        rainbowButton.setFont(new Font("Consolas",Font.BOLD,20));
        standardButton = new JRadioButton("Green");
        standardButton.setForeground(Color.red);
        standardButton.setBackground(Color.black);
        standardButton.setFont(new Font("Consolas",Font.BOLD,20));
        standardButton.setSelected(true);
        blackwhiteButton = new JRadioButton("Black & White");
        blackwhiteButton.setForeground(Color.red);
        blackwhiteButton.setBackground(Color.black);
        blackwhiteButton.setFont(new Font("Consolas",Font.BOLD,20));
        blueButton = new JRadioButton("Blue");
        blueButton.setForeground(Color.red);
        blueButton.setBackground(Color.black);
        blueButton.setFont(new Font("Consolas",Font.BOLD,20));

        ButtonGroup group = new ButtonGroup();
        group.add(rainbowButton);
        group.add(standardButton);
        group.add(blackwhiteButton);
        group.add(blueButton);

        rainbowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {setRainbowColor(g);}});
        
        standardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {setStandard(g);}});

        blackwhiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {setBlackWhite(g);}});

        colorPanel = new JPanel(new GridLayout(4,1));
        colorPanel.add(rainbowButton);
        colorPanel.add(standardButton);
        colorPanel.add(blackwhiteButton);
        colorPanel.add(blueButton);
    }   

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rainbowButton) {
            snakeColor="rainbow";
        } else if (e.getSource() == standardButton) {
            snakeColor="green";
        } else if (e.getSource() == blackwhiteButton) {
            snakeColor="b&w";
        } else if (e.getSource() == blueButton) {
            snakeColor="blue";
        }
    }
}

