import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;

public class GameDesign {

    public static void standardType(AbstractButton button){
        button.setForeground(Color.red);
        button.setBackground(Color.black);
        button.setFont(new Font("Consolas",Font.BOLD,20));
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
    
    public static void standardType(JLabel label){
        label.setForeground(Color.red);
        label.setBackground(Color.black);
        label.setFont(new Font("Consolas",Font.BOLD,20));
    }
}
