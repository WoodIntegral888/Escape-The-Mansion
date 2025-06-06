import java.awt.*;

import javax.swing.*;

public class UI{
    GameManager gameManager;

    JFrame window;
    public JTextArea messageText;
    public JPanel bgPanel[] = new JPanel[4]; 
    public JLabel bgLabel[] = new JLabel[4];


    public UI(GameManager gameManager){
        this.gameManager = gameManager;

        createMainField();
        displayText("testing displayText");
        generateScreen();

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

    public void createObject(int bgNum, int objx, int objy, int objWidth, int objHeight, String objFileName){
        JLabel objLabel = new JLabel();
        objLabel.setBounds(objx, objy, objWidth, objHeight);

        ImageIcon objIcon = new ImageIcon(getClass().getClassLoader().getResource(objFileName));
        objLabel.setIcon(objIcon);

        bgPanel[1].add(objLabel);
        bgPanel[1].add(bgLabel[1]);
    }

    public void generateScreen(){
        //First room - Scene 1
        
        createBackground(1, "temp.png");
        createObject(1, 440, 140, 200, 200, "temp2.png");
    }

    public void displayText(String message){
        messageText = new JTextArea(message);
        messageText.setBounds(50, 425, 700, 150);
        messageText.setBackground(Color.BLACK);
        messageText.setForeground(Color.WHITE);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Book Antique", Font.PLAIN, 20));
        window.add(messageText);
    }
}
