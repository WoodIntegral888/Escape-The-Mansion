import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Reception extends Room {
    GameManager gm;
    YearRiddle yearRiddle;
    NamePadlock namePadlock;

    private int[][] clientYears = makeRandomListYears();
    private String[][] clientNames = { { "", "Teresa", "Beatrice", "Darcie", "Emilia" },
            { "Cora", "Kye", "Cleo", "Bethan", "Robbie" },
            { "Fraces", "Sofia", "Violet", "Annie", "Terry" },
            { "Amira", "Claudia", "Edie", "Joanne", "Heather" },
            { "Lisa", "Ana", "Erica", "Bethany", "Alana" } };
    
    public String userName;

    private String userYear;

    public static String targetName;

    public Reception(GameManager gm) {
        this.gm = gm;
        this.yearRiddle = new YearRiddle(gm);
        this.namePadlock = new NamePadlock(gm);
    }

    @Override
    public void enterRoom() {
        makeArrayIntoFile("clientYearsOG", clientYears);
        showInstructions();
        String enteredTxt = gm.ui.inputTxt;
        while (enteredTxt==null){
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userName = enteredTxt;
        clientNames[0][0] = userName;
        gm.ui.messageText.setText("Hello, "+userName+"! Welcome to 'Escape the Mansion'! "+
                                    "Let's begin with our first riddle... ");
        String[][] sortedNames = insertionSort(clientNames);
        gm.delay(8);

        yearRiddle.askPuzzle();
        gm.ui.resetInputBox();

        gm.ui.messageText.setText("I need to record a date for your visit...Remind me, what year is it?");
        enteredTxt = gm.ui.inputTxt;
        while (enteredTxt=="" || enteredTxt==null){
            System.out.println("");
            enteredTxt = gm.ui.inputTxt;
        }
        userYear = enteredTxt;
        handleYearInputError(userYear);
        int[][] sortedYears = insertionSort(clientYears);
        gm.delay(3);

        gm.ui.messageText.setText("Onto the next riddle... You will need to solve this to "+
                                    "move on from the reception room.");
        gm.delay(6);
        namePadlock.askPuzzle();
        
        gm.ui.messageText.setText("The bookkeeper will look for the index of that year, "+
        "then provide you with the name of the same index in the alphabetically sorted list of clients.");
        gm.delay(10);
        int yearIDX = findYear(sortedYears, 1969);

        int targetRow = (int) yearIDX/5;
        int targetColumn = yearIDX%5;

        targetName = sortedNames[targetRow][targetColumn];
        gm.ui.messageText.setText("...");
        gm.delay(2);
        gm.ui.messageText.setText("Aha! The name you are looking for is "+ targetName);
        gm.delay(3);

        namePadlock.unlockPadlock();





    }

    @Override
    public void showInstructions() {
        gm.ui.messageText.setText("Welcome, dear guest... To whom do I owe the pleasure?");
    }

    public void makeArrayIntoFile(String fileName, int[][] arr2D) {

        try {
            FileWriter yearsFile = new FileWriter(fileName);
            for (int[] row : arr2D) {
                for (int num : row) {
                    yearsFile.write(String.format("%5d", num));
                }
                yearsFile.write(System.lineSeparator());
            }
            yearsFile.close();
        } catch (IOException e) {
            System.out.println("File (" + fileName + ") not found.");
        }
    }

    public int[][] makeRandomListYears() {
        int[][] yearsArr = new int[5][5];
        ArrayList<Integer> listNums = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int randNum = (int) (Math.random() * (2025 - 1000 + 1)) + 1000;
                while (listNums.contains(randNum) || randNum == 1969) {
                    randNum = (int) (Math.random() * (2025 - 1000 + 1)) + 1000;
                }
                if (i == 0 && j == 0) {
                    yearsArr[i][j] = 0;
                } 
                else if (i==0 && j==1) {
                    yearsArr[i][j] = 1969;
                }
                else {
                    yearsArr[i][j] = randNum;
                    listNums.add(randNum);
                }
            }
        }

        return yearsArr;
    }

    public String[][] insertionSort(String[][] arr2D) {
        String[] arr = new String[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[(i * 5) + j] = arr2D[i][j];
            }
        }

        for (int i = 1; i < 25; i++) {
            String key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j].compareTo(key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = key;
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr2D[i][j] = arr[(i * 5) + j];
                System.out.println(arr[(i * 5) + j]);
            }
        }

        return arr2D;
    }

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

    public void handleYearInputError(String input){
        int num=0;
        while (num == 0){
            try {
                num = Integer.parseInt(input);
                if (num==0){
                    gm.ui.messageText.setText("Surely this isn't the beginning of time...");
                    gm.delay(3);
                }
                else{
                    gm.ui.messageText.setText("Ahh, yes... "+num);
                    clientYears[0][0] = num;
                    return;
                }
            } catch (NumberFormatException e) {
                gm.ui.messageText.setText(Constants.ERROR_INVALID_INPUT);
            }
            gm.ui.resetInputBox();
            num=0;
            gm.delay(2);

            gm.ui.messageText.setText("Try again... What year is it?");
            String enteredTxt = gm.ui.inputTxt;
            while (enteredTxt==null || enteredTxt==""){
                System.out.println("");
                enteredTxt = gm.ui.inputTxt;
            }
            input = enteredTxt;
        }
        
    }

    public int findYear(int[][] years, int target){
        int[] arr = new int[25];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                arr[(i * 5) + j] = years[i][j];
            }
        }
        int low = 0; //initializing low as index 0
        int high = 24; //initializing high as the last index

        while (low <= high) { //Helps indicate when list has finished being searched
            //Setting the mid value as the middle search range
            int mid = (low + high) / 2; 
            int midE = arr[mid]; //Getting the word at the middle index

            if (midE==target) { //If 0, the word being searched has been found
                return mid; //returns the index of the word being searched
            } else if (midE < target) { //If <0, the word being searched for
                //  is further along than the current middle word
                // Switching searching range to right half
                low = mid + 1;
            } else { //If midE>target, the word being searched for is a
                //  earlier than the current middle word
                // Switching searching range to left half
                high = mid - 1;
            }
        }
        return -1;  // If the word is not found, returns -1
    }

}
