package launcher;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.*;
import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;


public class LauncherGUI extends javax.swing.JFrame {
    JFrame frameLauncher;
    JPanel JPanelButton;

    JButton btn2Player, btn4Player, btnScore, btnExit;

    public LauncherGUI() {
        super();
        /*On créé une référence vers la frame*/
        frameLauncher = this;

        /*JPanel principal*/
        JPanel main = new JPanel(new BorderLayout());

        /*JPanel qui contient les boutons en bas de la frame*/
        JPanelButton = new JPanel(new FlowLayout());

        //Bouttons
        btn2Player = new JButton("2 Joueurs");
        btn4Player = new JButton("4 Joueurs");
        btnScore = new JButton("Scores");
        btnExit = new JButton("Quitter");

        /*Settings JFrame*/
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setPreferredSize(new Dimension(700, 350));
        this.setLocationRelativeTo(null);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getPreferredSize().width /2 , (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getPreferredSize().height /2 );
        this.setTitle("Quoridor");

        /*Ajout des Bouttons au panel*/
        JPanelButton.add(btn2Player);
        JPanelButton.add(btn4Player);
        JPanelButton.add(btnScore);
        JPanelButton.add(btnExit);

        /*chargement de l'image*/
        InputStream imageURL = getClass().getResourceAsStream("/images/imgLauncher.jpg");
        JLabel imgMain = null;
        try {
            imgMain = new JLabel(new ImageIcon(ImageIO.read(imageURL)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Chargement de l'icone du programme*/
        InputStream iconeURL = getClass().getResourceAsStream("/images/iconQuoridor.png");
        ImageIcon imgIcon = null;
        try {
            imgIcon = new ImageIcon(ImageIO.read(iconeURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameLauncher.setIconImage(imgIcon.getImage());

        /*Ajout de l'image*/
        main.add(imgMain, BorderLayout.CENTER);
        /*Ajout des bouttons à la frame*/
        main.add(JPanelButton, BorderLayout.PAGE_END);
        this.add(main);

        /*Reglages des boutons*/
        btn2Player.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                launchNames(2);
            }
        });

        btn4Player.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                launchNames(4);
            }
        });

        btnScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(GameController.isScores()){
                    displayScoreTab();
                } else {
                    btnScore.setEnabled(false);
                }
            }
        });

        btnExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                System.exit(0);
            }
        });

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        defineAutoTheme();
        //Lancement de la fenêtre de jeu
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new LauncherGUI().setVisible(true);
            }
        });
    }


    private void launchNames(int nbPlayers) {
        frameLauncher.dispose();
        new NamesGUI(nbPlayers);
    }



    private void displayScoreTab() {
        LinkedHashMap<String, Integer> topRank =  GameController.getTopRank();
        LinkedHashMap<String, List<Integer>> topRankByParticipation =  GameController.getTopRankByParticipation();
        new ScoresGUI(topRank,topRankByParticipation);

    }

    public static void defineAutoTheme(){
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
    }
}
