/* Manoush and Vishwa
 * 9/6/2025
 * Puzzles interface
 */

 /**
 * The Puzzles interface defines the contract for all puzzle types
 * within the escape room game. Any class that implements this interface must
 * contain the methods askPuzzle() and validateInput(String input)
 */
public interface Puzzles {

    /**
     * This method will begin the puzzle by prompting the user with 
     * the riddle for the respective puzzle. It will then do the necessary 
     * comparisons and tasks for the puzzle
     */
    public void askPuzzle();

    /**
     * This method will ensure that the user has inputted the correct answer
     * to determine if they may proceed onto the next puzzle or not
     */
    public boolean validateInput(String input);
} //Vishwa
