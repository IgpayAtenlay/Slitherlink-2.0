package Visuals;

import Visuals.Panel.MainMenu;

import javax.swing.*;

public class Frame extends JFrame {
    private JPanel panel;

    public Frame() {
        super("Slitherlink Solver 3.0");
        this.panel = new MainMenu(this);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(panel);
    }

    public void switchPanel(JPanel panel) {
        remove(this.panel);
        this.panel = panel;
        add(this.panel);
        revalidate();
    }
}
