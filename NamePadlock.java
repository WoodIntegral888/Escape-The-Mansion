/* Manoush and Vishwa
 * 9/6/2025
 * NamePadlock class
 */

//Imports
import java.awt.Color;
import java.util.*;

/**
 * The NamePadlock class represents the second puzzle of the reception room
 * where the player must guess a specific year (1969) to answer a riddle 
 * and then input a name letter-by-letter to unlock the final padlock to escape the room 
 *
 * Implements: Puzzle interface
 */
public class NamePadlock implements Puzzles{
    GameManager gm; //Setting up instance of GameManager
    String userInput; //Will store user input
    boolean isCorrectAnswer; //Will track to see if answer is correct
    int attemptsCount = 0; //Tracks number of attempts user is making

    /**
     * Constructor for NamePadlock and initializes the GameManager reference
     * @param gm    GameManager object to access game methods and alter UI
     */
    public NamePadlock(GameManager gm) {
        this.gm = gm;
        
    }

    /**
     * Asks the player to solve a riddle by guessing the correct year (1969)
     * The player has a limited number of attempts to enter the correct answer 
     * This method overrides the askPuzzle in Puzzles interface
     */
    @Override
    public void askPuzzle() {
        //Prompting the user with the first riddle and informing them about the number of attempts they ahve
        // The following riddle was inspired by ChatGPT
        gm.ui.messageText.setText("I marked a giant leap, not just for one,\nWhen man first walked where there is no sun. "+
            "What year am I? \nYou have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");
        gm.ui.resetInputBox(); //Resetting input box for answers

        //Asking user for input
        String enteredTxt = gm.ui.inputTxt;
        while (enteredTxt==""){
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userInput = enteredTxt;

        isCorrectAnswer = validateInput(userInput); //Checking to see if their answer was correct
        attemptsCount++; //Updating number of attempts

        while (!isCorrectAnswer && attemptsCount<Constants.MAX_ATTEMPTS){ //While they are incorrect or under the valid number of attempts...
            //Reminding them of the riddle and informing them of how many attempts they have left
            gm.ui.messageText.setText("I marked a giant leap, not just for one,\nWhen man first walked where there is no sun. "+
            "What year am I? \nYou have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");

            enteredTxt = gm.ui.inputTxt;
            while (enteredTxt==""){
                System.out.println("");
                enteredTxt = gm.ui.inputTxt;
            }
            userInput = enteredTxt;
            isCorrectAnswer = validateInput(userInput); //Once again checking for correct answer
            attemptsCount++;
        }

        //If they maxed out their available number of attempts...
        if (attemptsCount>Constants.MAX_ATTEMPTS){
            gm.ui.messageText.setText("Unfortunately, the bookkeeper has no more patience for your pathetic"+
            " attempts. You will spend the rest of your days rotting in this reception room...");
            gm.endGame(); //Ends game and informed them that they have lost
        }
        else{
            //Informs them that they are doing good (exceeding expectations)
            gm.ui.messageText.setText("It seems I have underestimated you...");
            gm.delay(2);
        }
    }

    /**
     * Checks if user's answer is the correct answer (if year is 1969)
     * This method is an example of an overriding method because it overrides the 
     * validateInput() method in the Puzzles interface
     *
     * @param input     The user's entered text
     * @return          true if the input is correct otherwise it's false
     */
    @Override
    public boolean validateInput(String input) {
        int num; //sets up "num" to hold parsed int from input
        try {
            num = Integer.parseInt(input); //Attempting to parsing int from input
            if (num==1969){ //if the user has guessed correct number...
                gm.ui.messageText.setText("Yes! The year of the Apollo 11 moon landing. Well done!");
                gm.ui.inputBox.setBackground(Color.GREEN); //Informing the user they are right!
                gm.delay(2);
                return true; //ending check for valid input
            }
        } catch (NumberFormatException e) {
            gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT); //Incase they inputted an invalid type
        }
        gm.ui.messageText.setText("You are incorrect!"); //Informing them they are wrong
        gm.ui.inputBox.setBackground(Color.RED);
        gm.delay(2);
        gm.ui.resetInputBox(); //Resetting input box for future inputs
        return false;
    }

    /**
     * Second part of the puzzle which prompts the player to enter a name one letter at a 
     * time to match the target name.
     * If the full name is entered correctly, the padlock is "unlocked" and the user may
     * move on to the next room
     */
    public void unlockPadlock(){
        //Informing the user of the instructions
        gm.ui.messageText.setText("Please enter each letter of the name one by one. Hopefully you remember the name...");
        String nameFormed =""; //Setting up a String that'll hold each individual letter together as a word
        String targetName = Reception.targetName; //Sets target as the indexed name found in the reception class
        targetName = targetName.toUpperCase(); //Setting it to upper for efficient and accurate comparisons

        gm.delay(2);
        while (!nameFormed.equals(targetName)){ //Loop that will continuously ask them for the name (they have unlimited attempts)
            for (int i = 0; i < targetName.length(); i++) { //Ask for n letters, where n is the length of the target word
                gm.ui.resetInputBox(); //Resetting box for next input
                //Taking in next input
                String enteredTxt = gm.ui.inputTxt;
                while (enteredTxt==""){
                    System.out.println("");
                    enteredTxt = gm.ui.inputTxt;
                }
                userInput = enteredTxt;

                while (!isChar(userInput)){//While the user enters non-letters 
                    gm.ui.messageText.setText(nameFormed+"\nPlease enter a single letter...");
                    gm.ui.resetInputBox();
                    enteredTxt = gm.ui.inputTxt;
                    while (enteredTxt==""){
                        System.out.println("");
                        enteredTxt = gm.ui.inputTxt;
                    }
                    userInput = enteredTxt;
                }
                nameFormed+=userInput.charAt(0); //only takes the first letter they input (incase they input more)
                gm.ui.messageText.setText(nameFormed); //Displays it so that they are aware of the name they have formed thus far
            
            }
            nameFormed = nameFormed.toUpperCase(); //Sets name to upper case for comparision (reminder target is all upper case)
            if (!nameFormed.equals(targetName)){ //Informs them if they have guessed incorrectly
                gm.ui.messageText.setText("Incorrect guess! Try again...");
                gm.ui.inputBox.setBackground(Color.RED);
                gm.delay(2);
                gm.ui.resetInputBox();
                nameFormed="";
                gm.ui.messageText.setText("");
            }
        }
        //Informing them if they finally got it right and can escape to the next room
        gm.ui.messageText.setText("You got it! You have escaped the reception room.");
        gm.ui.inputBox.setBackground(Color.GREEN);
        gm.delay(4);
    }

    /**
     * Ensures that the input is a letter
     */
    public boolean isChar(String input){ 
        char ch = input.charAt(0);
        if (Character.isLetter(ch)){
            return true;
        }
        return false;
    }
} //Vishwa