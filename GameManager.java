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
}
