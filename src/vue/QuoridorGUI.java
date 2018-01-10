package vue;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;
import Class.*;
import Controller.GameController;


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
    private ArrayList<JPanel> arraySidePanel;

    private JLabel jLabelAide;
    private JPanel jPanelAide;


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
        arraySidePanel = new ArrayList<>();
        this.coeffSize = defineCoeffSize();
        this.wallColor = new Color(404040);
        sizeSquarePawn = (int) ((0.85 * coeffSize));
        int sizeSquareWall = (int) ((0.15 * coeffSize));
        int widthPanelInfo = (int) (2.5 * coeffSize);
        widthIHM = size * sizeSquarePawn + (size-1)* sizeSquareWall + 2 * widthPanelInfo;
        heightIHM = size * sizeSquarePawn + (size-1)* sizeSquareWall;
        int sizeBoardQuoridor = (size * sizeSquarePawn) + ((size - 1) * sizeSquareWall);

        /*Chargement de l'icone du programme*/
        java.net.URL iconeURL = QuoridorGUI.class.getResource(".." + File.separator + "SharedFiles"+ File.separator + "iconQuoridor.png");
        ImageIcon imgIcon = new ImageIcon(iconeURL);
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

        //Creation du stockage à murs gauche
        panelInfoLeft = new JPanel();
        layeredPane.add(panelInfoLeft, JLayeredPane.DEFAULT_LAYER);
        panelInfoLeft.setPreferredSize(new Dimension(widthPanelInfo, heightIHM));
        panelInfoLeft.setBounds(0, 0, widthPanelInfo, heightIHM);

        //Creation du stockage à murs gauche
        panelInfoRight = new JPanel();
        layeredPane.add(panelInfoRight, JLayeredPane.DEFAULT_LAYER);
        panelInfoRight.setPreferredSize(new Dimension(widthPanelInfo, heightIHM));
        panelInfoRight.setBounds(sizeBoardQuoridor + widthPanelInfo, 0, widthPanelInfo, heightIHM);

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
                    mapCoordPanelPawn.put(new Coordinates(i,j),square);
                } else if (j % 2 == 0 && i % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquarePawn, sizeSquareWall));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquareWall, sizeSquarePawn));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 1) {         // petit truc vide
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(sizeSquareWall, sizeSquareWall));
                    square.setBackground(Color.WHITE);
                    boardQuoridor.add(square,constraints);
                    mapCoordPanelWall.put(new Coordinates(i,j),square);
                }
            }
        }


        jLabelAide = new JLabel();
        jLabelAide.setBounds(tailleLargeurGarageMur/2 - coeffTaille/2,tailleIHMHauteur-coeffTaille,coeffTaille,coeffTaille);//placement adapté à la résolution

        String urlAide = "images" + File.separator + "iconeAide.png";
        java.net.URL sAide = QuoridorGUI.class.getResource(urlAide);
        jLabelAide.setIcon(new ImageIcon(new ImageIcon(sAide).getImage().getScaledInstance(coeffTaille, coeffTaille, Image.SCALE_SMOOTH)));

        layeredPane.add(jLabelAide, JLayeredPane.PALETTE_LAYER);//Affichage juste dessus de l'interface de base, evite les colisions

        //Creation du JPanel d'aide à afficher
        jPanelAide = new JPanel();
        createAideUtilisateur(jPanelAide);

        //Ajout d'un listener sur le JLabelAide
        jLabelAide.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent me) {
                jPanelAide.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                jPanelAide.setVisible(false);
            }

        });
    }

    public enum Case {
        PION, MURHORIZONTAL, MURVERTICAL, CROISEMENT
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
            class EndWindow extends JFrame{
                private JButton button = new JButton("FIN DE LA PARTIE");

                public EndWindow(){
                    this.setTitle("INFORMATION");
                    this.setSize(300, 100);
                    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    this.setLocationRelativeTo(null);
                    this.getContentPane().setLayout(new FlowLayout());
                    this.getContentPane().add(button);
                    button.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent arg0) {
                            System.exit(0);
                        }
                    });

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
                String url = "images" + File.separator + "Pawn" + pieceIHM.getQuoridorColor().toString() + ".png";
                java.net.URL s = QuoridorGUI.class.getResource(url);
                JLabel piece = new JLabel();
                piece.setIcon(new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(sizeSquarePawn, sizeSquarePawn, Image.SCALE_SMOOTH)));
                JPanel panel = (JPanel) boardQuoridor.getComponent((pieceIHM.getCoordinates().getY() * 17) + pieceIHM.getCoordinates().getX());
                mapPanelPiece.put(piece, pieceIHM);
                panel.removeAll();
                panel.add(piece);
            }
        }

        fillSidePanel();
    }

    /**
     * nous donne le type de cellule sélectionnée
     * @param c coordonnées
     * @return case de type pion, murHorizontal, murVertical croisement
     */
    private Case checkIfMurOrPion(Coordonnees c) {
        int i=c.getX(), j=c.getY();

        if (i%2 == 0 && j%2 == 0 ) {
            return Case.PION;
        } else if (i%2 == 1 && j%2 == 1) {
            return Case.CROISEMENT;
        } else if (i%2 == 0 && j%2 == 1){
            return Case.MURVERTICAL;
        }
        return Case.MURHORIZONTAL;
    }

    /**
     * permet de retourner une coordonnée sur un clic dans le plateau
     * @param x en Pixels
     * @param y en Pixels
     * @return Coordonnée offset  (numéro de ligne, numéro de colonne)
     */
    private Coordonnees getArrayPosition(int x, int y) {
        Coordonnees c = new Coordonnees(getOffsetFromPixel(x),getOffsetFromPixel(y));
        return c;
    }

    /**
     *  retourne une position offset dans le plateau
     *  cette fonction est appelée une fois par Axe (ligne ou colonne)
     *
     * @param p la position en pixel (soit un X soit un Y)
     * @return offset du tableau de jeu
     */
    private int getOffsetFromPixel (int p) {
        int i=0;
        while (p>0 && p < taillePlateauQuoridor ) {
            if (i%2 == 0) {
                p = p - tailleCasePion;
            } else {
                p = p - tailleCaseMur;
            }
            i++;
        }
        return i-1;
    }


    /**
     *
     * @param x position en X
     * @param y position en Y
     * @return le numéro de cellule (0 en haut a gauche, 288 en bas a droite)
     */
    private int convertCoordToCell(int x,int y){
        if (x == 1) {

            return y-1;
        }
        return ((x*17)-17) + y-1;
    }


    /**
     * donne le coefficient de la taille du plateau en fonction de la résolution de l'écran
     * @return int
     */
    private int defineCoeffSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return ((int) dim.getWidth() / 18); //18 est un rapport taille/ecran
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

    private void createAideUtilisateur(JPanel jp) {
        jp.setBounds(tailleIHMLargeur/2 - coeffTaille*2,tailleIHMHauteur/2 -coeffTaille*2,coeffTaille*4,coeffTaille*4);
        jp.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY ,coeffTaille/12));

        //Ajout du contenu
        jp.setLayout(new GridLayout(2,1));

        JLabel txtAidePion = new JLabel("<html>Déplacement d'un pion:<br/>- Cliquer sur votre pion et faites le glisser sur la case voulue.<br/>- Si le déplacement n'est pas autorisé, le pion revient à sa position de départ.<br/></html>");
        JLabel txtAideMur = new JLabel("<html>Positionnement d'un mur:<br/>- Pour un mur horizontal:<br/>&emsp;&emsp;Cliquer sur la case la plus à gauche du mur<br/>- Pour un mur vertical:<br/>&emsp;&emsp;Cliquer sur la case la plus en haut du mur<br/></html>");

        jp.add(txtAidePion);
        jp.add(txtAideMur);

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
            tabLeftPanel[i][0].setBackground(new Color(404040)); //Bleu-Nuit
            panelInfoLeft.add(tabLeftPanel[i][0]);
        }

        tabRightPanel = new JPanel[nbRowInColumn][1];
        for (int i = 0; i < nbRowInColumn; i++) {
            tabRightPanel[i][0] = new JPanel();
            tabRightPanel[i][0].setBackground(new Color(404040)); //Bleu-Nuit
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

        if(numPlayer == numCurrentPlayer + 1) {
            jp.setBorder(BorderFactory.createLineBorder(Color.GREEN, coeffSize / 12));
        }
        Color c = null;
        //Adapté pour 2 joueurs, rajouter les conditions lors du passage à 4 joueurs
        if(quoridorGameController.getPlayerColor(numPlayer - 1).equals(QuoridorColor.BLUE)) {
            c = new Color(0x8CC6D7); //Bleu pale
        } else if (quoridorGameController.getPlayerColor(numPlayer - 1).equals(QuoridorColor.RED)) {
            c = new Color(0xDB0B32); //Proche Rouge Pawn
        }

        jp.setBackground(new Color(0x808080)); //gris foncé
        jp.setLayout(new GridLayout(2, 1));
        JLabel jl1 =  new JLabel("Player " + quoridorGameController.getPlayerColor(numPlayer - 1));
        jl1.setForeground(c);
        jl1.setHorizontalAlignment(JLabel.CENTER);
        jl1.setFont(new Font("Impact", Font.PLAIN, coeffSize / 3));
        jp.add(jl1);
        JLabel jl2 = new JLabel("" + quoridorGameController.getPlayerWallRemaining(numPlayer - 1));
        jl2.setForeground(c);
        jl2.setHorizontalAlignment(JLabel.CENTER);
        jl2.setFont(new Font("Impact", Font.PLAIN, coeffSize / 2));
        jp.add(jl2);
    }
}