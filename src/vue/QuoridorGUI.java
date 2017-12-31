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
    private JPanel plateauQuoridor;
    private JPanel garageMurGauche;
    private JPanel garageMurDroit;
    private Coordonnees coordInit;
    // private JLabel chessPiece;
    private int xPionAdjustment;
    private int yPionAdjustment;
    private Dimension dim;
    private int tailleCasePion;
    private int tailleCaseMur;
    private int tailleIHMLargeur;
    private int tailleIHMHauteur;
    private int tailleLargeurGarageMur;
    private int taille;
    private HashMap<Coordonnees,JPanel> mapCoordPanelPion ;
    private HashMap<Coordonnees,JPanel> mapCoordPanelMur ;
    private HashMap<JLabel, PieceIHM> mapPanelPiece;
    private JLabel pion;
    private int taillePlateauQuoridor;
    private int coeffTaille;
    private String urlImages = "C:\\Users\\PC-Florian\\IdeaProjects\\Quoridor_4IRC\\src\\vue\\";

    // Coordonnées de la position initiale de la pièce déplacée
    private Coordonnees coordFinal;

    /**
     *
     * @param title titre de la fenêtre
     * @param taille nombre de case
     */
    public QuoridorGUI(String title, GameController quoridorGameController, int taille) {
        super(title);
        this.quoridorGameController = quoridorGameController;
        pion=null;
        //Creation des maps murs + pions
        mapCoordPanelPion = new HashMap<Coordonnees,JPanel>();
        mapCoordPanelMur = new HashMap<Coordonnees,JPanel>();
        this.coeffTaille = defineCoeffTaille();

        this.taille=taille;
        tailleCasePion = (int) ((0.85 * coeffTaille));
        tailleCaseMur = (int) ((0.15 * coeffTaille));
        tailleLargeurGarageMur = (int) (2.5 * coeffTaille);
        tailleIHMLargeur = taille * tailleCasePion + (taille-1)*tailleCaseMur + 2 * tailleLargeurGarageMur;
        tailleIHMHauteur = taille * tailleCasePion + (taille-1)*tailleCaseMur;
        taillePlateauQuoridor = (taille * tailleCasePion) + ((taille - 1) * tailleCaseMur);

        setLocation(definePositionInScreen());

        //Definition de la taille general de la frame
        dim = new Dimension(tailleIHMLargeur,tailleIHMHauteur);
        getContentPane().setPreferredSize(dim);
        //getContentPane().setLayout(new BorderLayout());
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(dim);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Creation du stockage à murs gauche
        garageMurGauche = new JPanel();
        layeredPane.add(garageMurGauche, JLayeredPane.DEFAULT_LAYER);
        garageMurGauche.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        garageMurGauche.setBounds(0, 0, tailleLargeurGarageMur, tailleIHMHauteur);
        garageMurGauche.setBackground(Color.GREEN);

        //Creation du stockage à murs gauche
        garageMurDroit = new JPanel();
        layeredPane.add(garageMurDroit, JLayeredPane.DEFAULT_LAYER);
        garageMurDroit.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        garageMurDroit.setBounds(taillePlateauQuoridor+tailleLargeurGarageMur, 0, tailleLargeurGarageMur, tailleIHMHauteur);
        garageMurDroit.setBackground(Color.BLUE);

        //Ajout du plateau de jeu
        plateauQuoridor = new JPanel();
        layeredPane.add(plateauQuoridor, JLayeredPane.DEFAULT_LAYER);

        //GridBagLayout pour grille personnalisée
        plateauQuoridor.setLayout(new GridBagLayout());

        plateauQuoridor.setPreferredSize(new Dimension(taillePlateauQuoridor, taillePlateauQuoridor));
        plateauQuoridor.setBounds(tailleLargeurGarageMur, 0, taillePlateauQuoridor, taillePlateauQuoridor);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        for (int j = 0; j < (taille * 2 - 1); j++) {
            constraints.gridx = j ;//décalage dans le grid bag layout
            for (int i = 0; i < (taille * 2 - 1); i++) {
                constraints.gridy = i ;//décalage dans le grid bag layout
                if (j % 2 == 0 && i % 2 == 0) {         // Case pion
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCasePion));
                    square.setBackground(Color.LIGHT_GRAY);
                    plateauQuoridor.add(square,constraints);
                    mapCoordPanelPion.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 0 && i % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCasePion));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 1) {         // petit truc vide
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                }
            }
        }
    }

    public enum Case {
        PION, MURHORIZONTAL, MURVERTICAL, CROISEMENT
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //int x = e.getX()-tailleLargeurGarageMur;
        int x = e.getX();
        int y = e.getY();

        Component cmp = layeredPane.findComponentAt(x, y);
        if(mapCoordPanelMur.containsValue(cmp)){
            JPanel jp = (JPanel) cmp;
            coordFinal = pixelToCell(jp);
            boolean b;
            b = quoridorGameController.putWall(coordFinal);
            if(b){
                positionneUnMur(x - tailleLargeurGarageMur, y);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JPanel jp;
        Component cmp =  layeredPane.findComponentAt(x,y);

        if (mapCoordPanelPion.containsValue(cmp)) {
            jp = (JPanel) cmp;
        }
        else if (mapCoordPanelPion.containsValue(cmp.getParent())) {
            jp = (JPanel) cmp.getParent();
        } else {
            return;
        }

        coordInit = pixelToCell(jp);
        if (jp.getComponents().length == 1) {
            pion = (JLabel) jp.getComponent(0);
            Point parentLocation = pion.getParent().getLocation();
            xPionAdjustment = parentLocation.x - e.getX();
            yPionAdjustment = parentLocation.y - e.getY();
            pion.setLocation(e.getX() + xPionAdjustment + plateauQuoridor.getX(), e.getY() + yPionAdjustment + plateauQuoridor.getY());
            layeredPane.add(pion, JLayeredPane.DRAG_LAYER);
        }
        else {
            System.out.println("Clique hors plateau de jeu");
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pion == null) {
            return;
        }
        int x = e.getX();
        int y = e.getY();

        pion.setVisible(false);
        Component cmp = findComponentAt(x , y);
        if (mapCoordPanelPion.containsValue(cmp)) {
            JPanel jp = (JPanel) cmp;
            coordFinal = pixelToCell(jp);
            boolean b;
            b = quoridorGameController.move(coordInit, coordFinal);
            if(b){
                Container parent = (Container) cmp;
                parent.add(pion);
                pion.setVisible(true);
                pion = null;
            }
        }
        else {
            //Lors d'un click en dehors de l'IHM
            //On remet l'IHM dans les conditions avant deplacement
            System.out.println("mouseReleased hors case pion");
            quoridorGameController.notifyObserver();
            pion=null;
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

            EndWindow fin = new EndWindow();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pion == null) {
            return;
        }
        pion.setLocation(e.getX() + xPionAdjustment + plateauQuoridor.getX(), e.getY() + yPionAdjustment + plateauQuoridor.getY());
    }

    @Override
    public void update(Observable o, Object arg) {
        List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;
        mapPanelPiece = new HashMap<JLabel, PieceIHM>();

        for(PieceIHM pieceIHM : piecesIHM) {
            String url = "images" + File.separator + "Pion" + pieceIHM.getCouleur().toString() + ".png";
            java.net.URL s = QuoridorGUI.class.getResource(url);
            JLabel piece = new JLabel(new ImageIcon(s));
            JPanel panel = (JPanel) plateauQuoridor.getComponent((pieceIHM.getCoordonnees().getY() * 17)+pieceIHM.getCoordonnees().getX());
            mapPanelPiece.put(piece, pieceIHM);
            panel.removeAll();
            panel.add(piece);
        }
    }

    /**
     * @param x position du click en X
     * @param y position du click en Y
     * @return case de type pion, murHorizontal, murVertical croisement
     */
    private Case checkIfMurOrPion(int x, int y) {
        int i,j;
        i = boucleCheckPosition(x);
        j = boucleCheckPosition(y);

        if (i%2 == 1 && j%2 == 1 ) {
            return Case.PION;
        } else if (i%2 == 0 && j%2 == 0) {
            return Case.CROISEMENT;
        } else if (i%2 == 1 && j%2 == 0){
            return Case.MURHORIZONTAL;
        }
        return Case.MURVERTICAL;
    }

    private int[] checkArrayPosition(int x, int y) {
        int values [] = {-1,-1};
        values[0] = boucleCheckPosition(x);
        values[1] = boucleCheckPosition(y);
        return values;
    }

    private int boucleCheckPosition (int p) {
        int i=0;
        while (p>0 && p <  taillePlateauQuoridor ) {
            if (i%2 == 0) {
                p = p - tailleCasePion;
            } else {
                p = p -tailleCaseMur;
            }
            i++;
        }
        return i;
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
    private int defineCoeffTaille () {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return ((int) dim.getWidth() / 18); //18 est un rapport taille/ecran
    }

    /**
     * donne la position du plateau en fonction de la résolution du plateau
     * @return Point
     */
    private Point definePositionInScreen () {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int largeur = (int)(dim.getWidth()-tailleIHMLargeur)/2;
        int hauteur = (int)(dim.getHeight()-tailleIHMHauteur)/2;
        Point p = new Point(largeur,hauteur);

        System.out.println(p);
        return p;
    }

    private Boolean isInPlateau(int x){
        return (x >= tailleLargeurGarageMur && x < (tailleIHMLargeur-tailleLargeurGarageMur));
    }

    private void positionneUnMur(int x,int y) {
        Case murOrPion = checkIfMurOrPion(x,y);

        int [] position = checkArrayPosition(x,y);

        if (murOrPion.equals(Case.MURHORIZONTAL)) {
            int pos = convertCoordToCell(position[0],position[1]);
            if (position[0] <= 15) {
                JPanel j = (JPanel) plateauQuoridor.getComponent(pos);
                JPanel k = (JPanel) plateauQuoridor.getComponent(pos + 17);
                JPanel l = (JPanel) plateauQuoridor.getComponent(pos + 34);
                if (j.getBackground() != Color.BLUE && k.getBackground() != Color.BLUE && l.getBackground() !=Color.BLUE)
                {
                    j.setBackground(Color.BLUE);
                    k.setBackground(Color.BLUE);
                    l.setBackground(Color.BLUE);
                }
            }
        }

        if (murOrPion.equals(Case.MURVERTICAL)) {
            int pos = convertCoordToCell(position[0],position[1]);
            if (position[1] <= 15) {
                JPanel j = (JPanel) plateauQuoridor.getComponent(pos);
                JPanel k = (JPanel) plateauQuoridor.getComponent(pos + 1);
                JPanel l = (JPanel) plateauQuoridor.getComponent(pos + 2);
                if (j.getBackground() != Color.BLUE && k.getBackground() != Color.BLUE && l.getBackground() !=Color.BLUE)
                {
                    j.setBackground(Color.BLUE);
                    k.setBackground(Color.BLUE);
                    l.setBackground(Color.BLUE);
                }
            }
        }
    }

    private Coordonnees pixelToCell(JPanel component){
        for(Map.Entry mapEntry : mapCoordPanelPion.entrySet()){
            if(component.equals(mapEntry.getValue())){
                return (Coordonnees)mapEntry.getKey();
            }
        }

        for(Map.Entry mapEntry : mapCoordPanelMur.entrySet()){
            if(component.equals(mapEntry.getValue())){
                return (Coordonnees)mapEntry.getKey();
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
}