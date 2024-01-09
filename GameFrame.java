import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game"); // Title of the frame
        this.setVisible(true);  // Makes the frame visible
        this.setResizable(false); // Disallows frame re-sizing 
        this.pack(); // Re-sizes the frame to fit its preferred sized based on the components
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
