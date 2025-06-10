public class NumberLock {
    GameManager game_manager;
    String user_input;
    String lock_code = "0000";

    public NumberLock(GameManager game_manager) {
        this.game_manager = game_manager;
    }

    public void askPuzzle() {
        boolean correct = false;

        while (!correct) {
            game_manager.ui.messageText.setText("Enter the 4-digit code:");
            game_manager.ui.resetInputBox();

            String enteredTxt = game_manager.ui.inputTxt;
            while (enteredTxt == "") {
                System.out.println("");
                enteredTxt = game_manager.ui.inputTxt;
            }
            user_input = enteredTxt;

            if (validateInput(user_input)) {
                game_manager.ui.messageText.setText("Congrats you completed the game!\n");
                game_manager.delay(2);
                game_manager.ui.messageText.setText("I am sadden to not keep you forever..");
                correct = true;
            } else {
                game_manager.ui.messageText.setText("Wrong!!!");
                game_manager.ui.resetInputBox();
            }
        }
    }

    public boolean validateInput(String comparing_answers) {
        return comparing_answers.equals(lock_code);
    }
}
