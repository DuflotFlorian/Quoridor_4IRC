package vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;
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

        int casePosX = 0; // Position sur le grille
        int casePosY = 0; // Position sur la grille


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
        affichePion(new Coordonnees(0,8),Couleur.NOIR);
        affichePion(new Coordonnees(16,8),Couleur.BLANC);
    }


    public enum Case {
        PION, MURHORIZONTAL, MURVERTICAL, CROISEMENT
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX()-tailleLargeurGarageMur;
        int y = e.getY();

        Coordonnees c = getArrayPosition(y,x); //création coord

        if (c.getY()>=0) {  //si on est dans le plateau de jeu
            positionneUnMur(c); //tente de positionner un mur si les conditions sont remplies
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        coordInit = new Coordonnees(x,y);

        if (isInPlateau(x)) { //cas ou on clique sur un piece , vérification clique sur le plateau quoridor

            Component cmp = layeredPane.getComponentAt(x, y).getComponentAt(x-tailleLargeurGarageMur,y);
            JPanel jp;

            if (cmp instanceof JPanel) {
                jp = (JPanel) cmp;
                if (jp.getComponents().length == 1) {
                    pion = (JLabel) jp.getComponent(0);
                    //pion.setLocation(e.getX(), e.getY());
                    //pion.setSize(pion.getWidth(), pion.getHeight());
                    Point parentLocation = pion.getParent().getLocation();
                    xPionAdjustment = parentLocation.x - e.getX();
                    yPionAdjustment = parentLocation.y - e.getY();
                    pion.setLocation(e.getX() + xPionAdjustment + tailleLargeurGarageMur, e.getY() + yPionAdjustment);
                    layeredPane.add(pion, JLayeredPane.DRAG_LAYER);

                }
            }
        }
        else {
            System.out.println("Clique hors plateau de jeu"); //si deplacement foireux, on refresh
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (pion == null) {
            return;
        }

        int x = e.getX()-tailleLargeurGarageMur;
        int y = e.getY();
        coordInit = new Coordonnees(x,y);

        //ajout d'un is move OK
        if (x > 0 && x < (taillePlateauQuoridor)) { //cas ou on clique sur un piece , vérification clique sur le plateau quoridor
            if (checkIfMurOrPion(coordInit) == Case.PION) {
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
                layeredPane.remove(pion);
                update();
            }
        }
        else {
            layeredPane.remove(pion);
            update();
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
        affichePion(new Coordonnees(0,8),Couleur.NOIR);
        affichePion(new Coordonnees(16,8),Couleur.BLANC);

        validate();
        repaint();
    }

    /**
     *
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
     * retourne notre coordonnée sur le plateau
     * @param x
     * @param y
     * @return
     */
    private Coordonnees getArrayPosition(int x, int y) {
        Coordonnees c = new Coordonnees(boucleCheckPosition(x),boucleCheckPosition(y));
        return c;
    }


    /**
     *  retourne une position dans le plateau
     *  soit dans les X, soit dans les Y selon l'appel à cette fonction
     *
     * @param p  la position à trouver
     * @return position
     */
    private int boucleCheckPosition (int p) {
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


    /**
     * affiche un mur à l'emplacement des coordonnées passées
     * horizontal, on part de la gauche puis les 3 cases à droites
     * vertical : on part du haut ; puis les 3 cases dessous
     *
     * @param c Coordonnées
     */
    private void positionneUnMur(Coordonnees c) {

System.out.println(c);
        Case murOrPion = checkIfMurOrPion(c);// vérifie quel est le type du panel (pion, murH,murV, croisemnt)
System.out.println("Mur ou pion :" +murOrPion);


//        Component cmp = layeredPane.findComponentAt(c.getX(),c.getY()); //défini le composant sur lequel on veut positionner un mur
        Component cmp = plateauQuoridor.findComponentAt(c.getX(),c.getY()); //défini le composant sur lequel on veut positionner un mur


System.out.println(cmp.toString());

        if (mapCoordPanelMur.containsValue(cmp)) {
            System.out.println("toto");
        }


        if (murOrPion.equals(Case.MURHORIZONTAL)) {

            //TODO a reprendre avec les jpanel transformer
//            boucleCheckPosition(c.getX());
//            int pos = convertCoordToCell(position.getX(),position.getY());

//            if (position.getX() <= 15 && position.getX()>= 0) {
//
//                JPanel j = (JPanel) plateauQuoridor.getComponent(pos);
//                JPanel k = (JPanel) plateauQuoridor.getComponent(pos + 17);
//                JPanel l = (JPanel) plateauQuoridor.getComponent(pos + 34);
//                if (j.getBackground() != Color.BLUE && k.getBackground() != Color.BLUE && l.getBackground() !=Color.BLUE)
//                {
//                    j.setBackground(Color.BLUE);
//                    k.setBackground(Color.BLUE);
//                    l.setBackground(Color.BLUE);
//                }
//            }
//        }
//
//        if (murOrPion.equals(Case.MURVERTICAL)) {
//            int pos = convertCoordToCell(position.getX(),position.getY());
//            if (position.getY() <= 15 && position.getY() >= 0) {
//                JPanel j = (JPanel) plateauQuoridor.getComponent(pos);
//                JPanel k = (JPanel) plateauQuoridor.getComponent(pos + 1);
//                JPanel l = (JPanel) plateauQuoridor.getComponent(pos + 2);
//                if (j.getBackground() != Color.BLUE && k.getBackground() != Color.BLUE && l.getBackground() !=Color.BLUE)
//                {
//                    j.setBackground(Color.BLUE);
//                    k.setBackground(Color.BLUE);
//                    l.setBackground(Color.BLUE);
//                }
//            }
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
}