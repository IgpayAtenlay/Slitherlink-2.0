package Visuals;

import Memory.MemorySet;

import javax.swing.*;

public class Frame extends JFrame {
    MemorySet memorySet;
    Panel panel;

    public Frame(MemorySet memorySet) {
        super("Slitherlink Solver 3.0");
        this.memorySet = memorySet;
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        panel = new Panel(memorySet, this);
        add(panel);
    }
}
