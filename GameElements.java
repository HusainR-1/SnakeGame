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
    JButton NextButton;
    JCheckBox checkBox;
    JRadioButton easyButton;
    JRadioButton mediumButton;
    JRadioButton hardButton;
    JPanel radioContainer;
    JPanel detailsContainer;
    JPanel startContainer;
    JTextField nameField;
    JLabel nameLabel;

    public JPanel difficultyMode() {

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

        radioContainer = new JPanel(new GridLayout(3,1));
        radioContainer.add(easyButton);
        radioContainer.add(mediumButton);
        radioContainer.add(hardButton);

        return radioContainer;
    }

    public JPanel createName(){

        nameField = new JTextField(15); //Takes in the UserName
        nameLabel = new JLabel("Enter your Name: "); //Prompt Label 
        NextButton = new JButton("Next");

        NextButton.setBorderPainted(false);

        detailsContainer = new JPanel();
        detailsContainer.add(nameLabel);
        detailsContainer.add(nameField);
        detailsContainer.add(NextButton);
        
        GameDesign.standardType(nameLabel);
        GameDesign.standardType(NextButton);
        GameDesign.standardType(detailsContainer);
        
        return (detailsContainer);
    }

    public void createStartButton(){

        startButton = new JButton("START GAME");
        startButton.setBorderPainted(false);

        GameDesign.standardType(startButton,25);   
    }

    public JPanel createAssistiveMode(){

        checkBox = new JCheckBox("Assistive Mode");
        checkBox.setFocusable(false);

        GameDesign.standardType(checkBox);
        
        startContainer = new JPanel(new GridLayout(2,1));
        startContainer.add(startButton);
        startContainer.add(checkBox);

        return startContainer;        
    }


    @Override
    public void actionPerformed(ActionEvent e) {}
}