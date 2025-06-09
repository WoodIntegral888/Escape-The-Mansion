import java.awt.Color;

public class GameManager {

    UI ui;
    Reception reception;

    public static void main (String[] args){
        GameManager gm = new GameManager();
        gm.startGame();

    }

    public GameManager(){
        ui = new UI(this);
        reception = new Reception(this);
    }

    public void startGame(){
        reception.enterRoom();
    }

    public void delay(int seconds){
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void endGame(){
        ui.inputBox.setText("Game Over x_x");
        ui.inputBox.setEditable(false);
        ui.inputBox.setBackground(Color.RED);
    }
}
