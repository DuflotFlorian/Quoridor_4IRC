package vue;

import java.awt.*;

import static java.awt.GridBagConstraints.NORTHWEST;
import static javax.swing.JLayeredPane.DRAG_LAYER;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Class.Coordonnees;
import java.util.Map;

/**
 *
 * @author Kevin
 */
public class QuoridorGUI extends JFrame implements MouseListener, MouseMotionListener {

    private JLayeredPane layeredPane;
    private JPanel plateauQuoridor;
    private JPanel garageMurGauche;
    private JPanel garageMurDroit;
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
    private JLabel pion;
    private int taillePlateauQuoridor;
    private int coeffTaille;

    // Coordonnées de la position initiale de la pièce déplacée
    private Coordonnees coordInit;

    public QuoridorGUI(String title, int taille) {
        super(title);
        pion=null;
        //Creation des maps murs + pions
        mapCoordPanelPion = new HashMap<Coordonnees,JPanel>();
        mapCoordPanelMur = new HashMap<Coordonnees,JPanel>();
        this.coeffTaille = defineCoeffTaille();





        this.taille=taille;
        tailleCasePion = (int) ((0.85 * coeffTaille));
        tailleCaseMur = (int) ((0.15 * coeffTaille));
        tailleLargeurGarageMur = (int) (2.5 * coeffTaille);
        //ajouter une barre en haut avec les informations qui vont bien
        tailleIHMLargeur = taille * tailleCasePion + (taille-1)*tailleCaseMur + 2 * tailleLargeurGarageMur;
        tailleIHMHauteur = taille * tailleCasePion + (taille-1)*tailleCaseMur;
        taillePlateauQuoridor = (taille * tailleCasePion) + ((taille - 1) * tailleCaseMur);
        //Definition du layout general
        //this.setLayout(new BorderLayout());

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
        //plateauQuoridor.setPreferredSize(dim);
        plateauQuoridor.setPreferredSize(new Dimension(taillePlateauQuoridor, taillePlateauQuoridor));
        plateauQuoridor.setBounds(tailleLargeurGarageMur, 0, taillePlateauQuoridor, taillePlateauQuoridor);
        //System.out.println(taille+","+tailleCasePion +","+tailleCaseMur);

        int casePosX = 0; // Position sur le grille
        int casePosY = 0; // Position sur la grille


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        /*voir pour faire le remplissage des garages murs*/
        for (int i = 0; i < (taille * 2 - 1); i++) {
            constraints.gridx = i ;//décalage dans le grid bag layout

            for (int j = 0; j < (taille * 2 - 1); j++) {

                constraints.gridy = j ;//décalage dans le grid bag layout

                if (i % 2 == 0 && j % 2 == 0) {         // Case pion
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCasePion));
                    square.setBackground(Color.LIGHT_GRAY);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCasePion ;
                    casePosY += tailleCasePion ;
                    mapCoordPanelPion.put(new Coordonnees(i,j),square);
                } else if (i % 2 == 0 && j % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCasePion ;
                    casePosY += tailleCaseMur ;
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (i % 2 == 1 && j % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCasePion));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCaseMur ;
                    casePosY += tailleCasePion ;
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (i % 2 == 1 && j % 2 == 1) {         // petit truc vide
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCaseMur ;
                    casePosY += tailleCaseMur ;
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                }
            }
        }
        //placement pions initiale
        for (int i=0;i<2;i++) {

            if (i == 0) {
                JPanel j = (JPanel)plateauQuoridor.getComponent((8*17)); // colone 8 , ligne 0
                ImageIcon ic = new ImageIcon(new ImageIcon("images/PionNoir.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            } else {
                JPanel j = (JPanel)plateauQuoridor.getComponent((8*17)+16); // colone 8 , ligne 16
                ImageIcon ic = new ImageIcon(new ImageIcon("images/PionRouge.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            }

        }


        //remplissage des garages
        /*beta en dur degeulasse*/
        /*GridLayout gl = new GridLayout(10,1);
        garageMurGauche.setLayout(gl);
        garageMurGauche.setBorder(new EmptyBorder(50,20,50,20));
        gl.setVgap(70);
        for(int i=0; i<10;i++){
            JPanel square = new JPanel(new BorderLayout());
            square.setPreferredSize(new Dimension(50,8));
            square.setBackground(Color.orange);
            garageMurGauche.add(square);
        }*/
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();
        String murOrPion = checkIfMurOrPion(x,y);
        int [] position = checkArrayPosition(x,y);

        if (murOrPion.equals("murHorizontal")) {
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

        if (murOrPion.equals("murVertical")) {
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

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        coordInit = new Coordonnees(x,y);

        if (x >= tailleLargeurGarageMur && x < (tailleIHMLargeur-tailleLargeurGarageMur)) { //cas ou on clique sur un piece , vérification clique sur le plateau quoridor

            Component cmp = layeredPane.getComponentAt(x, y).getComponentAt(x-tailleLargeurGarageMur,y);
            //JPanel jPanelGridBagLayout = JPane
            //System.out.println(cmp);
            JPanel jp = null;

            if (cmp instanceof JPanel) {
                jp = (JPanel) cmp;

                //System.out.println("Panel ok");
                if (jp.getComponents().length == 1) {
                    pion = (JLabel) jp.getComponent(0);
                    //pion.setLocation(e.getX(), e.getY());
                    //pion.setSize(pion.getWidth(), pion.getHeight());
                    Point parentLocation = pion.getParent().getLocation();
                    //System.out.println(parentLocation);
                    xPionAdjustment = parentLocation.x - e.getX();
                    yPionAdjustment = parentLocation.y - e.getY();
                    pion.setLocation(e.getX() + xPionAdjustment + tailleLargeurGarageMur, e.getY() + yPionAdjustment);
                    //pion.setLocation(e.getX() , e.getY());
                    layeredPane.add(pion, JLayeredPane.DRAG_LAYER); //inverser deux niveau de layout // Exception à debug mais marche quand même
                    //layeredPane.add(pion);

                }
            }
        }
        else {
            System.out.println("Clique hors plateau de jeu"); //si deplacement foireux, on refresh
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //pion= null;
        if (pion == null) {
            return;
        }

        int x = e.getX();
        int y = e.getY();
        coordInit = new Coordonnees(x,y);

        //ajout d'un is move OK

        if (x >= tailleLargeurGarageMur && x < (tailleIHMLargeur-tailleLargeurGarageMur)) { //cas ou on clique sur un piece , vérification cloque sur le plateau quoridor
            pion.setVisible(false);
            Component c = plateauQuoridor.findComponentAt(e.getX() - tailleLargeurGarageMur, e.getY());
            System.out.println(e.getX() + "," + e.getY());

            //fonction de reajustement des coordonnées
            //test de coordonnée autorisée
            //fonction pour move dans le controleur

            Container parent = (Container) c;
            parent.add(pion);


            pion.setVisible(true);
        }
        else {

            System.out.println("non autorisé");
            //update();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pion == null) {
            return;
        }
        //pion.setLocation(e.getX(), e.getY());
        pion.setLocation(e.getX() + xPionAdjustment + tailleLargeurGarageMur, e.getY() + yPionAdjustment);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void update() { //voir les arguments à récupérer pour afficher
        plateauQuoridor.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;

        for (int i = 0; i < (taille * 2 - 1); i++) {
            constraints.gridx = i ;//décalage dans le grid bag layout

            for (int j = 0; j < (taille * 2 - 1); j++) {

                constraints.gridy = j ;//décalage dans le grid bag layout

                if (i % 2 == 0 && j % 2 == 0) {         // Case pion
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCasePion));
                    square.setBackground(Color.LIGHT_GRAY);
                    plateauQuoridor.add(square,constraints);
                } else if (i % 2 == 0 && j % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                } else if (i % 2 == 1 && j % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCasePion));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);

                } else if (i % 2 == 1 && j % 2 == 1) {         // petit truc vide
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                }
            }
        }
        //placement pions initiale
        for (int i=0;i<2;i++) {

            if (i == 0) {
                JPanel j = (JPanel)plateauQuoridor.getComponent(8*17); // colone 8 , ligne 0
                ImageIcon ic = new ImageIcon(new ImageIcon("images/PionNoir.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            } else {
                JPanel j = (JPanel)plateauQuoridor.getComponent((8*17)+16); // colone 8 , ligne 0
                ImageIcon ic = new ImageIcon(new ImageIcon("images/PionRouge.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            }

        }
        validate();
        repaint();
    }

    /**
     *
     * @param x position du click en X
     * @param y position du click en Y
     * @return pion, murHorizontal, murVertical croisement
     */
    private String checkIfMurOrPion(int x, int y) {
        int i,j;
        i = boucleCheckPosition(x);
        j = boucleCheckPosition(y);

        if (i%2 == 1 && j%2 == 1 ) {
            return "pion";
        } else if (i%2 == 0 && j%2 == 0) {
            return "croisement";
        } else if (i%2 == 1 && j%2 == 0){
            return "murHorizontal";
        }
        return "murVertical";
    }


    private int[] checkArrayPosition(int x, int y) {
        int values [] = {-1,-1};
        values[0] = boucleCheckPosition(x);
        values[1] = boucleCheckPosition(y);
        return values;
    }

    private int boucleCheckPosition (int p) {
        int i=0;
        while (p>tailleLargeurGarageMur && p < (tailleLargeurGarageMur + taillePlateauQuoridor) ) {
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

    public HashMap<Coordonnees, JPanel> getMapCoordPanelPion() {
        return mapCoordPanelPion;
    }

    public HashMap<Coordonnees, JPanel> getMapCoordPanelMur() {
        return mapCoordPanelMur;
    }


    /**
     * donne le coefficient de la taille du plateau en fonction de la résolution de l'écran
     * @return int
     */
    private int defineCoeffTaille () {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return ((int) dim.getWidth() / 18);
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

}