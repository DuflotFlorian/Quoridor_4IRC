package launcher;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;

public class LauncherGUI extends javax.swing.JFrame {

    JFrame frameLauncher;
    JPanel JPanelButton;

    //Bouttons
    JButton button2Joueurs ;
    JButton button4Joueurs ;
    JButton buttonScores ;
    JButton buttonQuitter;

    public LauncherGUI() {
        super();
        /*On cré un référence vers la frame*/
        frameLauncher = this;

        /*JPanel principal*/
        JPanel main = new JPanel(new BorderLayout());

        /*JPanel qui contient les boutons en bas de la frame*/
        JPanelButton = new JPanel(new FlowLayout());

        //Bouttons
        button2Joueurs = new JButton("2 Joueurs");
        button4Joueurs = new JButton("4 Joueurs");
        buttonScores = new JButton("Scores");
        buttonQuitter = new JButton("Quitter");


        //frame = new QuoridorGUI("Quoridor", 9);
        //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setResizable(false);
        //frame.pack();
        //frame.setVisible(true);

        /*Settings JFrame*/
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(700, 350));
        this.setLocationRelativeTo(null);
        //Dimension dimLauncher = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setLocation(5,);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getPreferredSize().width /2 , (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getPreferredSize().height /2 );

        //this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - this.getSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - this.getSize().height / 2);
        this.setTitle("Quoridor");



        /*Ajout des Bouttonsau panel*/
        JPanelButton.add(button2Joueurs);
        JPanelButton.add(button4Joueurs);
        JPanelButton.add(buttonScores);
        JPanelButton.add(buttonQuitter);

        /*chargement de l'image*/
        java.net.URL imageURL = LauncherGUI.class.getResource("images" + File.separator + "imgLauncher.jpg");
        JLabel imgMain = new JLabel(new ImageIcon(imageURL));

        /*Ajout de l'image*/
        main.add(imgMain, BorderLayout.CENTER);
        /*Ajout des bouttons à la frame*/
        main.add(JPanelButton, BorderLayout.PAGE_END);
        this.add(main);

        /*Reglages des boutons*/
        button2Joueurs.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {

                //JDialog2Players dialog2 = new JDialog2Players(frameLauncher);
                //dialog2.setVisible(true);
                //méthode appelée quand action sur le bouton
                QuoridorGUI mainFrame = new QuoridorGUI("Quoridor",9);
                mainFrame.pack();
                mainFrame.setVisible(true);

                //
                frameLauncher.setVisible(false);

            }
        });
        button4Joueurs.setEnabled(false);
        buttonScores.setEnabled(false);
        buttonQuitter.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {

        /*Définition auto du theme en fonction du système*/
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LauncherGUI().setVisible(true);
            }
        });


    }


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
