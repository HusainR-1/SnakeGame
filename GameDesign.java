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
        g.setColor(Color.red);
        g.setFont(new Font("Consolas",Font.BOLD,20)); 
    }

    public  JLabel displayText(String textToDisplay){
        textLabel = new JLabel();
        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.red);
        textLabel.setFont(new Font("Ink Free",Font.BOLD,(int)(GamePanel.SCREEN_WIDTH/textToDisplay.length()*0.6)));
        textLabel.setText(textToDisplay);// Display the text as usual
        return textLabel;
    }

}
