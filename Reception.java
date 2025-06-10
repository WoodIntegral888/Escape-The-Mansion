/* Manoush and Vishwa
 * 9/6/2025
 * Reception class
 */

//Imports
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Reception class represents the first room in the game
 * It extends the Room class and contains game logic for name input, year puzzles,
 * name padlock puzzles, and contains the searching and sorting algorithms for 
 * this project
 */
public class Reception extends Room {
    GameManager gm; //Reference to the game manager
    YearRiddle yearRiddle; //Component for the year riddle puzzle
    NamePadlock namePadlock; //Component for the name padlock puzzle

    private int[][] clientYears = makeRandomListYears(); //Randomly generates a 5x5 grid to hold all the years

    //List of all clients set in a 2D array (first one is blank for the user's name)
    private String[][] clientNames = { { "", "Teresa", "Beatrice", "Darcie", "Emilia" },
            { "Cora", "Kye", "Cleo", "Bethan", "Robbie" },
            { "Fraces", "Sofia", "Violet", "Annie", "Terry" },
            { "Amira", "Claudia", "Edie", "Joanne", "Heather" },
            { "Lisa", "Ana", "Erica", "Bethany", "Alana" } };

    public String userName; //Will later store the user's name

    private String userYear; //Will later store the user's entered year

    public static String targetName; //Will later hold the final target name to escape reception 

    /**
     * Constructor for GameManager
     * Initializes the puzzle components and the Reception room and the 'this' keyword so it can 
     * interact with instance of GameManager
     */
    public Reception(GameManager gm) {
        this.gm = gm;
        this.yearRiddle = new YearRiddle(gm);
        this.namePadlock = new NamePadlock(gm);
    }

    /**
     * The entry point to the Reception room (a method inherited from Rooms class)
     * It asks user for their name, displays welcome message, starts off year puzzle, then 
     * name padlock puzzle, and finds target name by matching year index to name index
     * in the sorted list
     */
    @Override
    public void enterRoom() {
        makeArrayIntoFile("clientYearsOG", clientYears); // Stores inital randomized list of years into a file
        //in case they are later altered 

        showInstructions(); //Calls Instructions and begins discussion with user
        //Starting off by asking user for their name
        String enteredTxt = gm.ui.inputTxt; //Takes in whatever is in the textbox currently
        while (enteredTxt == null) { //Most likely it is null but this will repeat until the user
            System.out.println(""); //enters something (almost like a buffer)
            enteredTxt = gm.ui.inputTxt;
        }
        userName = enteredTxt; //reads input and stores it as the user's name
        clientNames[0][0] = userName; //adds users name to the first of the client names 2D array

        gm.ui.messageText.setText("Hello, " + userName + "! Welcome to 'Escape the Mansion'! " +
                "Let's begin with our first riddle... "); //Welcome message for user to see
        String[][] sortedNames = insertionSort(clientNames); //Sorts list of names including user's name
        gm.delay(8); //delays 8 seconds to allow user to read

        yearRiddle.askPuzzle(); //Starts first puzzle, yearRiddle
        gm.ui.resetInputBox(); //Resets input for next puzzle

        //Taking in user's entered year to add it to the year list
        gm.ui.messageText.setText("I need to record a date for your visit...Remind me, what year is it?");
        enteredTxt = gm.ui.inputTxt;
        while (enteredTxt == "" || enteredTxt == null) {
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userYear = enteredTxt;

        handleYearInputError(userYear); //Ensures they have entered a valid year and adds to list of years
        int[][] sortedYears = insertionSort(clientYears); //Sorts the randomized list of years
        gm.delay(3); 

        gm.ui.messageText.setText("Onto the next riddle... You will need to solve this to " +
                "move on from the reception room."); //Lets user know that we are moving on
        gm.delay(6);
        namePadlock.askPuzzle(); //begins the name padlock riddle (first part of it's puzzle)

        //After successfully passing that, they will get a hint for the next part of the puzzle
        gm.ui.messageText.setText("The bookkeeper will look for the index of that year, " +
                "then provide you with the name of the same index in the alphabetically sorted list of clients.");
        gm.delay(10); 
        int yearIDX = findYear(sortedYears, 1969); //Finds the index of the year 1969 (our target year)

        int targetRow = (int) yearIDX / 5; //finds the what the i value or row number the target name would be on
        int targetColumn = yearIDX % 5; //finds what the j value or column number the target name would be on

        targetName = sortedNames[targetRow][targetColumn]; //finds and stores target name
        gm.ui.messageText.setText("..."); //to show user that the bookkeeper is searching for the name
        gm.delay(2);
        gm.ui.messageText.setText("Aha! The name you are looking for is " + targetName); //Informs user of target name
        gm.delay(3);

        namePadlock.unlockPadlock(); //Final part of the name padlock before escaping room
        return; //Ends the reception room/exits it
    }

    /**
     * Simply welcomes user and begins to ask them for their name
     * This method overrides the method from the parent class, Room
     */
    @Override
    public void showInstructions() {
        gm.ui.messageText.setText("Welcome, dear guest... To whom do I owe the pleasure?");
    }

    /**
     * Writes a 2D integer array to a file with specific formatting
     * @param fileName  name of the file to write
     * @param arr2D     the 2D array to save (note that this is an int array)
     */
    public void makeArrayIntoFile(String fileName, int[][] arr2D) {
        try {
            FileWriter yearsFile = new FileWriter(fileName); //Creates a file with the provided name
            for (int[] row : arr2D) { //for loop for each row of the 2d array
                for (int num : row) { //each value in each row
                    yearsFile.write(String.format("%5d", num)); //Adding it to the file and formatting ti
                }
                yearsFile.write(System.lineSeparator()); //moving on to next line
            }
            yearsFile.close(); //Finished editing file
        } catch (IOException e) {
            System.out.println("File (" + fileName + ") not found."); //In case file is not found
        }
    }

    /**
     * Generates a 5x5 2D array of unique random years between 1000–2025
     * Ensures 0 and 1969 are placed at [0][0] and [0][1] because the first space makes room for
     * the user year taken later and 1969 is going to be our key year from one of the puzzles
     * @return the generated 2D year grid
     */
    public int[][] makeRandomListYears() {
        int[][] yearsArr = new int[5][5];
        ArrayList<Integer> listNums = new ArrayList<>(); //to store any numbers to prevent repeats

        for (int i = 0; i < 5; i++) { //for row number
            for (int j = 0; j < 5; j++) { //for column number
                int randNum = (int) (Math.random() * (2025 - 1000 + 1)) + 1000; //generating number
                while (listNums.contains(randNum) || randNum == 1969) { //prevents repeats (including a repeat of 1969)
                    randNum = (int) (Math.random() * (2025 - 1000 + 1)) + 1000;
                }
                if (i == 0 && j == 0) { 
                    yearsArr[i][j] = 0; //setting first space as empty (temporary 0)
                } else if (i == 0 && j == 1) {
                    yearsArr[i][j] = 1969; //Setting second space as the target 1969 (this will be later reorganized and sorted)
                } else {
                    yearsArr[i][j] = randNum; //adding it to the 2D array
                    listNums.add(randNum); //preventing repeats
                }
            }
        }

        return yearsArr;
    }

    /**
     * Sorts a 2D string array alphabetically using insertion sort (quickest for lists under 30)
     * @param arr2D     2D string array to sort
     * @return          sorted 2D string array
     */
    public String[][] insertionSort(String[][] arr2D) {
        //Making the 2D array into 1D for easier sorting
        String[] arr = new String[25]; 
        for (int i = 0; i < 5; i++) { 
            for (int j = 0; j < 5; j++) {
                arr[(i * 5) + j] = arr2D[i][j];
            }
        }

        //Insertion sort section
        for (int i = 1; i < 25; i++) {
            String key = arr[i]; //Current element that will be inserted
            int j = i - 1;

            //Shifting all the elements that are greater than the current key to the right
            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key; //Setting the key into its correct position
        }

        //Turning the 1D array back into 2D
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr2D[i][j] = arr[(i * 5) + j];
                System.out.println(arr[(i * 5) + j]);
            }
        }

        return arr2D;
    }

    /**
     * Sorts a 2D integer array in ascending order using insertion sort
     * 
     * This is our example of method overloading (same method names)
     * Works the same as the other insertion sort, except slightly adjusted comparisons for 
     * @param arr2D     2D int array to sort
     * @return          sorted 2D int array
     */
    public int[][] insertionSort(int[][] arr2D) {
        int[] arr = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[(i * 5) + j] = arr2D[i][j];
            }
        }

        for (int i = 1; i < 25; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr2D[i][j] = arr[(i * 5) + j];
            }
        }

        return arr2D;
    }

    /**
     * Ensures that user enters a valid year (not a string, characters, or the year 0) and 
     * asks them again and again until they enter a valid number, upon which that year is added to 
     * it's designated spot in the list of client years
     * @param input     string input from user
     */
    public void handleYearInputError(String input) {
        int num = 0; //Will hold the parsed int from the user input
        while (num <= 0) { //Continuing loop until a valid number is entered
            try {
                num = Integer.parseInt(input); //trying to parse an int from the string input
                if (num <= 0) { //To make sure they enter a positive year (also allows loop to continue)
                    gm.ui.messageText.setText("Surely this isn't the beginning of time...");
                    gm.delay(3);
                } else {
                    gm.ui.messageText.setText("Ahh, yes... " + num); //Acknowledges valid input
                    clientYears[0][0] = num; //Adds to designated spot
                    return; //ends method
                }
            } catch (NumberFormatException e) {
                gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT); //Handles non-int input
                //Informing user that they have entered an invalid input
            }
            gm.ui.resetInputBox(); //Resets box for user input again
            num = 0;
            gm.delay(2);

            gm.ui.messageText.setText("Try again... What year is it?"); //Prompts user again
            String enteredTxt = gm.ui.inputTxt;
            while (enteredTxt == null || enteredTxt == "") {
                System.out.println("");
                enteredTxt = gm.ui.inputTxt;
            }
            input = enteredTxt;
        }

    }

    /**
     * Uses binary search to find the index of a target year in a sorted 2D array
     * @param years     sorted 2D int array to search
     * @param target    year to find
     * @return          index in flattened 1D form, or -1 if not found
     */
    public int findYear(int[][] years, int target) {
        //Turning 2D array into 1D for easier searching
        int[] arr = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[(i * 5) + j] = years[i][j];
            }
        }

        //Binary Search Section
        int low = 0; // initializing low as index 0
        int high = 24; // initializing high as the last index

        while (low <= high) { // Helps indicate when list has finished being searched
            // Setting the mid value as the middle search range
            int mid = (low + high) / 2;
            int midE = arr[mid]; // Getting the word at the middle index

            if (midE == target) { // If 0, the word being searched has been found
                return mid; // returns the index of the word being searched
            } else if (midE < target) { // If <0, the word being searched for
                // is further along than the current middle word
                // Switching searching range to right half
                low = mid + 1;
            } else { // If midE>target, the word being searched for is a
                // earlier than the current middle word
                // Switching searching range to left half
                high = mid - 1;
            }
        }
        return -1; // If the word is not found, returns -1
    }

} //Vishwa
