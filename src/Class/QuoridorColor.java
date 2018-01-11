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
    private JLabel playerName;


    QuoridorColor(String c, String name) {
        StyleSheet s = new StyleSheet();

        this.colorBox  = s.stringToColor(c);
        this.playerName = new JLabel(name);
        this.playerName.setForeground(this.colorBox);
        this.playerName.setHorizontalAlignment(JLabel.CENTER);
    }


    public Color getColorBox() {
        return colorBox;
    }

    public void setColorBox(Color colorBox) {
        this.colorBox = colorBox;
    }

    public JLabel getPlayerName() {
        return playerName;
    }

    public void setPlayerName(JLabel playerName) {
        this.playerName = playerName;
    }
}




