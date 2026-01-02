package Visuals;

import Visuals.Panel.MainMenu;

import javax.swing.*;

public class Frame extends JFrame {
    private JPanel panel = null;

    public Frame() {
        super("Slitherlink Solver 3.0");
        switchPanel(new MainMenu(this));
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void switchPanel(JPanel panel) {
        if (this.panel != null) {
            remove(this.panel);
        }
        this.panel = panel;
        add(this.panel);
        revalidate();
    }
}
