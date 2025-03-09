package Visuals;

import Memory.MemorySet;

import javax.swing.*;

public class Frame extends JFrame {
    private MemorySet memorySet;
    private Panel panel;

    public Frame(MemorySet memorySet) {
        super("Slitherlink Solver 3.0");
        this.memorySet = memorySet;
        this.panel = new Panel(memorySet, this);
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(panel);
    }
}
