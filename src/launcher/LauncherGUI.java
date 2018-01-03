package launcher;

import java.util.Observer;
import javax.swing.*;

import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;

public class LauncherGUI {

    public static void main(String[] args) {
        JFrame frame;
        QuoridorGame quoridorGame;
        GameController quoridorGameController;

        quoridorGame = new QuoridorGame();
        quoridorGameController = new GameController(quoridorGame);

        frame = new QuoridorGUI("Quoridor", quoridorGameController,  9);
        quoridorGame.addObserver((Observer) frame);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}
}
