import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Reception extends Room{
    GameManager gm;

    private int[][] clientYears = makeRandomListYears();
    private String[][] clientNames = {{"Dale", "Teresa", "Beatrice", "Darcie", "Emilia"},
                                        {"Cora", "Kye", "Cleo", "Bethan", "Robbie"},
                                        {"Fraces", "Sofia", "Violet", "Annie", "Terry"},
                                        {"Amira", "Claudia", "Edie", "Joanne", "Heather"},
                                        {"Lisa", "Ana", "Erica", "Bethany", "Alana"}};
    public Reception(GameManager gm){
        this.gm = gm;
    }

    public void enterRoom(){
        makeArrayIntoFile("clientYears", clientYears);
        //showInstructions();
    }

    public void showInstructions(){
        gm.ui.messageText.setText("This is my recep test...");
    }

    public static void makeArrayIntoFile(String fileName, int[][] arr2D) {

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




    public static int[][] makeRandomListYears(){
        int[][] yearsArr = new int[5][5];
        ArrayList<Integer> listNums = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int randNum = (int) Math.floor(Math.random() * 100 + 1);
                while (listNums.contains(randNum)) {
                    randNum = (int) Math.floor(Math.random() * 100 + 1);
                }
                yearsArr[i][j] = randNum;
                listNums.add(randNum);
            }
        }

        return yearsArr;
    }
}
