/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.NORTHWEST;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Kevin
 */
public class QuoridorGUI extends JFrame implements MouseListener, MouseMotionListener {

    private JLayeredPane layeredPane;
    private JPanel PlateauQuoridor;
    // private JLabel chessPiece;
    private int xAdjustment;
    private int yAdjustment;
    int coeffTaille;        
    int TailleIHM ;
    int tailleCasePion;
    int tailleCaseMur;
    int Taille;

    public QuoridorGUI(String title, Dimension dim, int Taille, int nbJoueurs) {
        //public ChessGameGUI(String title, ChessGameControlers chessGameControler, Dimension dim) {
        super(title);
        this.Taille = Taille;
        coeffTaille = 100;        
        TailleIHM = Taille * coeffTaille ;
        tailleCasePion = (int) ((0.7 * TailleIHM) / Taille);
        tailleCaseMur = (int) ((0.3 * TailleIHM) / Taille);
        Dimension boardSize = new Dimension(Taille * 100, Taille * 100);
        //int tailleCasePion = (int) ((0.7 * TailleIHM) / Taille);
        //int tailleCaseMur = (int) ((0.3 * TailleIHM) / Taille);
        
        System.out.println(Taille+","+tailleCasePion +","+tailleCaseMur);
        //  Use a Layered Pane for this this application
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize(boardSize);
        layeredPane.addMouseListener(this);
        layeredPane.addMouseMotionListener(this);

        //Add a chess board to the Layered Pane 
        PlateauQuoridor = new JPanel();
        //PlateauQuoridor.setBackground(Color.red);
        layeredPane.add(PlateauQuoridor, JLayeredPane.DEFAULT_LAYER);
        //PlateauQuoridor.setLayout(new GridLayout(Taille*2-1, Taille*2-1));
        PlateauQuoridor.setLayout(new GridBagLayout());
        PlateauQuoridor.setPreferredSize(boardSize);
        PlateauQuoridor.setBounds(0, 0, boardSize.width, boardSize.height);
        //PlateauQuoridor.setSize(100, 100);
        //PlateauQuoridor.setBackground(Color.red);
        int casePosX = 0; // used to set starting gridX position
	int casePosY = 0; // used to set starting gridY position
        
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = NORTHWEST;
        //constraints.gridx = casePosX ;
        //constraints.gridy = casePosY ;
        //constraints.insets = new Insets(0, 30, 40, 0); // AbsPos
        constraints.weightx = 1;
        constraints.weighty = 1;
        
        
        for (int i = 0; i < (Taille * 2 - 1); i++) {
            constraints.gridx = i ;
            
            for (int j = 0; j < (Taille * 2 - 1); j++) {
                
                constraints.gridy = j ;
                if (i % 2 == 0 && j % 2 == 0) {
                    JPanel square = new JPanel(new BorderLayout());
                    //square.setSize(tailleCasePion, tailleCasePion);
                    //square.setSize(tailleCasePion, tailleCasePion);
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = tailleCasePion;
                    constraints.ipady = tailleCasePion;
                    constraints.weightx = 1;
                    constraints.weighty = 1;
                    square.setBackground(Color.LIGHT_GRAY);
                    if (j==0 && i==(Taille+(Taille-1))/2) {
                        JLabel jlabelPionHaut = new JLabel(new ImageIcon("D:\\Documents\\IRC\\S7\\Génie Logiciel\\Quoridor\\Quoridor_4IRC\\src\\images\\PionNoir.png"));
                        //jlabelPionHaut.setSize(20, 20);
                        square.setMaximumSize(new Dimension (20,20));
                        square.add(jlabelPionHaut);
                    }
                    if (j==(2*Taille)-2 && i==(Taille+(Taille-1))/2) {
                        JLabel jlabelPionHaut = new JLabel(new ImageIcon("D:\\Documents\\IRC\\S7\\Génie Logiciel\\Quoridor\\Quoridor_4IRC\\src\\images\\PionRouge.png"));
                        square.setMaximumSize(new Dimension (20,20));
                        square.add(jlabelPionHaut);
                    }
                    
                    PlateauQuoridor.add(square,constraints);
                } else if (i % 2 == 0 && j % 2 == 1) {
                    JPanel square = new JPanel(new BorderLayout());
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    //square.setSize(tailleCasePion, 10);
                    constraints.ipadx = tailleCasePion;
                    constraints.ipady = 10;
                    constraints.weightx = 1;
                    constraints.weighty = 1;
                    square.setBackground(Color.WHITE);
                    //PlateauQuoridor.add(square);
                    PlateauQuoridor.add(square,constraints);
                } else if (i % 2 == 1 && j % 2 == 0) {
                    JPanel square = new JPanel(new BorderLayout());
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = 10;
                    constraints.ipady = tailleCasePion;
                    constraints.weightx = 1;
                    constraints.weighty = 1;
                    square.setBackground(Color.WHITE);
                    PlateauQuoridor.add(square,constraints);
                    //PlateauQuoridor.add(square);
                } else if (i % 2 == 1 && j % 2 == 1) {
                    JPanel square = new JPanel(new BorderLayout());
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = 10;
                    constraints.ipady = 10;
                    square.setBackground(Color.WHITE);
                    //PlateauQuoridor.add(square);
                    PlateauQuoridor.add(square,constraints);
                }
            }
        }
        
        //init en dur à modifier plus tard
        //GridBagLayout gbl = (GridBagLayout) PlateauQuoridor.getLayout();
        //System.out.println(PlateauQuoridor.getLayout());
        //Point pionHaut= gbl.location(0, Taille/2 +1); // pion haut
        //Point pionHaut= gbl.location(0, Taille/2 +1);
        //JPanel jpanelPionHaut = (JPanel) this.getComponentAt(pionHaut);
        //JLabel jlabelPionHaut = new JLabel(new ImageIcon("D:\\Documents\\IRC\\S6\\POO\\projet\\3IRC_4ETI_POO_Projet_e-campus_1516\\3IRC 4ETI POO Projet e-campus 1516\\images\\images\\pionBlancS.png"));
        //System.out.println(gbl.getConstraints());
        //jpanelPionHaut.add(jlabelPionHaut);
        
        //Point pionBas= gbl.location(Taille-1, Taille/2 +1); // pion bas
        //JPanel jpanelPionBas = (JPanel) this.getComponentAt(pionHaut)
        //jpanelPionHaut.add(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //Point x = e.getPoint();
        //Point y = e.getPoint();
        
        Component c = this.findComponentAt(e.getX(), e.getY());
        if (c instanceof JPanel) {
            //case vide
            return;
        }
        //Component parent = c.getParent();
        System.out.println(c);
        //this.update();
        //System.out.println(x+" "+y);
        Point parentLocation = c.getParent().getLocation();
        JLabel pion = (JLabel) c;
        pion.setLocation(e.getX() , e.getY());
            //pion.setSize(pion.getWidth(), pion.getHeight());
            layeredPane.add(pion, JLayeredPane.DRAG_LAYER);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    
    public void update() {

        PlateauQuoridor.removeAll();
        
        int casePosX = 0; // used to set starting gridX position
	int casePosY = 0; // used to set starting gridY position
        
        
        GridBagConstraints constraints = new GridBagConstraints();
        //constraints.anchor = NORTHWEST;
        //constraints.gridx = casePosX ;
        //constraints.gridy = casePosY ;
        //constraints.insets = new Insets(0, 30, 40, 0); // AbsPos
        constraints.weightx = 1;
        constraints.weighty = 1;
        
        for (int i = 0; i < (Taille * 2 - 1); i++) {
            constraints.gridx = i ;
            
            for (int j = 0; j < (Taille * 2 - 1); j++) {
                
                constraints.gridy = j ;
                if (i % 2 == 0 && j % 2 == 0) {
                    JPanel square = new JPanel();
                    //square.setSize(tailleCasePion, tailleCasePion);
                    //square.setSize(tailleCasePion, tailleCasePion);
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = tailleCasePion;
                    constraints.ipady = tailleCasePion;
                    square.setBackground(Color.LIGHT_GRAY);
                    PlateauQuoridor.add(square,constraints);
                } else if (i % 2 == 0 && j % 2 == 1) {
                    JPanel square = new JPanel();
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    //square.setSize(tailleCasePion, 10);
                    constraints.ipadx = tailleCasePion;
                    constraints.ipady = 10;
                    square.setBackground(Color.WHITE);
                    //PlateauQuoridor.add(square);
                    PlateauQuoridor.add(square,constraints);
                } else if (i % 2 == 1 && j % 2 == 0) {
                    JPanel square = new JPanel();
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = 10;
                    constraints.ipady = tailleCasePion;
                    square.setBackground(Color.WHITE);
                    PlateauQuoridor.add(square,constraints);
                    //PlateauQuoridor.add(square);
                } else if (i % 2 == 1 && j % 2 == 1) {
                    JPanel square = new JPanel();
                    //square.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    constraints.ipadx = 10;
                    constraints.ipady = 10;
                    square.setBackground(Color.WHITE);
                    //PlateauQuoridor.add(square);
                    PlateauQuoridor.add(square,constraints);
                }
            }
        }
/*
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            chessBoard.add(square);

            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? Color.DARK_GRAY : Color.white);
            } else {
                square.setBackground(i % 2 == 0 ? Color.white : Color.DARK_GRAY);
            }
        }

        List<PieceIHM> piecesIHM = (List<PieceIHM>) arg;

        for (PieceIHM pieceIHM : piecesIHM) {

            Couleur color = pieceIHM.getCouleur();

            int x = pieceIHM.getX();
            int y = pieceIHM.getY();
            String fileName = ChessImageProvider.getImageFile(pieceIHM.getNamePiece(), color);
            JLabel piece = new JLabel(new ImageIcon(fileName));
            JPanel panel = (JPanel) chessBoard.getComponent(8 * y + x);

            panel.add(piece);
        }
        //affichage des cases menacees
        //showCasesMenaces();
        */
        PlateauQuoridor.revalidate();
        PlateauQuoridor.repaint();
    }

}
