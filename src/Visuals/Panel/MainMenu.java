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

        puzzleCreatorButton("New Puzzle", 7, (x, y) -> mainMenuInteractions.newPuzzle(x, y));
        puzzleCreatorButton("Generate Puzzle", 10, (x, y) -> mainMenuInteractions.generatePuzzle(x, y));
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
        JButton button = new JButton(text);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(text) + 50;
        button.setBounds(componentX, componentY, buttonWidth, buttonHeight);
        button.addActionListener(l);
        add(button);
        componentY += buttonHeight + BUTTON_GAP;
    }
    @FunctionalInterface
    interface PuzzleCreator {
        void create(int x, int y);
    }
    public void puzzleCreatorButton(String text, int defaultNumber, PuzzleCreator puzzleCreator) {
        JTextField field1 = new JTextField(Integer.toString(defaultNumber), 10);
        JTextField field2 = new JTextField(Integer.toString(defaultNumber), 10);
        JLabel label = new JLabel("x");
        JButton button = new JButton(text);

        int fieldWidth = getFontMetrics(getFont()).stringWidth("20") + 5;
        int labelWidth = getFontMetrics(getFont()).stringWidth("x");
        int buttonWidth = getFontMetrics(getFont()).stringWidth(text) + 50;
        int buttonHeight = getFont().getSize() + 2;

        int currentX = componentX;

        field1.setBounds(currentX, componentY, fieldWidth, buttonHeight);
        currentX += fieldWidth + BUTTON_GAP;
        label.setBounds(currentX, componentY, labelWidth, buttonHeight);
        currentX += labelWidth + BUTTON_GAP;
        field2.setBounds(currentX, componentY, fieldWidth, buttonHeight);
        currentX += fieldWidth + BUTTON_GAP;
        button.setBounds(currentX, componentY, buttonWidth, buttonHeight);

        button.addActionListener(e -> {
            try {
                int text1 = Integer.parseInt(field1.getText());
                int text2 = Integer.parseInt(field2.getText());
                if (text1 > 0 && text1 < 100 && text2 > 0 && text2 < 100) {
                    puzzleCreator.create(text1, text2);
                }
            } catch (Exception ignored) {

            }
        });

        add(field1);
        add(label);
        add(field2);
        add(button);
        componentY += buttonHeight + BUTTON_GAP;
    }
}
