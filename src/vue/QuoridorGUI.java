package vue;

import java.awt.*;

import static java.awt.GridBagConstraints.NORTHWEST;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.Border;
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

    // Coordonnées de la position initiale de la pièce déplacée
    private Coordonnees coordInit;

    public QuoridorGUI(String title, int coeffTaille, int taille) {
        super(title);
        pion=null;
        //Creation des maps murs + pions
        mapCoordPanelPion = new HashMap<Coordonnees,JPanel>();
        mapCoordPanelMur = new HashMap<Coordonnees,JPanel>();

        this.taille=taille;
        tailleCasePion = (int) ((0.85 * coeffTaille));
        tailleCaseMur = (int) ((0.15 * coeffTaille));
        tailleLargeurGarageMur = (int) (2.5 * coeffTaille);

        tailleIHMLargeur = taille * tailleCasePion + (taille-1)*tailleCaseMur + 2 * tailleLargeurGarageMur;
        tailleIHMHauteur = taille * tailleCasePion + (taille-1)*tailleCaseMur;
        //Definition du layout general
        //this.setLayout(new BorderLayout());

        //Definition de la taille general de la frame
        this.dim = new Dimension(tailleIHMLargeur,tailleIHMHauteur);
        System.out.println(taille+","+tailleCasePion +","+tailleCaseMur);

        //Creation du gridLayer genral global pour la fenetre
        //  LayeredPane pour ajouter DRAG_LAYER
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(dim);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Creation d'un BorderLayout pour les 3 zones principales (stockage mur gauche , plateau , stockage mur droit)
        layeredPane.setLayout(new BorderLayout());

        //Creation du stockage à murs gauche
        garageMurGauche = new JPanel();
        layeredPane.add(garageMurGauche, BorderLayout.WEST ,JLayeredPane.DEFAULT_LAYER);
        garageMurGauche.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        //plateauQuoridor.setBounds(0, 0, dim.width, dim.height);
        garageMurGauche.setBackground(Color.GREEN);

        //Creation du stockage à murs gauche
        garageMurDroit = new JPanel();
        layeredPane.add(garageMurDroit, BorderLayout.EAST ,JLayeredPane.DEFAULT_LAYER);
        garageMurDroit.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        //plateauQuoridor.setBounds(0, 0, dim.width, dim.height);
        garageMurDroit.setBackground(Color.BLUE);

        //Ajout du plateau de jeu
        plateauQuoridor = new JPanel();
        layeredPane.add(plateauQuoridor, BorderLayout.CENTER, JLayeredPane.DEFAULT_LAYER);
        //GridBagLayout pour grille personnalisée
        plateauQuoridor.setLayout(new GridBagLayout());
        //plateauQuoridor.setPreferredSize(dim);
        plateauQuoridor.setPreferredSize(new Dimension(tailleIHMLargeur - 2* tailleLargeurGarageMur , tailleIHMHauteur));
        //plateauQuoridor.setBounds(tailleLargeurGarageMur, 0, tailleIHMLargeur, tailleIHMHauteur);

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
        for (int i=0;i<2;i++) {

            if (i == 0) {
                JPanel j = (JPanel)plateauQuoridor.getComponent(8*17); // colone 8 , ligne 0
                ImageIcon ic = new ImageIcon(new ImageIcon("src/images/PionNoir.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            } else {
                JPanel j = (JPanel)plateauQuoridor.getComponent((8*17)+16); // colone 8 , ligne 16
                ImageIcon ic = new ImageIcon(new ImageIcon("src/images/PionRouge.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //System.out.println(e.getX()+"," + e.getY());
        //JPanel jp = (JPanel) plateauQuoridor.getComponentAt(e.getX(),e.getY());
       //System.out.println(jp);
        //update();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        coordInit = new Coordonnees(x,y);

        if (x >= tailleLargeurGarageMur && x < (tailleIHMLargeur-tailleLargeurGarageMur)) { //cas ou on clique sur un piece , vérification cloque sur le plateau quoridor

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
                    //layeredPane.add(pion, JLayeredPane.DRAG_LAYER); //inverser deux niveau de layout // Exception à debug mais marche quand même
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
        pion.setLocation(e.getX(), e.getY());
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
                ImageIcon ic = new ImageIcon(new ImageIcon("src/images/PionNoir.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            } else {
                JPanel j = (JPanel)plateauQuoridor.getComponent((8*17)+16); // colone 8 , ligne 0
                ImageIcon ic = new ImageIcon(new ImageIcon("src/images/PionRouge.png").getImage().getScaledInstance(tailleCasePion,tailleCasePion,Image.SCALE_DEFAULT));
                JLabel piece = new JLabel(ic);

                j.add(piece);
            }

        }
        revalidate();
        repaint();
    }

    public HashMap<Coordonnees, JPanel> getMapCoordPanelPion() {
        return mapCoordPanelPion;
    }

    public HashMap<Coordonnees, JPanel> getMapCoordPanelMur() {
        return mapCoordPanelMur;
    }
}