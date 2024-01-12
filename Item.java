import java.util.Random;

public class Item {

    int x;
    int y;
    Random random = new Random();

    Item(){
        System.out.println("Generated");
    }
    public void New(int SCREEN_HEIGHT,int UNIT_SIZE){
        x = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        y = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
}

