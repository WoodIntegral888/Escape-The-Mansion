import java.awt.Color;
import java.util.*;

public class NamePadlock implements Puzzles{
    GameManager gm;
    String userInput;
    boolean isCorrectAnswer;
    int attemptsCount = 0;

    public NamePadlock(GameManager gm) {
        this.gm = gm;
        
    }

    @Override
    public void askPuzzle() {
        // The following puzzle was taken from chatGPT
        gm.ui.messageText.setText("I marked a giant leap, not just for one,\nWhen man first walked where there is no sun. "+
            "What year am I? \nYou have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");
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

            gm.ui.messageText.setText("I marked a giant leap, not just for one,\nWhen man first walked where there is no sun. "+
            "What year am I? \nYou have "+(Constants.MAX_ATTEMPTS-attemptsCount)+" chances to answer...");

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
            gm.ui.messageText.setText("It seems I have underestimated you...");
            gm.delay(2);
        }
    }

    @Override
    public boolean validateInput(String input) {
        int num;
        try {
            num = Integer.parseInt(input);
            if (num==1969){
                gm.ui.messageText.setText("Yes! The year of the Apollo 11 moon landing. Well done!");
                gm.ui.inputBox.setBackground(Color.GREEN);
                gm.delay(2);
                return true;
            }
        } catch (NumberFormatException e) {
            gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT);
        }
        gm.ui.messageText.setText("You are incorrect!");
        gm.ui.inputBox.setBackground(Color.RED);
        gm.delay(2);
        gm.ui.resetInputBox();
        return false;
    }

    public void unlockPadlock(){
        gm.ui.messageText.setText("Please enter each letter of the name one by one. Hopefully you remember the name...");
        String nameFormed =""; 
        String targetName = Reception.targetName;
        targetName = targetName.toUpperCase();

        gm.delay(2);
        while (!nameFormed.equals(targetName)){
            for (int i = 0; i < targetName.length(); i++) {
                gm.ui.resetInputBox();
                String enteredTxt = gm.ui.inputTxt;
                while (enteredTxt==""){
                    System.out.println("");
                    enteredTxt = gm.ui.inputTxt;
                }
                userInput = enteredTxt;
                while (!isChar(userInput)){
                    gm.ui.messageText.setText(nameFormed+"\nPlease enter a single letter...");
                    gm.ui.resetInputBox();
                    enteredTxt = gm.ui.inputTxt;
                    while (enteredTxt==""){
                        System.out.println("");
                        enteredTxt = gm.ui.inputTxt;
                    }
                    userInput = enteredTxt;
                }
                nameFormed+=userInput.charAt(0);
                gm.ui.messageText.setText(nameFormed);
            
            }
            nameFormed = nameFormed.toUpperCase();
            if (!nameFormed.equals(targetName)){
                gm.ui.messageText.setText("Incorrect guess! Try again...");
                gm.ui.inputBox.setBackground(Color.RED);
                gm.delay(2);
                gm.ui.resetInputBox();
                nameFormed="";
                gm.ui.messageText.setText("");
            }
        }
        gm.ui.messageText.setText("You got it! You have escaped the reception room.");
        gm.ui.inputBox.setBackground(Color.GREEN);
        gm.delay(4);

        


    }

    public boolean isChar(String input){
        char ch = input.charAt(0);
        if (Character.isLetter(ch)){
            return true;
        }
        return false;
    }
}