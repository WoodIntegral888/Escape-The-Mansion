public class Lounge extends Room {
    GameManager game_manager;
    RecursiveRiddle recursiveRiddlePuzzle1;
    CaesarCipher caesarCipherPuzzle2;
    NumberLock numLockPuzzleFinal;
    String end_word;

    public Lounge(GameManager game_manager) {
        this.game_manager = game_manager;
        this.recursiveRiddlePuzzle1 = new RecursiveRiddle(game_manager);
        this.caesarCipherPuzzle2 = new CaesarCipher(4);
        this.numLockPuzzleFinal = new NumberLock(game_manager);
    }

    @Override
    public void enterRoom() {
        showInstructions();

        game_manager.ui.messageText.setText("start message");
        game_manager.delay(2);

        end_word = recursiveRiddlePuzzle1.check_recursion(0, "");

        if (end_word.equals("FOUR")) {
            game_manager.ui.messageText.setText("correct enter note");
            game_manager.delay(2);
            caesarCipherPuzzle2.askPuzzle(game_manager);

            game_manager.delay(2);
            numLockPuzzleFinal.askPuzzle();
        }
    }

    @Override
    public void showInstructions() {
        game_manager.ui.messageText.setText("escpe the room");
    }
}
