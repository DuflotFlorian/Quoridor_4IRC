package launcher;

import Class.observable.QuoridorGame;
import Controller.GameController;
import vue.QuoridorGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import static launcher.LauncherGUI.defineAutoTheme;

public class NamesGUI extends JFrame {
    private JFrame frameLauncher;
    private JButton launchGame;
    private Color backgroundColor = new Color(404040);
    private int nbPlayers;
    private List <String> listName;
    private JTextField t1,t2,t3,t4;
    private QuoridorGame quoridorGame;
    private GameController quoridorGameController;

    public NamesGUI(int nbP) {
        super();
        this.nbPlayers = nbP;
        frameLauncher = this;
//        JPanel main = new JPanel(new GridLayout(this.nbPlayers+1,2));
        JPanel main = new JPanel(new GridLayout(this.nbPlayers+1,1));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getPreferredSize().width /2 , (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getPreferredSize().height /2 );
        this.setTitle("Nom des Joueurs | Quoridor");

        //set the default name's players
        this.listName = new ArrayList<String>();
        this.listName.add("Joueur BLEU");
        this.listName.add("Joueur ROUGE");
        this.listName.add("Joueur JAUNE");
        this.listName.add("Joueur VERT");

        // set the game
        quoridorGame = new QuoridorGame(this.nbPlayers, listName);
        quoridorGameController = new GameController(quoridorGame);

        //Chargement de l'icone du programme
        InputStream iconeURL = getClass().getResourceAsStream("/images/iconQuoridor.png");
        ImageIcon imgIcon = null;
        try {
            imgIcon = new ImageIcon(ImageIO.read(iconeURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameLauncher.setIconImage(imgIcon.getImage());
        this.add(main);


        switch (this.nbPlayers) {
            case 2:
                Box hBox = Box.createHorizontalBox();
                main.add(hBox);
                hBox.add(getPlayerIcon("/images/PawnBLUE.png"));
                t1 = new JTextField(quoridorGameController.getPlayerName(0));
                hBox.add(getJtextField(t1));

                Box hBox2 = Box.createHorizontalBox();
                main.add(hBox2);
                hBox2.add(getPlayerIcon("/images/PawnRED.png"));
                t2 = new JTextField(quoridorGameController.getPlayerName(1));
                hBox2.add(getJtextField(t2));

                break;
            case 4:
                Box h4Box = Box.createHorizontalBox();
                main.add(h4Box);
                h4Box.add(getPlayerIcon("/images/PawnBLUE.png"));
                t1 = new JTextField(quoridorGameController.getPlayerName(0));
                h4Box.add(getJtextField(t1));

                Box h4Box2 = Box.createHorizontalBox();
                main.add(h4Box2);
                h4Box2.add(getPlayerIcon("/images/PawnRED.png"));
                t2 = new JTextField(quoridorGameController.getPlayerName(1));
                h4Box2.add(getJtextField(t2));

                Box h4Box3 = Box.createHorizontalBox();
                main.add(h4Box3);
                h4Box3.add(getPlayerIcon("/images/PawnYELLOW.png"));
                t3 = new JTextField(quoridorGameController.getPlayerName(2));
                h4Box3.add(getJtextField(t3));

                Box h4Box4 = Box.createHorizontalBox();
                main.add(h4Box4);
                h4Box4.add(getPlayerIcon("/images/PawnGREEN.png"));
                t4 = new JTextField(quoridorGameController.getPlayerName(3));
                h4Box4.add(getJtextField(t4));
                break;

            default:
                break;
        }

        launchGame = new JButton("PLAY");
        JPanel JPanelButton = new JPanel(new FlowLayout());
        JPanelButton.setBackground(this.backgroundColor);
        JPanelButton.add(launchGame);
        //main.add(new JLabel());// case vide
        main.add(JPanelButton);// bouton "play"
        main.setBackground(this.backgroundColor);
        main.setMinimumSize(new Dimension(500,300));
        getContentPane().setBackground(this.backgroundColor);
        this.pack();
        this.setVisible(true);




        launchGame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // set new player name
                List playerNames = quoridorGameController.listPlayer();

                switch (nbPlayers) {
                    case 2:
                        quoridorGameController.setPlayerName(0, t1.getText());
                        listName.set(0,t1.getText());
                        quoridorGameController.setPlayerName(1, t2.getText());
                        listName.set(1,t2.getText());
                        break;
                    case 4:
                        quoridorGameController.setPlayerName(0, t1.getText());
                        listName.set(0,t1.getText());
                        quoridorGameController.setPlayerName(1, t2.getText());
                        listName.set(1,t2.getText());
                        quoridorGameController.setPlayerName(2, t3.getText());
                        listName.set(2,t3.getText());
                        quoridorGameController.setPlayerName(3, t4.getText());
                        listName.set(3,t4.getText());
                        break;

                    default:
                        break;
                }

                launchFrameGame();
            }
        });







    }

    public void launchFrameGame(){
        frameLauncher.dispose();

        QuoridorGame quoridorGame;
        GameController quoridorGameController;
        quoridorGame = new QuoridorGame(this.nbPlayers, listName);
        quoridorGameController = new GameController(quoridorGame);


        //launch game windows
        JFrame quoridorFrame;
        quoridorFrame = new QuoridorGUI("Quoridor", quoridorGameController,  9);
        quoridorGame.addObserver((Observer) quoridorFrame);
        quoridorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        quoridorFrame.pack();
        quoridorFrame.setVisible(true); ///  DEBUG cache launcher
    }

    public static void main(String[] args) {
        defineAutoTheme();
    }

    private JLabel getPlayerIcon(String url) {
        InputStream inputStreamUrl = getClass().getResourceAsStream(url);
        JLabel pawnJlabel = new JLabel();
        try {
            pawnJlabel.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(inputStreamUrl)).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pawnJlabel;
    }

    private JTextField getJtextField(JTextField textField){
        textField.setPreferredSize( new Dimension( 150, 24 ) );
        textField.setForeground(Color.BLACK);
        textField.setBackground(this.backgroundColor);
        textField.setOpaque(false);
        textField.setBorder(new LineBorder(this.backgroundColor, 20));
        return textField;
    }
}

