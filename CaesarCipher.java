public class CaesarCipher {
    String userInput;         
    int cipherKey;  
    //this is the basic setup.          

    public CaesarCipher(int cipherKey) {
        this.cipherKey = cipherKey;
    }//this is the constructor.

    public void askPuzzle(GameManager game_manager) {
        String cipherRiddle = "prior to dycrpt"; 
        game_manager.ui.messageText.setText("Riddle:\n" + cipherRiddle + "\nUse the key from riddle to dycrpt the cipher.");
        game_manager.ui.resetInputBox();//displaying the cipher prior to dycription and asking user input.

        boolean rightanswer = false;
        while (!rightanswer) {
            game_manager.ui.resetInputBox();
            String enteredTxt = game_manager.ui.inputTxt;
            while (enteredTxt == "") {
                System.out.println("");
                enteredTxt = game_manager.ui.inputTxt;
            }
            userInput = enteredTxt;

            if (validateInput(userInput)) {
                game_manager.ui.messageText.setText("right");
                rightanswer = true;
            } else {
                game_manager.ui.messageText.setText("wrong");
                game_manager.ui.resetInputBox();
                //checking for correct input
            }
        }
    }

    public boolean validateInput(String input) {
        String correct_decrypt = decrypt("prior to dycrpt", 4);
        return input.equalsIgnoreCase(correct_decrypt); 
    } // <- added this missing closing bracket

    public String decrypt(String message, int answer_key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char m = message.charAt(i);
            if (Character.isLetter(m)) {
                char upperCase = Character.isUpperCase(m) ? 'A' : 'a';
                m = (char) ((m - upperCase - answer_key + 26) % 26 + upperCase);
            }
            result.append(m); //this is our program running its own caesar cipher.
        
        }
        return result.toString();
    }
//AI recomended to do the part below.
    public String decrypt(int flipped_key, String flipped_message) {
        return decrypt(flipped_message, flipped_key);
    }
}
