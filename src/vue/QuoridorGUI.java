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
    // private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;
    private Dimension dim;
    private int tailleCasePion;
    private int tailleCaseMur;
    private int tailleIHM;
    private int taille;
    private HashMap<Coordonnees,JPanel> mapCoordPanelPion ;
    private HashMap<Coordonnees,JPanel> mapCoordPanelMur ;
    private JLabel pion;

    public QuoridorGUI(String title, int coeffTaille, int taille) {
        super(title);
        pion=null;
        //Creation des maps murs + pions
        mapCoordPanelPion = new HashMap<Coordonnees,JPanel>();
        mapCoordPanelMur = new HashMap<Coordonnees,JPanel>();

        this.taille=taille;
        tailleCasePion = (int) ((0.85 * coeffTaille));
        tailleCaseMur = (int) ((0.15 * coeffTaille));

        tailleIHM = taille * tailleCasePion + (taille-1)*tailleCaseMur ;

        this.dim = new Dimension(tailleIHM,tailleIHM);

        //  LayeredPane pour ajouter DRAG_LAYER
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(dim);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Ajout du plateau de jeu
        plateauQuoridor = new JPanel();
        layeredPane.add(plateauQuoridor, JLayeredPane.DEFAULT_LAYER);
        //GridBagLayout pour grille personnalisée
        plateauQuoridor.setLayout(new GridBagLayout());
        plateauQuoridor.setPreferredSize(dim);
        plateauQuoridor.setBounds(0, 0, dim.width, dim.height);

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

        JPanel jp = (JPanel) plateauQuoridor.getComponentAt(x,y);
        if(jp.getComponents().length == 1) {
            pion = (JLabel)  jp.getComponent(0);
            //pion.setLocation(e.getX(), e.getY());
            //pion.setSize(pion.getWidth(), pion.getHeight());
            layeredPane.add(pion, JLayeredPane.DRAG_LAYER);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pion= null;
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
        while (p>0 && p < tailleIHM) {
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


}