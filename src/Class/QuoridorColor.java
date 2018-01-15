package Class;

import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public enum QuoridorColor {

    BLUE("#0FB1CB", "Joueur BLEU"),
    RED("#CA2B0F", "Joueur ROUGE"),
    GREEN("#90D30D", "Joueur VERT"),
    YELLOW("#DFE219","Joueur JAUNE");

    private Color colorBox;
    private JLabel JLabelPlayerName;


    QuoridorColor(String c, String name) {
        StyleSheet s = new StyleSheet();

        this.colorBox  = s.stringToColor(c);
        this.JLabelPlayerName = new JLabel(name);
        this.JLabelPlayerName.setForeground(this.colorBox);
        this.JLabelPlayerName.setHorizontalAlignment(JLabel.CENTER);
    }


    public Color getColorBox() {
        return colorBox;
    }

    public void setColorBox(Color colorBox) {
        this.colorBox = colorBox;
    }

    public JLabel getJLabelPlayerName() {
        return JLabelPlayerName;
    }

    public void setJLabelPlayerName(JLabel playerName) {
        this.JLabelPlayerName = playerName;
    }
}




