package launcher;

import java.awt.Dimension;

import java.util.ArrayList;
import javax.swing.JFrame;
import vue.QuoridorGUI;
import Class.*;



/**
 * @author francoise.perrin
 * Lance l'exécution d'un jeu d'échec en mode graphique.
 * La vue (ChessGameGUI) observe le modèle (ChessGame)
 * les échanges passent par le contrôleur (ChessGameControlers)
 * 
 */
public class LauncherGUI {

    public static void main(String[] args) {

        JFrame frame;
        Dimension dim;
        int coeffTaille = 100;
        frame = new QuoridorGUI("Quoridor", coeffTaille ,9);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600, 10);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        /*
         ChessGame chessGame;
         ChessGameControlers chessGameControler;
         JFrame frame;
         Dimension dim;

		dim = new Dimension(700, 700);
		
		chessGame = new ChessGame();	
		chessGameControler = new ChessGameControler(chessGame);
		
		frame = new ChessGameGUI("Jeu d'échec", chessGameControler,  dim);
		chessGame.addObserver((Observer) frame);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(600, 10);
		frame.setResizable(true);
		frame.pack();
		frame.setVisible(true);
                    
                */
	}
}
