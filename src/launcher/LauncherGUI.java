package launcher;

import java.awt.*;

import java.util.ArrayList;
import javax.swing.*;

import vue.QuoridorGUI;
import Class.*;

public class LauncherGUI {

    public static void main(String[] args) {
        JFrame frame;
        Dimension dim;
        frame = new QuoridorGUI("Quoridor", 9);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}


}
