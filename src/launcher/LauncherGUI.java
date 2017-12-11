package launcher;

import java.awt.*;

import java.util.ArrayList;
import javax.swing.*;

import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;
import Class.*;

public class LauncherGUI {

    public static void main(String[] args) {
        JFrame frame;
        Dimension dim;
        QuoridorGame quoridorGame;
        GameController quoridorGameController;

        quoridorGame = new QuoridorGame();
        quoridorGameController = new GameController(quoridorGame);

        frame = new QuoridorGUI("Quoridor", quoridorGameController,  9);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}
}
