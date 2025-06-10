/* Manoush and Vishwa
 * 9/6/2025
 * GameManager class
 */

//Imports
import java.awt.Color;

import javax.swing.JFrame;

/**
 * The GameManager class is the central controller of the game
 * It allows for management and game initializing, switching between rooms, 
 * delaying time, and ending the game
 */
public class GameManager {
    
    UI ui; //Initalizing the UI that manage the graphics in Java Swing
    Reception reception; //Initializing the Reception room
    Lounge lounge; //Initializing the Lounge room

    /**
     * Initializes a GameManager instance and queues the start of the game
     */
    public static void main (String[] args){
        GameManager gm = new GameManager(); //Creates instance of game manager
        gm.startGame(); //Calls startGame method

    }

    /**
     * Constructor for GameManager
     * Initializes the UI and the Reception room and the 'this' keyword is passed so UI 
     * and Reception can interact with instance of GameManager
     */
    public GameManager(){
        ui = new UI(this); //Instantiates UI
        reception = new Reception(this); //Instantiates reception room
        lounge = new Lounge(this); //Instantiates lounge room
    }

    /**
     * Starts the game
     * This acts as the guide from which the rooms are played
     */
    public void startGame(){
        reception.enterRoom(); //starts with reception room
        ui.generateScreen(2);
        lounge.enterRoom(); //Starts lounge when reception is done
    }

    /**
     * Allows for pausing during game execution
     * Used frequently to allow user time to read text
     * 
     * @param seconds Number of seconds to pause
     */
    public void delay(int seconds){
        try {
            Thread.sleep(seconds*1000); //Converts to milliseconds to be used correctly 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Ends the game with a visual cue
     * Disables the input box and shows "Game Over" with a red background
     */
    public void endGame(){
        ui.inputBox.setText("Game Over x_x"); //Shows game over text
        ui.inputBox.setEditable(false); //Prevents any more inputs
        ui.inputBox.setBackground(Color.RED); //Extra to show game over
        delay(7);
        ui.messageText.setText("You lost...");
        delay(2);
        ui.window.dispose(); //Closes game
    }
} //Vishwa
