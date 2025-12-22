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

    private final int buttonX;
    private int buttonY;
    public MainMenu(Frame frame) {
        buttonX = STARTING_X;
        buttonY = STARTING_Y;
        setLayout(null);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        mainMenuInteractions = new Visuals.Interactions.MainMenu(frame);

        createButton("Load", e -> mainMenuInteractions.load());
    }

    public void createButton(String text, ActionListener l) {
        JButton checkAccuracy = new JButton(text);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(text) + 50;
        checkAccuracy.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        checkAccuracy.addActionListener(l);
        add(checkAccuracy);
        buttonY += buttonHeight + BUTTON_GAP;
    }
}
