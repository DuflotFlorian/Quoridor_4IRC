package launcher;

import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import static launcher.LauncherGUI.defineAutoTheme;


public class NamesGUI extends JFrame {
    JFrame frameLauncher;
    JPanel JPanelButton;
    Color BackgroundColor = new Color(404040);
    int nbPlayers;
    List <String> listName;

    public NamesGUI(int nbP) {
        super();
        this.nbPlayers = nbP;
        frameLauncher = this;

        this.listName = new ArrayList<String>();
        this.listName.add("Joueur BLEU");
        this.listName.add("Joueur ROUGE");
        this.listName.add("Joueur JAUNE");
        this.listName.add("Joueur VERT");
        // set the game
        QuoridorGame quoridorGame;
        GameController quoridorGameController;
        quoridorGame = new QuoridorGame(this.nbPlayers, listName);
        quoridorGameController = new GameController(quoridorGame);

        JPanel main = new JPanel(new GridLayout(2,2));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getPreferredSize().width /2 , (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getPreferredSize().height /2 );
        this.setTitle("Nom des Joueurs | Quoridor");

        InputStream iconeURL = getClass().getResourceAsStream("/images/iconQuoridor.png");
        ImageIcon imgIcon = null;
        try {
            imgIcon = new ImageIcon(ImageIO.read(iconeURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameLauncher.setIconImage(imgIcon.getImage());
        main.setBackground(this.BackgroundColor);
        this.add(main);


        List playerNames = quoridorGameController.listPlayer();
        for (int i=0;i<playerNames.size();i++) {
            System.out.println(quoridorGameController.getPlayerName(i));
            quoridorGameController.setPlayerName(i,"a" + i);
        }

        for (int i=0;i<playerNames.size();i++) {
            System.out.println(quoridorGameController.getPlayerName(i));
        }

        this.setPreferredSize(new Dimension(500,300));
        this.pack();
        this.setVisible(true);





        //launch game windows
        JFrame quoridorFrame;
        quoridorFrame = new QuoridorGUI("Quoridor", quoridorGameController,  9);
        quoridorGame.addObserver((Observer) quoridorFrame);
        quoridorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        quoridorFrame.pack();


        quoridorFrame.setVisible(false); ///  DEBUG cache launcher


    }

    public static void main(String[] args) {
        defineAutoTheme();
    }




}
