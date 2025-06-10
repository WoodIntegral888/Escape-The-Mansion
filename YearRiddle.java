import java.awt.Color;
import java.util.*;

public class YearRiddle implements Puzzles{
    GameManager gm;
    String userInput;
    boolean isCorrectAnswer;
    int attemptsCount = 0;

    public YearRiddle(GameManager gm) {
        this.gm = gm;
        
    }

    @Override
    public void askPuzzle() {
        //Following riddle is inspired by user Vaiist on Reddit:
        //  https://www.reddit.com/r/riddles/comments/2o9oza/a_riddle_with_the_answer_of_365/
        gm.ui.messageText.setText("Sometimes I leap, but that's just one spec, \n"+
                            "I can be measured like eggs, or cards in a deck. What am I? \n"+
                            "You have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");
        gm.ui.resetInputBox();
        String enteredTxt = gm.ui.inputTxt;
        while (enteredTxt==""){
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userInput = enteredTxt;
        isCorrectAnswer = validateInput(userInput);
        attemptsCount++;

        while (!isCorrectAnswer && attemptsCount<Constants.MAX_ATTEMPTS){

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

        if (attemptsCount>=Constants.MAX_ATTEMPTS){
            gm.ui.messageText.setText("Unfortunately, the bookkeeper has no more patience for your pathetic"+
            " attempts. You will spend the rest of your days rotting in this reception room...");
            gm.endGame();
        }
        else{
            gm.ui.messageText.setText("Good job! Beginner's luck I suppose...");
            gm.delay(4);
        }
    }

    @Override
    public boolean validateInput(String input) {
        try{
            input = input.toUpperCase();
            if (input.equals("YEAR") || input.equals("YEARS") ||
                input.equals("YEAR ") || input.equals("YEARS ") ){
                gm.ui.messageText.setText("You are correct!");
                gm.ui.inputBox.setBackground(Color.GREEN);
                gm.delay(3);
                return true;
            }
        }
        catch(InputMismatchException e){
            gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT);
            gm.ui.messageText.setText("Change");
            gm.delay(2);
        }
        gm.ui.messageText.setText("You are incorrect!");
        gm.ui.inputBox.setBackground(Color.RED);
        gm.delay(2);
        gm.ui.resetInputBox();
        return false;
    }
    
}


