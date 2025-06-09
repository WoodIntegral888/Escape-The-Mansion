import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UI{
    GameManager gm;

    JFrame window;
    public JTextArea messageText;
    public JTextField inputBox;
    public String inputTxt;
    public JPanel bgPanel[] = new JPanel[4]; 
    public JLabel bgLabel[] = new JLabel[4];


    public UI(GameManager gameManager){
        this.gm = gameManager;

        createMainField();
        generateScreen();
        displayText("testing displayText");
        displayInputBox();

        window.setVisible(true);
    }

    public void createMainField(){
        window = new JFrame();
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.BLACK);
        window.setLayout(null);
    }
  
    public void createBackground(int bgNum, String bgFileName){
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(50,50,700,350);
        bgPanel[bgNum].setBackground(Color.BLUE);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);

        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0, 0, 700, 350);

        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource(bgFileName));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void generateScreen(){
        //First room - Scene 1
        createBackground(1, "reception.jpg");
    }

    public void displayText(String message){
        messageText = new JTextArea(message);
        messageText.setBounds(50, 425, 700, 70);
        messageText.setBackground(Color.BLACK);
        messageText.setForeground(Color.WHITE);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antique", Font.PLAIN, 20));
        window.add(messageText);
    }

    public void displayInputBox(){
        inputBox = new JTextField(10);
        inputBox.setBounds(50, 510, 200, 30);
        inputBox.setEditable(true);
        inputBox.setBackground(Color.WHITE);
        inputBox.setToolTipText("Enter name here...");

        inputBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                inputTxt = inputBox.getText();
                System.out.println(inputTxt);
                inputBox.setBackground(Color.GRAY);
                inputBox.setEditable(false);
            }
        });

        inputBox.setFont(new Font("Book Antique", Font.PLAIN, 20));
        window.add(inputBox);
        
    }

    public void resetInputBox(){
        inputBox.setEditable(true);
        inputBox.setBackground(Color.WHITE);
        inputBox.setText("");
        inputBox.setToolTipText("Enter answer here...");
        inputTxt = "";
    }
}
