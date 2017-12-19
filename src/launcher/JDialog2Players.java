package launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class JDialog2Players extends JDialog {

    public JDialog2Players(Frame owner) {
        super(owner);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setSize(300,250);
        this.setResizable(false);
        this.setLayout(new BorderLayout());


        /*Ajout des Panels*/
        JPanel panelMain = new JPanel(new GridLayout(2,2));
        panelMain.setBorder(new EmptyBorder(10,10,10,10));
        JPanel panelButtons = new JPanel(new FlowLayout());

        /*Remplissage du panelMain*/
        //TODO passer en flow layout sur les lignes
        JLabel labelJoueur1 = new JLabel("Joueur 1:");
        JLabel labelJoueur2 = new JLabel("Joueur 2:");
        JTextField txtJoueur1 = new JTextField("Joueur 1");
        JTextField txtJoueur2 = new JTextField("Joueur 2");

        panelMain.add(labelJoueur1);
        panelMain.add(txtJoueur1);
        panelMain.add(labelJoueur2);
        panelMain.add(txtJoueur2);

        /*Ajout panelMain au JDialog*/
        this.add(panelMain, BorderLayout.CENTER);

        /*Remplissage du panelBoutton*/
        JButton buttonOK = new JButton("OK");
        JButton buttonCancel = new JButton("Annuler");

        panelButtons.add(buttonOK);
        panelButtons.add(buttonCancel);

        /*Ajout panelButton au JDialog*/
        this.add(panelButtons, BorderLayout.PAGE_END);
    }
}
