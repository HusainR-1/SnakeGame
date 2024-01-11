import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
public class SnakeColors {
    private static Random random = new Random();
    
    public static void setRainbowColor(Graphics g){
        //For Random Colors (Rainbow Effect)
        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
    }

    public static void setBlackWhite(Graphics g){
        //Black and White Maestro
        int randomColor = random.nextInt(255);
        g.setColor(new Color(randomColor,randomColor,randomColor));
    }

    public static void setStandard(Graphics g){
        //Standard Green Color
        g.setColor(new Color(45,180,0));
    }

    public static void setBlue(Graphics g){
        //Blue Color
        g.setColor(new Color(0,45,180));
    }
}
