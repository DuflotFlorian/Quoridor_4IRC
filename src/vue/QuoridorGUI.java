package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.awt.Color;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import Class.*;
import Class.observable.QuoridorGame;
import Controller.GameController;
import launcher.LauncherGUI;
import org.json.simple.parser.ParseException;


public class QuoridorGUI extends JFrame implements MouseListener, MouseMotionListener, Observer {
    private GameController quoridorGameController;
    private JLayeredPane layeredPane;
    private JPanel boardQuoridor;
    private JPanel panelInfoLeft;
    private JPanel panelInfoRight;
    private Coordinates coordInit;
    private int xPawnAdjustment;
    private int yPawnAdjustment;
    private int sizeSquarePawn;
    private int widthIHM;
    private int heightIHM;
    private HashMap<Coordinates,JPanel> mapCoordPanelPawn;
    private HashMap<Coordinates,JPanel> mapCoordPanelWall;
    private HashMap<JLabel, PieceIHM> mapPanelPiece;
    private JLabel pawn;
    private int coeffSize;
    private Color wallColor;
    private Color backgroundColor;
    private ArrayList<JPanel> arraySidePanel;

    private JLabel jLabelHelp;
    private JPanel jPanelHelp;

    // Coordonnées de la position initiale de la pièce déplacée
    private Coordinates coordFinal;

    /**
     *
     * @param title titre de la fenêtre
     * @param size nombre de case
     */
    public QuoridorGUI(String title, GameController quoridorGameController, int size) {
        super(title);
        this.quoridorGameController = quoridorGameController;
        pawn = null;
        //Creation des maps murs + pions
        mapCoordPanelPawn = new HashMap<Coordinates,JPanel>();
        mapCoordPanelWall = new HashMap<Coordinates,JPanel>();
        arraySidePanel = new ArrayList<JPanel>();
        this.coeffSize = defineCoeffSize();
        this.wallColor = new Color(404040);
        this.backgroundColor = new Color(404040);
        sizeSquarePawn = (int) ((0.85 * coeffSize));
        int sizeSquareWall = (int) ((0.15 * coeffSize));
        int widthPanelInfo = (int) (2.5 * coeffSize);
        widthIHM = size * sizeSquarePawn + (size-1)* sizeSquareWall + 2 * widthPanelInfo;
        heightIHM = size * sizeSquarePawn + (size-1)* sizeSquareWall;
        int sizeBoardQuoridor = (size * sizeSquarePawn) + ((size - 1) * sizeSquareWall);
        /*Chargement de l'icone du programme*/
        InputStream iconeURL = getClass().getResourceAsStream("/images/iconQuoridor.png");
        ImageIcon imgIcon = null;
        try {
            imgIcon = new ImageIcon(ImageIO.read(iconeURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setIconImage(imgIcon.getImage());
        setLocation(definePositionInScreen());

        //Definition de la size general de la frame
        Dimension dim = new Dimension(widthIHM, heightIHM);

        getContentPane().setPreferredSize(dim);
        //getContentPane().setLayout(new BorderLayout());
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(dim);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);
        getContentPane().setBackground(this.backgroundColor);

        //Creation du stockage à murs gauche
        panelInfoLeft = new JPanel();
        panelInfoLeft.setBackground(this.backgroundColor);
        layeredPane.add(panelInfoLeft, JLayeredPane.DEFAULT_LAYER);
        panelInfoLeft.setPreferredSize(new Dimension(widthPanelInfo, heightIHM));
        panelInfoLeft.setBounds(0, 0, widthPanelInfo, heightIHM);

        //Creation du stockage à murs droite
        panelInfoRight = new JPanel();
        layeredPane.add(panelInfoRight, JLayeredPane.DEFAULT_LAYER);
        panelInfoRight.setPreferredSize(new Dimension(widthPanelInfo, heightIHM));
        panelInfoRight.setBounds(sizeBoardQuoridor + widthPanelInfo, 0, widthPanelInfo, heightIHM);
        panelInfoRight.setBackground(this.backgroundColor);
        //Ajout du plateau de jeu
        boardQuoridor = new JPanel();
        layeredPane.add(boardQuoridor, JLayeredPane.DEFAULT_LAYER);

        //GridBagLayout pour grille personnalisée
        boardQuoridor.setLayout(new GridBagLayout());

        boardQuoridor.setPreferredSize(new Dimension(sizeBoardQuoridor, sizeBoardQuoridor));
        boardQuoridor.setBounds(widthPanelInfo, 0, sizeBoardQuoridor, sizeBoardQuoridor);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        for (int j = 0; j < (size * 2 - 1); j++) {
            constraints.gridx = j ;//décalage dans le grid bag layout
            for (int i = 0; i < (size * 2 - 1); i++) {
                constraints.gridy = i ;//décalage dans le grid bag layout
                if (j % 2 == 0 && i % 2 == 0) {         // Case pawn
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquarePawn, sizeSquarePawn));
                    square.setBackground(Color.LIGHT_GRAY);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelPawn.put(new Coordinates(i,j), square);
                } else if (j % 2 == 0 && i % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquarePawn, sizeSquareWall));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j), square);
                } else if (j % 2 == 1 && i % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquareWall, sizeSquarePawn));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j), square);
                } else if (j % 2 == 1 && i % 2 == 1) {         // petit truc vide
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquareWall, sizeSquareWall));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j), square);
                }
            }
        }

        jLabelHelp = new JLabel();
        jLabelHelp.setBounds(widthPanelInfo / 2 - coeffSize / 2, heightIHM - coeffSize, coeffSize, coeffSize);//placement adapté à la résolution

        InputStream urlHelp = getClass().getResourceAsStream("/images/iconeAide.png");
        try {
            jLabelHelp.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(urlHelp)).getImage().getScaledInstance(coeffSize, coeffSize, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        layeredPane.add(jLabelHelp, JLayeredPane.PALETTE_LAYER);//Affichage juste dessus de l'interface de base, evite les colisions

        //Creation du JPanel d'aide à afficher
        jPanelHelp = new JPanel();
        createUserHelp(jPanelHelp);

        //Ajout d'un listener sur le JLabelAide
        jLabelHelp.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                jPanelHelp.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                jPanelHelp.setVisible(false);
            }

        });
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        Component cmp = layeredPane.findComponentAt(x, y);
        if(mapCoordPanelWall.containsValue(cmp)){
            JPanel jp = (JPanel) cmp;
            coordFinal = pixelToCell(jp);
            boolean b;
            b = quoridorGameController.putWall(coordFinal);
            if(b){
                positionWall(coordFinal);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JPanel jp;

        Component cmp =  layeredPane.findComponentAt(x,y);
        if (mapCoordPanelPawn.containsValue(cmp)) {
            jp = (JPanel) cmp;
        }
        else if (mapCoordPanelPawn.containsValue(cmp.getParent())) {
            jp = (JPanel) cmp.getParent();
        } else {
            return;
        }

        coordInit = pixelToCell(jp);
        if (jp.getComponents().length == 1) {
            pawn = (JLabel) jp.getComponent(0);
            Point parentLocation = pawn.getParent().getLocation();
            xPawnAdjustment = parentLocation.x - e.getX();
            yPawnAdjustment = parentLocation.y - e.getY();
            pawn.setLocation(e.getX() + xPawnAdjustment + boardQuoridor.getX(), e.getY() + yPawnAdjustment + boardQuoridor.getY());
            layeredPane.add(pawn, JLayeredPane.DRAG_LAYER);

        }
        else {
            System.out.println("Clique hors plateau de jeu");
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pawn == null) {
            return;
        }
        int x = e.getX();
        int y = e.getY();

        pawn.setVisible(false);
        Component cmp = findComponentAt(x , y);
        if (mapCoordPanelPawn.containsValue(cmp)) {
            JPanel jp = (JPanel) cmp;
            coordFinal = pixelToCell(jp);
            boolean b;
            b = quoridorGameController.move(coordInit, coordFinal);
            if(b){
                Container parent = (Container) cmp;
                parent.add(pawn);
                pawn.setVisible(true);
                pawn = null;
            }
        }
        else {
            //Lors d'un click en dehors de l'IHM
            //On remet l'IHM dans les conditions avant deplacement
            System.out.println("mouseReleased hors case pawn");
            quoridorGameController.notifyObserver();
            pawn =null;
        }

        if(quoridorGameController.isEnd()){
            quoridorGameController.deleteObservers();

            class EndWindow extends JOptionPane{
                private JButton btnQuit = new JButton("Quitter");
                private JButton btnSave = new JButton("Sauvegarde");
                private JButton btnMenu = new JButton("Menu Principal");
                Object[] options = {btnQuit,
                                    btnSave,
                                    btnMenu};

                public EndWindow(){
                    btnQuit.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent arg0) {
                            System.exit(0);
                        }
                    });

                    btnSave.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            //enregistrement de la partie dans le fichier de score
                            int numCurrentPlayer =  quoridorGameController.getCurrentPlayer();
                            QuoridorColor currentPlayerColor = quoridorGameController.getPlayerColor(numCurrentPlayer-1);
                            List<Player> listPlayers = quoridorGameController.listPlayer();
                            try {
                                Scores.createJson(listPlayers, currentPlayerColor);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            btnSave.setEnabled(false);
                            Window w = SwingUtilities.getWindowAncestor(btnSave);
                            if (w != null) w.setVisible(false);
                            JOptionPane.showOptionDialog(layeredPane,
                                    "Score de la partie sauvegardé",
                                    "FIN DE LA PARTIE",
                                    JOptionPane.YES_NO_CANCEL_OPTION,
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    options,
                                    options[1]);

                        }
                    });

                    btnMenu.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            QuoridorGUI.super.dispose();
                            new LauncherGUI().setVisible(true);
                        }
                    });

                    JOptionPane.showOptionDialog(layeredPane,
                            "Vous avez fini!",
                            "FIN DE LA PARTIE",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]);

                    this.setVisible(true);
                }
            }

            new EndWindow();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pawn == null) {
            return;
        }
        pawn.setLocation(e.getX() + xPawnAdjustment + boardQuoridor.getX(), e.getY() + yPawnAdjustment + boardQuoridor.getY());
    }

    @Override
    public void update(Observable o, Object arg) {
        List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;
        mapPanelPiece = new HashMap<JLabel, PieceIHM>();

        for(PieceIHM pieceIHM : piecesIHM) {
            if(pieceIHM.getNamePiece().equals("Pawn")) {
                String url = "/images/Pawn" + pieceIHM.getQuoridorColor().toString() + ".png";
                InputStream inputStreamUrl = getClass().getResourceAsStream(url);
                JLabel piece = new JLabel();
                try {
                    piece.setIcon(new ImageIcon(new ImageIcon(ImageIO.read(inputStreamUrl)).getImage().getScaledInstance(sizeSquarePawn, sizeSquarePawn, Image.SCALE_SMOOTH)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JPanel panel = (JPanel) boardQuoridor.getComponent((pieceIHM.getCoordinates().getY() * 17) + pieceIHM.getCoordinates().getX());
                mapPanelPiece.put(piece, pieceIHM);
                panel.removeAll();
                panel.add(piece);
            }
        }

        fillSidePanel();
    }

    /**
     * donne le coefficient de la taille du plateau en fonction de la résolution de l'écran
     * @return int
     */
    private int defineCoeffSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return ((int) dim.getHeight() / 11); //11 est un rapport taille/ecran
    }

    /**
     * donne la position du plateau en fonction de la résolution du plateau
     * @return Point
     */
    private Point definePositionInScreen () {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(dim.getWidth()- widthIHM) / 2;
        int height = (int)(dim.getHeight()- heightIHM) / 2 - coeffSize / 2;
        return new Point(width,height);
    }

    /**
     * affiche un mur à l'emplacement des coordonnées passées
     * horizontal, on part de la gauche puis les 3 cases à droites
     * vertical : on part du haut ; puis les 3 cases dessous
     * Avant d'appeler cette méthode la vérification et le placement coté model/controller doivennt être faites
     * @param coord, cInterface
     */
    private void positionWall(Coordinates coord) {
        if(Wall.isWallHorizontal(coord)) {
            colorizeWallPanel(this.mapCoordPanelWall.get(coord));
            colorizeWallPanel(this.mapCoordPanelWall.get(new Coordinates(coord.getX(), coord.getY() + 1)));
            colorizeWallPanel(this.mapCoordPanelWall.get(new Coordinates(coord.getX(), coord.getY() + 2)));
        } else {
            colorizeWallPanel(this.mapCoordPanelWall.get(coord));
            colorizeWallPanel(this.mapCoordPanelWall.get(new Coordinates(coord.getX() + 1, coord.getY())));
            colorizeWallPanel(this.mapCoordPanelWall.get(new Coordinates(coord.getX() + 2, coord.getY())));
        }
    }

    private void colorizeWallPanel(JPanel cell) {
        cell.setBackground(wallColor); //colorize la case courante
    }

    private Coordinates pixelToCell(JPanel component){
        for(Map.Entry mapEntry : mapCoordPanelPawn.entrySet()){
            if(component.equals(mapEntry.getValue())){
                return (Coordinates)mapEntry.getKey();
            }
        }

        for(Map.Entry mapEntry : mapCoordPanelWall.entrySet()){
            if(component.equals(mapEntry.getValue())){
                return (Coordinates)mapEntry.getKey();
            }
        }

        return null;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    private void createUserHelp(JPanel jp) {
        jp.setBounds(widthIHM / 2 - coeffSize * 2, heightIHM / 2 - coeffSize * 2,coeffSize * 4, coeffSize * 4);
        jp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, coeffSize / 12));

        //Ajout du contenu
        jp.setLayout(new GridLayout(2, 1));

        JLabel labelHelpPawn = new JLabel("<html>Déplacement d'un pion:<br/>- Cliquer sur votre pion et faites le glisser sur la case voulue.<br/>- Si le déplacement n'est pas autorisé, le pion revient à sa position de départ.<br/></html>");
        JLabel labelHelpWall = new JLabel("<html>Positionnement d'un mur:<br/>- Pour un mur horizontal:<br/>&emsp;&emsp;Cliquer sur la case la plus à gauche du mur<br/>- Pour un mur vertical:<br/>&emsp;&emsp;Cliquer sur la case la plus en haut du mur<br/></html>");

        jp.add(labelHelpPawn);
        jp.add(labelHelpWall);

        layeredPane.add(jp, JLayeredPane.MODAL_LAYER);
        jp.setVisible(false);
    }

    private void fillSidePanel () {
        panelInfoRight.removeAll();
        panelInfoLeft.removeAll();
        int nbPlayer = quoridorGameController.listPlayer().size();
        int nbRowInColumn = 0; //Variable qui permet de savoir combien de panel sont mis dans les panels sur les cotés
        //Pour 2 joueurs, 3 panel et on remplis celui du milieu
        //Pour 4 joueurs,  panel et on remplis le 2ème et le 4ème

        JPanel [][] tabLeftPanel;
        JPanel [][] tabRightPanel;

        if (nbPlayer == 2) {
            nbRowInColumn = 3;

        } else if (nbPlayer == 4) {
            nbRowInColumn = 5;
        }

        panelInfoLeft.setLayout(new GridLayout(nbRowInColumn,1));
        panelInfoRight.setLayout(new GridLayout(nbRowInColumn,1));

        tabLeftPanel = new JPanel[nbRowInColumn][1];
        for (int i = 0; i < nbRowInColumn; i++) {
            tabLeftPanel[i][0] = new JPanel();
            tabLeftPanel[i][0].setBackground(this.backgroundColor);
            panelInfoLeft.add(tabLeftPanel[i][0]);
        }

        tabRightPanel = new JPanel[nbRowInColumn][1];
        for (int i = 0; i < nbRowInColumn; i++) {
            tabRightPanel[i][0] = new JPanel();
            tabRightPanel[i][0].setBackground(this.backgroundColor);
            panelInfoRight.add(tabRightPanel[i][0]);
        }

        if(nbPlayer == 2) {
            createSideLabel(tabLeftPanel[1][0],1); //Player 1
            createSideLabel(tabRightPanel[1][0],2); //Player 2

        } else if (nbPlayer == 4){
            createSideLabel(tabLeftPanel[1][0],1); //Player 1
            createSideLabel(tabRightPanel[1][0],2); //Player 2
            createSideLabel(tabLeftPanel[3][0],3); //Player 3
            createSideLabel(tabRightPanel[3][0],4); //Player 4
        }
    }

    private void createSideLabel(JPanel jp, int numPlayer) {
        int numCurrentPlayer =  quoridorGameController.getCurrentPlayer();
        QuoridorColor currentPlayerColor = quoridorGameController.getPlayerColor(numPlayer - 1);


        if(numPlayer == numCurrentPlayer + 1) {
            jp.setBorder(BorderFactory.createLineBorder(currentPlayerColor.getColorBox(), coeffSize / 12));
        }

        // display the player name in french
        JLabel playerName = currentPlayerColor.getJLabelPlayerName();

        // set the player panel
        jp.setBackground(new Color(0x808080)); //gris foncé
        jp.setLayout(new GridLayout(2,1));
        playerName.setFont(new Font("Impact", Font.PLAIN, coeffSize / 3));
        jp.add(playerName);

        // display the remaining wall of current player
        JLabel remainWall = new JLabel("" + quoridorGameController.getPlayerWallRemaining(numPlayer - 1));
        remainWall.setForeground(currentPlayerColor.getColorBox());
        remainWall.setHorizontalAlignment(JLabel.CENTER);
        remainWall.setFont(new Font("Impact", Font.PLAIN, coeffSize / 2));
        jp.add(remainWall);

    }
}