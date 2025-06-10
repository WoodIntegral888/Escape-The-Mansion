public class RecursiveRiddle {
    GameManager game_manager;         
    String userinput;   
    //basic setup             

    public RecursiveRiddle(GameManager game_manager) {
        this.game_manager = game_manager;
    }//this is the constructor

    public String check_recursion(int index, String user_correctInput) {
        String[] riddles = {
            "Riddle 1: enter f",
            "Riddle 2: enter o",
            "Riddle 3: enter u",
            "Riddle 4: enter r"
        };//indexing through the array so that as the user inputs correct answers the next riddle appears

        String[] correct_answers = { "F", "O", "U", "R" };

        if (index >= riddles.length) {
            return user_correctInput;
        }

        game_manager.ui.messageText.setText(riddles[index]);
        game_manager.ui.resetInputBox();

        String enteredTxt = game_manager.ui.inputTxt;
        while (enteredTxt == "") {
            System.out.println("");
            enteredTxt = game_manager.ui.inputTxt;
        }
        userinput = enteredTxt;

        if (userinput.equalsIgnoreCase(correct_answers[index])) {
            return check_recursion(index + 1, user_correctInput + correct_answers[index]);
            //this builds a string of the correct answers using recursion 
        } else {
            game_manager.ui.messageText.setText("try again");
            game_manager.ui.resetInputBox();
            return check_recursion(index, user_correctInput);
        }
    }
}
