import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class GameDesign {

    JLabel textLabel;
    int big;

    public static void standardType(JComponent component){
        component.setForeground(Color.red);
        component.setBackground(Color.black);
        component.setFont(new Font("Consolas",Font.BOLD,20));
    }

    public static void standardType(AbstractButton button,int size){
        button.setForeground(Color.red);
        button.setBackground(Color.black);
        button.setFont(new Font("Consolas",Font.BOLD,size));
    }

    public static void standardType(ButtonGroup group){
         Enumeration<AbstractButton> buttons = group.getElements();
        while (buttons.hasMoreElements()) {
            standardType(buttons.nextElement());
        }
    }

    public static void standardType(Graphics g){
        if(!GamePanel.end){g.setColor(Color.green);}
        else{g.setColor(Color.red);}
        g.setFont(new Font("Consolas",Font.BOLD,20)); 
    }

    public int SIZE(String textToDisplay){
        double x = 60;
        for(int i = 1; i <= textToDisplay.length(); i+=5){
            x-=10;
        }
        big = (int)(textToDisplay.length()+x);
        return big;
    }

    public  JLabel displayText(String textToDisplay){
        textLabel = new JLabel();
        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.red);
        textLabel.setFont(new Font("Ink Free",Font.BOLD,GamePanel.SCREEN_WIDTH/textToDisplay.length()));
        textLabel.setText(textToDisplay);// Display the text as usual
        return textLabel;
    }

    public static void gridLines(Graphics g){
        //Drawing Grid Lines 
        for (int i=0;i<GamePanel.SCREEN_HEIGHT/GamePanel.UNIT_SIZE;i++){
            //X-Axis
            g.drawLine(i*GamePanel.UNIT_SIZE, 0, i*GamePanel.UNIT_SIZE, GamePanel.SCREEN_HEIGHT);
            //Y-Axis
            g.drawLine(0,i*GamePanel.UNIT_SIZE,GamePanel.SCREEN_WIDTH,i*GamePanel.UNIT_SIZE );
        }
    }    

}
