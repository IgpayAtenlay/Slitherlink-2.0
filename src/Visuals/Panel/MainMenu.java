package Visuals.Panel;

import Visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    public Visuals.Interactions.MainMenu mainMenuInteractions;
    private static final int STARTING_X = 20;
    private static final int STARTING_Y = 20;
    private static final int BUTTON_GAP = 5;

    private final int componentX;
    private int componentY;
    public MainMenu(Frame frame) {
        componentX = STARTING_X;
        componentY = STARTING_Y;
        setLayout(null);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        mainMenuInteractions = new Visuals.Interactions.MainMenu(frame);

        createText("Main Menu");

        createButton("New 20x20 Puzzle", e -> mainMenuInteractions.newPuzzle(20, 20));
        createButton("Generate Puzzle", e -> mainMenuInteractions.generatePuzzle(20, 20));
        createButton("Load", e -> mainMenuInteractions.load());
        createButton("Import PDFs", e -> mainMenuInteractions.importPDFs());
    }

    public void createText(String text) {
        JLabel heading = new JLabel(text);
        heading.setBounds(componentX, componentY, 100, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        componentY += 40;
        add(heading);
    }

    public void createButton(String text, ActionListener l) {
        JButton checkAccuracy = new JButton(text);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(text) + 50;
        checkAccuracy.setBounds(componentX, componentY, buttonWidth, buttonHeight);
        checkAccuracy.addActionListener(l);
        add(checkAccuracy);
        componentY += buttonHeight + BUTTON_GAP;
    }
}
