/* Manoush and Vishwa
 * 9/6/2025
 * UI class
 */

//Imports
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This class is responsible for housing and controlling all of the frontend/UI (Java Swing)
 * for this project.
 * It is responsible for starting up the main page, setting up (and switching between backgrounds),
 * displaying messages, and taking in user inputs.
 * 
 * A lot of this class is inspired by RyiSnow's tutorials on YouTube (To help me understand Java Swing)
 */
public class UI {
    GameManager gm; //Instantiating the GameManager object which the game is run by
    JFrame window; //Main window/base frame
    public JTextArea messageText; //Text area to display messages to user
    public JTextField inputBox; //Input box to take in messages from user
    public String inputTxt; //Used to hold message inputted by user
    public JPanel bgPanel[] = new JPanel[3]; // Used to house the two different background panels
    public JLabel bgLabel[] = new JLabel[3]; //Used to display two different background panels

    /**
     * Constructor to initialize the UI
     * Sets up the main window, scenes, and any other graphical components
     */
    public UI(GameManager gameManager) {
        this.gm = gameManager; //Refers to the singular instance of GameManager 

        //Calling all graphical functions to setup them up initially 
        createMainField(); 
        generateScreen(1);
        displayText("");
        displayInputBox();

        window.setVisible(true); //Makes window visible after components are added
    }

    /**
     * Initializes the main field
     */
    public void createMainField() {
        window = new JFrame(); //Creates the frame
        window.setSize(800, 600); //Sets size of window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes upon exit
        window.getContentPane().setBackground(Color.BLACK); //Background colour
        window.setLayout(null); //Allows for all components to be positioned manually using setBounds()
    }

    /**
     * Creates a background panel which will house/display the room image
     * @param bgNum         Index of the background panel 1 or 2
     * @param bgFileName    Name of the image file to be able to locate it
     */
    public void createBackground(int bgNum, String bgFileName) {
        bgPanel[bgNum] = new JPanel(); //Initializes panel
        bgPanel[bgNum].setBounds(50, 50, 700, 350); //allows for manual sizing and positioning
        window.add(bgPanel[bgNum]); //Adds panel to the window so it can be displayed later

        bgLabel[bgNum] = new JLabel(); //The label that will hold picture
        bgLabel[bgNum].setBounds(0, 0, 700, 350); //Setting same size and position as panel

        //Setting image as background using ImageIcon
        ImageIcon bgIcon = new ImageIcon(getClass().getClassLoader().getResource(bgFileName)); 
        bgLabel[bgNum].setIcon(bgIcon);
        bgPanel[bgNum].add(bgLabel[bgNum]); //Adds label holding image to the panel
    }

    /**
     * Generates background screens and allows for easy switching between the two
     * @param num   Index number of which screen will be displayed (which room)
     */
    public void generateScreen(int num) {
        if (num==1){
            //First room - Scene 1
            createBackground(1, "reception.png");
        }
        else{
            //Second room - Scene 1
            createBackground(2, "lounge.png");
        }
        
    }

    /**
     * Displays given messages to the player
     * @param message   The text message to show
     */
    public void displayText(String message) {
        messageText = new JTextArea(message); //Creates text area with message
        messageText.setBounds(50, 425, 700, 70); //Positions it under scene and sizes it
        messageText.setBackground(Color.BLACK); //Matches background colour
        messageText.setForeground(Color.WHITE); //Contrasting colour for text
        messageText.setEditable(false); //So that it cannot be edited
        messageText.setLineWrap(true); //Allows for wrapping
        messageText.setWrapStyleWord(true); //Breaks only at certain parts
        messageText.setFont(new Font("Book Antique", Font.PLAIN, 20)); //Styling the font
        window.add(messageText); //Adds to window to be displayed
    }

    /**
     * Displays a user input box that allows users to respond
     * When entered the input is saved to the variable inputTxt to allow for 
     * easy access in other classes
     */
    public void displayInputBox() {
        inputBox = new JTextField(10); //Creates a input box that displays 10 characters at a time
        inputBox.setBounds(50, 510, 200, 30); //Positioning it at the bottom of screen
        inputBox.setEditable(true); //Allows user to edit it
        inputBox.setBackground(Color.WHITE); //Makes text easy to see when typing
        inputBox.setToolTipText("Enter name here..."); //Help guide the user to type

        //Action for when "enter" key is hit
        inputBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputTxt = inputBox.getText(); //Stores input
                inputBox.setBackground(Color.GRAY); //To show the user that input was entered
                inputBox.setEditable(false); //Prevent them changing their answer
            }
        });

        inputBox.setFont(new Font("Book Antique", Font.PLAIN, 20)); //Maintaining font theme
        window.add(inputBox); //Adds to window to become visible later
    }

    /**
     * Allows for easy reset of box between user's answers
     * This clears and restores box to the original state
     */
    public void resetInputBox() {
        inputBox.setEditable(true); //Makes it editable again
        inputBox.setBackground(Color.WHITE); // Makes background white again
        inputBox.setText(""); //Takes out any remaining text in the box
        inputBox.setToolTipText("Enter answer here..."); //Restores guide
        inputTxt = ""; //Clears the stores text input
    }
} //Vishwa
