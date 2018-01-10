package Class;

import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public enum QuoridorColor {

    BLUE("#0FB1CB", "Joueur BLEU"),
    RED("#CA2B0F", "Joueur ROUGE"),
    GREEN("#90D30D", "Joueur VERT"),
    YELLOW("#DFE219","Joueur JAUNE");

    private Color color_box;
    private JLabel playerName;


    QuoridorColor(String c, String name) {
        StyleSheet s = new StyleSheet();

        this.color_box  = s.stringToColor(c);
        this.playerName = new JLabel(name);
        this.playerName.setForeground(this.color_box);
        this.playerName.setHorizontalAlignment(JLabel.CENTER);
    }


    public Color getColor_box() {
        return color_box;
    }

    public void setColor_box(Color color_box) {
        this.color_box = color_box;
    }

    public JLabel getPlayerName() {
        return playerName;
    }

    public void setPlayerName(JLabel playerName) {
        this.playerName = playerName;
    }
}




