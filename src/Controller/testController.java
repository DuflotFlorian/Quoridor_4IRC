package Controller;
import Class.*;

public class testController {

    public static void main(String[] args){
        Game myGame = new Game(2);
        //myGame.isThereAPath();

    }
    public static void test_movement(){

        Game myGame = new Game(2);
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(2,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(16,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(4,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(14,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(6,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(12,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(8,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(10,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(10,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(8,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(12,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(6,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(14,8));
        myGame.move(myGame.getCurrentPlayer(), new Coordinates(4,6));

        myGame.move(myGame.getCurrentPlayer(), new Coordinates(16,8));
        System.out.println(myGame.toString());
    }

}
