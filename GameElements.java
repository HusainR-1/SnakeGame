import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GameElements implements ActionListener{

    static int DELAY = 75; // Delay in the movement of the snake (Default Medium Mode)
    JButton startButton;
    JButton nextButton;
    JCheckBox assistCheckBox;
    JRadioButton easyButton;
    JRadioButton mediumButton;
    JRadioButton hardButton;
    JPanel difficultyPanel;
    JPanel namePanel;
    JPanel startContainer;
    JTextField nameField;
    JLabel nameLabel;
    JButton showDifficultyButton;
    JPanel viewDifficultyPanel;

    public void difficultyMode() {

        easyButton = new JRadioButton("Easy");
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");

        mediumButton.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);

        GameDesign.standardType(group);

        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 100;}});
        
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 75;}});

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DELAY = 50;}});

        difficultyPanel = new JPanel(new GridLayout(1,3));
        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        difficultyPanel.setVisible(false);

        GameDesign.standardType(difficultyPanel);

    }

    public JPanel createNamePanel(){

        nameField = new JTextField(15); //Takes in the UserName
        nameLabel = new JLabel("Enter your Name: "); //Prompt Label 
        nextButton = new JButton("Next");

        nextButton.setBorderPainted(false);

        namePanel = new JPanel();
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.add(nextButton);
        
        GameDesign.standardType(nameLabel);
        GameDesign.standardType(nextButton);
        GameDesign.standardType(namePanel);
        
        return (namePanel);
    }

    public void createStartButton(){

        startButton = new JButton("START GAME");
        startButton.setBorderPainted(false);

        GameDesign.standardType(startButton,30);   
    }

    public JPanel createAssistiveMode(){

        assistCheckBox = new JCheckBox("Assistive Mode");
        assistCheckBox.setFocusable(false);

        GameDesign.standardType(assistCheckBox,15);

        createStartButton();
        
        startContainer = new JPanel(new GridLayout(1,2));
        startContainer.add(startButton);
        startContainer.add(assistCheckBox);

        return startContainer;        
    }

    public JPanel makeDifficultyPanel(){
        showDifficultyButton = new JButton("Difficulty Level");
        showDifficultyButton.setBorderPainted(false);
        showDifficultyButton.setFocusable(false);

        GameDesign.standardType(showDifficultyButton);

        difficultyMode();

        viewDifficultyPanel = new JPanel(new GridLayout(2,1));
        viewDifficultyPanel.add(showDifficultyButton);
        viewDifficultyPanel.add(difficultyPanel);

        GameDesign.standardType(viewDifficultyPanel);

        return viewDifficultyPanel;
    }   


    @Override
    public void actionPerformed(ActionEvent e) {}
}