import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Item {

    int x;
    int y;
    Random random = new Random();

    public void New(){
        x = random.nextInt((int)(GamePanel.SCREEN_HEIGHT/GamePanel.UNIT_SIZE))*GamePanel.UNIT_SIZE;
        y = random.nextInt((int)(GamePanel.SCREEN_WIDTH/GamePanel.UNIT_SIZE))*GamePanel.UNIT_SIZE;
    }

    public void Design(Graphics g,int UNIT_SIZE){
        g.setColor(Color.red);
        g.fillOval(x,y, UNIT_SIZE, UNIT_SIZE);
    }

    //Check Item Method
    public void Check(){
        if((GamePanel.x[0]==x) && (GamePanel.y[0]==y)){
            GamePanel.bodyParts++;
            GamePanel.itemsEaten++;
            New();
        }
    }
}

