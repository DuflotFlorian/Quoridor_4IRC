package launcher;

import java.awt.Dimension;

import java.util.ArrayList;
import javax.swing.*;

import vue.QuoridorGUI;
import Class.*;

public class LauncherGUI {

    public static void main(String[] args) {
        JFrame frame;
        Dimension dim;
        int coeffTaille = 100;
        frame = new QuoridorGUI("Quoridor", coeffTaille ,9);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(350, 10);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
	}
}
