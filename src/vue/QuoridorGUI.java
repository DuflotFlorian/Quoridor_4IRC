package vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Class.Coordonnees;
import Class.Couleur;

/**
 *
 * @author Kevin & ludo
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
    private String urlImages = "";
    private ArrayList<JPanel> arrayPanelCote ;

    // Coordonnées de la position initiale de la pièce déplacée
    private Coordonnees coordInit;

    /**
     *
     * @param title titre de la fenêtre
     * @param taille nombre de case
     */
    public QuoridorGUI(String title, int taille) {
        super(title);

        pion=null;
        //Creation des maps murs + pions
        mapCoordPanelPion = new HashMap<Coordonnees,JPanel>();
        mapCoordPanelMur = new HashMap<Coordonnees,JPanel>();
        arrayPanelCote = new ArrayList<JPanel>();
        this.coeffTaille = defineCoeffTaille();

        this.taille=taille;
        tailleCasePion = (int) ((0.85 * coeffTaille));
        tailleCaseMur = (int) ((0.15 * coeffTaille));
        tailleLargeurGarageMur = (int) (2.5 * coeffTaille);
        //TODO ajouter une barre en haut avec les informations qui vont bien
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
        //garageMurGauche.setBackground(Color.GREEN);
        garageMurGauche.setBackground(Color.GRAY);

        //Creation du stockage à murs gauche
        garageMurDroit = new JPanel();
        layeredPane.add(garageMurDroit, JLayeredPane.DEFAULT_LAYER);
        garageMurDroit.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        garageMurDroit.setBounds(taillePlateauQuoridor+tailleLargeurGarageMur, 0, tailleLargeurGarageMur, tailleIHMHauteur);
        //garageMurDroit.setBackground(Color.BLUE);
        garageMurDroit.setBackground(Color.GRAY);

        //Ajout du plateau de jeu
        plateauQuoridor = new JPanel();
        layeredPane.add(plateauQuoridor, JLayeredPane.DEFAULT_LAYER);

        //GridBagLayout pour grille personnalisée
        plateauQuoridor.setLayout(new GridBagLayout());

        //plateauQuoridor.setPreferredSize(dim);
        plateauQuoridor.setPreferredSize(new Dimension(taillePlateauQuoridor, taillePlateauQuoridor));
        plateauQuoridor.setBounds(tailleLargeurGarageMur, 0, taillePlateauQuoridor, taillePlateauQuoridor);

        int casePosX = 0; // Position sur le grille
        int casePosY = 0; // Position sur la grille


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
                    //ajout à la map
                    casePosX += tailleCasePion ;
                    casePosY += tailleCasePion ;
                    mapCoordPanelPion.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 0 && i % 2 == 1) {         // Case mur horizontal
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCasePion,tailleCaseMur));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCasePion ;
                    casePosY += tailleCaseMur ;
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 0) {         // Case mur vertical
                    JPanel square = new JPanel(new BorderLayout());
                    square.setPreferredSize(new Dimension(tailleCaseMur,tailleCasePion));
                    square.setBackground(Color.WHITE);
                    plateauQuoridor.add(square,constraints);
                    //ajout à la map
                    casePosX += tailleCaseMur ;
                    casePosY += tailleCasePion ;
                    mapCoordPanelMur.put(new Coordonnees(i,j),square);
                } else if (j % 2 == 1 && i % 2 == 1) {         // petit truc vide
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

        //plateauQuoridor.setBorder(BorderFactory.createLineBorder(Color.BLACK ,2));
        //placement pions initiale
        affichePion(new Coordonnees(0,8),Couleur.NOIR);
        affichePion(new Coordonnees(16,8),Couleur.BLANC);

        remplissagePanelCote();
    }


    public enum Case {
        PION, MURHORIZONTAL, MURVERTICAL, CROISEMENT
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX()-tailleLargeurGarageMur;
        int y = e.getY();

        positionneUnMur(x,y);



    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        JPanel jp;
        coordInit = new Coordonnees(x,y);
        Component cmp =  layeredPane.findComponentAt(x,y);
        if (mapCoordPanelPion.containsValue(cmp)) {
            jp = (JPanel) cmp;
        }
        else if (mapCoordPanelPion.containsValue(cmp.getParent())) {
            jp = (JPanel) cmp.getParent();
        } else {
            return;
        }


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
        coordInit = new Coordonnees(x,y);

                pion.setVisible(false);
                Component cmp = findComponentAt(x , y);
                if (mapCoordPanelPion.containsValue(cmp)) {
                    Container parent = (Container) cmp;
                    parent.add(pion);
                    pion.setVisible(true);
                    //update();
                }
                else {
                    //update();
                }
                pion=null;
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
        pion.setLocation(e.getX() + xPionAdjustment + plateauQuoridor.getX(), e.getY() + yPionAdjustment + plateauQuoridor.getY());
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
        affichePion(new Coordonnees(0,8),Couleur.NOIR);
        affichePion(new Coordonnees(16,8),Couleur.BLANC);

        validate();
        repaint();
    }

    /**
     *
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

    /**
     * Permet de passer au dessus des problématiques de PATH et de windows ou linux pour les séparateurs
     * le File.separator permet dez choisir \ sous windows,  / sous nux
     * @param coord
     * @param c
     */
    private void affichePion(Coordonnees coord, Couleur c) {
        java.net.URL imageURL = QuoridorGUI.class.getResource("images" + File.separator+ "Pion" + c.toString() + ".png");
        JPanel j = (JPanel) plateauQuoridor.getComponent((coord.getY() * 17)+coord.getX()); // colone 8 , ligne 0
        JLabel piece = new JLabel(new ImageIcon(imageURL));
        j.add(piece);
    }


    private void remplissagePanelCote (/*TODO passer la liste des joueurs IHM*/) {
        /*TODO Count nombre de joueurs pour les afficher sur les cotés*/
        int nbPlayer = 4 ; //Utiliser la liste des joueurs au lieu de la valmeur en dur
        int nbRowInColumn = 0;


        garageMurDroit.setBackground(Color.PINK);
        garageMurGauche.setBackground(Color.ORANGE);

        JPanel [][] tabPanelGauche = null;
        JPanel [][] tabPanelDroit = null;

        if (nbPlayer == 2) {
            nbRowInColumn = 3;

        } else if (nbPlayer == 4) {
            nbRowInColumn = 5;
        }

        garageMurGauche.setLayout(new GridLayout(nbRowInColumn,1));
        garageMurDroit.setLayout(new GridLayout(nbRowInColumn,1));

        tabPanelGauche = new JPanel[nbRowInColumn][1];
        for (int i =0 ; i<nbRowInColumn ; i++) {
            tabPanelGauche[i][0] = new JPanel();
            tabPanelGauche[i][0].setBackground(new Color(404040));
            garageMurGauche.add(tabPanelGauche[i][0]);
        }

        tabPanelDroit = new JPanel[nbRowInColumn][1];
        for (int i =0 ; i<nbRowInColumn ; i++) {
            tabPanelDroit[i][0] = new JPanel();
            tabPanelDroit[i][0].setBackground(new Color(404040));
            garageMurDroit.add(tabPanelDroit[i][0]);
        }

        if(nbPlayer==2) {
            createLabelCote(tabPanelGauche[1][0],1);
            createLabelCote(tabPanelDroit[1][0],2);

        } else if (nbPlayer == 4){
            createLabelCote(tabPanelGauche[1][0],1);
            createLabelCote(tabPanelDroit[1][0],2);
            createLabelCote(tabPanelGauche[3][0],3);
            createLabelCote(tabPanelDroit[3][0],4);
        }
    }

    private void createLabelCote (JPanel jp, int numJoueur) {
        /*TODO GetCurrentPlayer*/
        int numCurrentPlayer = 1;
        if(numJoueur == numCurrentPlayer) {
            jp.setBorder(BorderFactory.createLineBorder(Color.GREEN ,coeffTaille/12));
        }

        jp.setBackground(new Color(0x808080));
        jp.setLayout(new GridLayout(2,1));
        JLabel jl1 =  new JLabel("Joueur "+numJoueur);
        jl1.setForeground(Color.WHITE);
        jl1.setHorizontalAlignment(JLabel.CENTER);
        jl1.setFont(new Font("Impact", Font.PLAIN, coeffTaille/3));
        jp.add(jl1);
        JLabel jl2 = new JLabel(""+8);
        jl2.setForeground(Color.WHITE);
        jl2.setHorizontalAlignment(JLabel.CENTER);
        jl2.setFont(new Font("Impact", Font.PLAIN, coeffTaille/2));
        jp.add(jl2);
    }
}