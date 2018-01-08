package vue;

import java.awt.*;
import java.awt.event.*;
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
    private  Color couleurMur;

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
        this.couleurMur = new Color(404040);
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

        //Creation du stockage à murs gauche
        garageMurDroit = new JPanel();
        layeredPane.add(garageMurDroit, JLayeredPane.DEFAULT_LAYER);
        garageMurDroit.setPreferredSize(new Dimension(tailleLargeurGarageMur , tailleIHMHauteur));
        garageMurDroit.setBounds(taillePlateauQuoridor+tailleLargeurGarageMur, 0, tailleLargeurGarageMur, tailleIHMHauteur);

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
        int x = e.getX();
        int y = e.getY();

        Component cmp = layeredPane.findComponentAt(x, y);
        if(mapCoordPanelMur.containsValue(cmp)){
            JPanel jp = (JPanel) cmp;
            coordFinal = pixelToCell(jp);
            boolean b;
            b = quoridorGameController.putWall(coordFinal);
            if(b){
                positionneUnMur(coordFinal , new Coordonnees(x-tailleLargeurGarageMur ,y));
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
            if(pieceIHM.getNamePiece().equals("Pion")) {
                String url = "/vue/images/Pion" + pieceIHM.getCouleur().toString() + ".png";
                java.net.URL s = QuoridorGUI.class.getResource(url);
                JLabel piece = new JLabel();
                piece.setIcon(new ImageIcon(new ImageIcon(s).getImage().getScaledInstance(tailleCasePion, tailleCasePion, Image.SCALE_SMOOTH)));
                JPanel panel = (JPanel) plateauQuoridor.getComponent((pieceIHM.getCoordonnees().getY() * 17) + pieceIHM.getCoordonnees().getX());
                mapPanelPiece.put(piece, pieceIHM);
                panel.removeAll();
                panel.add(piece);
            }
        }

        remplissagePanelCote();
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
        int hauteur = (int)(dim.getHeight()-tailleIHMHauteur)/2 -coeffTaille/2;
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
     * Avant d'appeler cette méthode la vérification et le placement coté model/controller doivennt être faites
     * @param cJeu, cInterface
     */
    private void positionneUnMur(Coordonnees cJeu, Coordonnees cInterface) {
        if(Mur.isWallBeHorizontal(cJeu)) {
            colorisePanelMur(this.mapCoordPanelMur.get(cJeu));
            colorisePanelMur(this.mapCoordPanelMur.get(new Coordonnees(cJeu.getX(), cJeu.getY()+1)));
            colorisePanelMur(this.mapCoordPanelMur.get(new Coordonnees(cJeu.getX(), cJeu.getY()+2)));
        } else {
            colorisePanelMur(this.mapCoordPanelMur.get(cJeu));
            colorisePanelMur(this.mapCoordPanelMur.get(new Coordonnees(cJeu.getX()+1, cJeu.getY())));
            colorisePanelMur(this.mapCoordPanelMur.get(new Coordonnees(cJeu.getX()+2, cJeu.getY())));
        }
    }

    private void colorisePanelMur(JPanel cellule) {
        cellule.setBackground(couleurMur); //colorise la case courante
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

    private void remplissagePanelCote () {
        garageMurDroit.removeAll();
        garageMurGauche.removeAll();
        int nbPlayer = quoridorGameController.listPlayer().size();
        int nbRowInColumn = 0; //Variable qui permet de savoir combien de panel sont mis dans les panels sur les cotés
        //Pour 2 joueurs, 3 panel et on remplis celui du milieu
        //Pour 4 joueurs,  panel et on remplis le 2ème et le 4ème


        JPanel [][] tabPanelGauche ;
        JPanel [][] tabPanelDroit ;

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
            tabPanelGauche[i][0].setBackground(new Color(404040)); //Bleu-Nuit
            garageMurGauche.add(tabPanelGauche[i][0]);
        }

        tabPanelDroit = new JPanel[nbRowInColumn][1];
        for (int i =0 ; i<nbRowInColumn ; i++) {
            tabPanelDroit[i][0] = new JPanel();
            tabPanelDroit[i][0].setBackground(new Color(404040)); //Bleu-Nuit
            garageMurDroit.add(tabPanelDroit[i][0]);
        }

        if(nbPlayer==2) {
            createLabelCote(tabPanelGauche[1][0],1); //Joueur 1
            createLabelCote(tabPanelDroit[1][0],2); //Joueur 2

        } else if (nbPlayer == 4){
            createLabelCote(tabPanelGauche[1][0],1); //Joueur 1
            createLabelCote(tabPanelDroit[1][0],2); //Joueur 2
            createLabelCote(tabPanelGauche[3][0],3); //Joueur 3
            createLabelCote(tabPanelDroit[3][0],4); //Joueur 4
        }
    }

    private void createLabelCote (JPanel jp, int numJoueur) {
        int numCurrentPlayer =  quoridorGameController.getIdCurrentPlayer(); //Numéro du joueur courant

        if(numJoueur == numCurrentPlayer+1) {
            jp.setBorder(BorderFactory.createLineBorder(Color.GREEN ,coeffTaille/12));
        }
        Color c = null;
        //Adapté pour 2 joueurs, rajouter les conditions lors du passage à 4 joueurs
        if(quoridorGameController.getPlayerColor(numJoueur-1).equals(Couleur.BLEU)) {
            c = new Color(0x8CC6D7); //Bleu pale
        } else if (quoridorGameController.getPlayerColor(numJoueur-1).equals(Couleur.ROUGE)) {
            c = new Color(0xDB0B32); //Proche Rouge Pion
        }

        jp.setBackground(new Color(0x808080)); //gris foncé
        jp.setLayout(new GridLayout(2,1));
        JLabel jl1 =  new JLabel("Joueur "+quoridorGameController.getPlayerColor(numJoueur-1));
        jl1.setForeground(c);
        jl1.setHorizontalAlignment(JLabel.CENTER);
        jl1.setFont(new Font("Impact", Font.PLAIN, coeffTaille/3));
        jp.add(jl1);
        JLabel jl2 = new JLabel(""+quoridorGameController.getPlayerWallRemaining(numJoueur-1));
        jl2.setForeground(c);
        jl2.setHorizontalAlignment(JLabel.CENTER);
        jl2.setFont(new Font("Impact", Font.PLAIN, coeffTaille/2));
        jp.add(jl2);
    }
}