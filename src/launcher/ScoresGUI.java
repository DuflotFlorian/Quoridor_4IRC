package launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

import static launcher.LauncherGUI.defineAutoTheme; //TODO delete ?

public class ScoresGUI extends JFrame {
    JFrame frameLauncher;
    Color BackgroundColor = new Color(404040);
    LinkedHashMap<String, Integer> topRank;
    LinkedHashMap<String, List<Integer>> rankRatio;

    public ScoresGUI(LinkedHashMap<String, Integer> tr, LinkedHashMap<String, List<Integer>> rr) {
        super();
        this.topRank = tr;
        this.rankRatio = rr;
        frameLauncher = this;
        JPanel main = new JPanel(new GridLayout(2,2));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - this.getPreferredSize().width /2 , (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - this.getPreferredSize().height /2 );
        this.setTitle("Scores Quoridor");



        /*Chargement de l'icone du programme*/
        InputStream iconeURL = getClass().getResourceAsStream("/images/iconQuoridor.png");
        ImageIcon imgIcon = null;
        try {
            imgIcon = new ImageIcon(ImageIO.read(iconeURL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frameLauncher.setIconImage(imgIcon.getImage());

        main.setBackground(this.BackgroundColor);
        this.add(main);

        JLabel titleTopRank = new JLabel("TOP RANK");
        titleTopRank.setForeground(Color.WHITE);
        main.add(titleTopRank);

        JLabel titleRankByPlayed = new JLabel("% VICTOIRES / NOMBRE DE JEUX");
        titleRankByPlayed.setForeground(Color.WHITE);
        main.add(titleRankByPlayed);

        //tableau TopRank
        JTable tableT=new JTable(topRank.size(),2);
        int row=0;
        for(Map.Entry<String,Integer> entry: topRank.entrySet()){
            tableT.setValueAt(entry.getKey(),row,0);
            tableT.setValueAt(entry.getValue(),row,1);
            row++;
        }
        tableT.setBackground(BackgroundColor);
        tableT.setGridColor(BackgroundColor);
        tableT.setForeground(Color.WHITE);
        main.add(tableT);

        //tableau rankRatio
        JTable tableR=new JTable(topRank.size(),3);
        Iterator it = rankRatio.entrySet().iterator();
        row=0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String player = pair.getKey().toString();
            tableR.setValueAt(player,row,0);
            ArrayList a = (ArrayList) pair.getValue();
            tableR.setValueAt(a.get(0)+" %",row,1);
            tableR.setValueAt("["+a.get(1)+" jeux]",row,2);
            row++;
        }

        tableR.setBackground(BackgroundColor);
        tableR.setGridColor(BackgroundColor);
        tableR.setForeground(Color.WHITE);
        tableR.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableR.getColumnModel().getColumn(0).setPreferredWidth(150);
        main.add(tableR);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        defineAutoTheme();
    }

}
