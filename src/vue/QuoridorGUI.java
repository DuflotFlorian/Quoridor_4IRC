package vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
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
    private Color couleurMur;

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
        this.couleurMur = new Color(404040);
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

        //placement pions initiale
        affichePion(new Coordonnees(0,8),Couleur.BLEU);
        affichePion(new Coordonnees(16,8),Couleur.ROUGE);

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
                    update();
                }
                else {
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
        return ((int) dim.getWidth() / 18); //18 est un rapport taille/ecran    /!\ attention, /2 pour face de test sur double écran ludo, à virer en prod
    }

    /**
     *
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

        Case murOrPion = checkIfMurOrPion(c);// vérifie quel est le type du panel (pion, murH,murV, croisemnt)
        Component celluleGaucheOuHaut = mapCoordPanelMur.get(c); // cellule à horizontale ou verticale à coloriser
        Component celluleCentre, celluleDroiteOuBas; // cellules adjacentes

        if (murOrPion.equals(Case.MURHORIZONTAL)) {
            if (c.getY() <= 14 && c.getY()>= 0) {
                    // détermine les cases horizontale à côté du composant cliqué
                celluleCentre = mapCoordPanelMur.get(new Coordonnees(c.getX(),c.getY()+1));
                celluleDroiteOuBas = mapCoordPanelMur.get(new Coordonnees(c.getX(),c.getY()+2));
                if (celluleGaucheOuHaut.getBackground() != couleurMur && celluleCentre.getBackground() != couleurMur && celluleDroiteOuBas.getBackground() !=couleurMur)
                {
                    colorisePanelMur(celluleGaucheOuHaut, celluleCentre, celluleDroiteOuBas);
                }
            }
        }

        if (murOrPion.equals(Case.MURVERTICAL)) {
            if (c.getX() <= 14 && c.getX()>= 0) {
                // détermine les cases horizontale à côté du composant cliqué
                celluleCentre = mapCoordPanelMur.get(new Coordonnees(c.getX()+1,c.getY()));
                celluleDroiteOuBas = mapCoordPanelMur.get(new Coordonnees(c.getX()+2,c.getY()));
                if (celluleGaucheOuHaut.getBackground() != couleurMur && celluleCentre.getBackground() != couleurMur && celluleDroiteOuBas.getBackground() !=couleurMur)
                {
                    colorisePanelMur(celluleGaucheOuHaut, celluleCentre, celluleDroiteOuBas);
                }
            }
        }
    }

    /**
     * fonction colorisant les Jpanel des cellules recevant un mur
     * @param celluleGaucheOuHaut
     * @param celluleCentre
     * @param celluleDroiteOuBas
     */
    private void colorisePanelMur(Component celluleGaucheOuHaut, Component celluleCentre, Component celluleDroiteOuBas) {
        JPanel j = (JPanel) celluleGaucheOuHaut; //cast en Jpanel
        JPanel k = (JPanel) celluleCentre;
        JPanel l = (JPanel) celluleDroiteOuBas;

        j.setBackground(couleurMur); //colorise la case courante
        k.setBackground(couleurMur);
        l.setBackground(couleurMur);
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