package launcher;

import java.awt.Dimension;

import java.util.ArrayList;
import javax.swing.JFrame;
import vue.QuoridorGUI;
import Class.*;

public class LauncherGUI {

    public static void main(String[] args) {
        JFrame frame;
        Dimension dim;
        dim = new Dimension(900, 900);
        frame = new QuoridorGUI("Quoridor", dim ,9);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(600, 10);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}
}
