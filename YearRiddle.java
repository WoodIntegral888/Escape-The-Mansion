/* Manoush and Vishwa
 * 9/6/2025
 * YearRiddle class
 */

//Imports
import java.awt.Color;
import java.util.*;

/**
 * The YearRiddle class represents a first puzzle component in the reception room
 * which will prompt the user to solve the following riddle, then ask them for the 
 * current year. This puzzle acts as a segue between the two.
 * 
 * Implements: Puzzles interface for puzzle methods that must be included
 */
public class YearRiddle implements Puzzles{
    GameManager gm; //Reference to the instance of GameManager
    String userInput; //Will later be used to store the current user input
    boolean isCorrectAnswer; //Will later be used to check if user's answer was correct 
    int attemptsCount = 0; //Tracks how many attempts the user makes

    /**
     * Constructor for YearRiddle
     * @param gm    Instance of the GameManager 
     */
    public YearRiddle(GameManager gm) {
        this.gm = gm;
        
    }

    /**
     * Prompts the player with a riddle
     * The player has a limited number of attempts to enter the correct answer 
     * This method overrides the askPuzzle in Puzzles interface
     */
    @Override
    public void askPuzzle() {
        //Displaying the first riddle and how many tries the user has remaining
        //Following riddle is inspired by user Vaiist on Reddit:
        //  https://www.reddit.com/r/riddles/comments/2o9oza/a_riddle_with_the_answer_of_365/
        gm.ui.messageText.setText("Sometimes I leap, but that's just one spec, \n"+
                            "I can be measured like eggs, or cards in a deck. What am I? \n"+
                            "You have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");
        gm.ui.resetInputBox(); //Clears previous input

        String enteredTxt = gm.ui.inputTxt;
        while (enteredTxt==""){
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userInput = enteredTxt;

        isCorrectAnswer = validateInput(userInput); //checks if the user's input is valid 
        attemptsCount++; //updating number of attempts the user makes

        //Allows up to set maxium number of attempts or until the user is correct
        while (!isCorrectAnswer && attemptsCount<Constants.MAX_ATTEMPTS){
            //To remind user and update number of attempts they have left
            gm.ui.messageText.setText("Sometimes I leap, but that's just one spec, \n"+
                            "I can be measured like eggs, or cards in a deck. What am I? \n"+
                            "You have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");

            enteredTxt = gm.ui.inputTxt;
            while (enteredTxt==""){
                System.out.println("");
                enteredTxt = gm.ui.inputTxt;
            }
            userInput = enteredTxt;
            isCorrectAnswer = validateInput(userInput);
        }

        //If game ended because they maxed out their attempts...
        if (attemptsCount>=Constants.MAX_ATTEMPTS){
            gm.ui.messageText.setText("Unfortunately, the bookkeeper has no more patience for your pathetic"+
            " attempts. You will spend the rest of your days rotting in this reception room...");
            gm.endGame(); //Informs them the game ended and ends game
        }
        else{ //Informing them they got it right
            gm.ui.messageText.setText("Good job! Beginner's luck I suppose...");
            gm.delay(4); //Delay to allow for reading the message
        }
    }

    /**
     * Checks if user's answer is the correct answer
     * This method is an example of an overriding method because it overrides the 
     * validateInput() method in the Puzzles interface
     *
     * @param input     The user's entered text
     * @return          true if the input is correct otherwise it's false
     */
    @Override
    public boolean validateInput(String input) {
        try{
            input = input.toUpperCase(); //Trying to turn the input into upper case
            // Check for different versions of the answer (which is year)
            if (input.equals("YEAR") || input.equals("YEARS") ||
                input.equals("YEAR ") || input.equals("YEARS ") ){
                gm.ui.messageText.setText("You are correct!");
                gm.ui.inputBox.setBackground(Color.GREEN);
                gm.delay(3);
                return true; //ends method if answer is correct
            }
        }
        catch(InputMismatchException e){ //Incase invalid type...
            gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT); //informs user
            gm.delay(2);
        }
        gm.ui.messageText.setText("You are incorrect!"); //informs user that they are not correct
        gm.ui.inputBox.setBackground(Color.RED); //To visually queue that they are wrong
        gm.delay(2);
        gm.ui.resetInputBox(); //To allow for next input
        return false; 
    }
    
} //Vishwa


