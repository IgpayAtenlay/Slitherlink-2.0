package Visuals;

import Enums.*;
import Memory.MemorySet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Panel extends JPanel {
    private final MemorySet memorySet;
    public Interaction interaction;
    private final Frame frame;
    private boolean checkAccuracy;
    private static final int DOT_DIAMETER = 6;
    private static final int STARTING_X = 20;
    private static final int STARTING_Y = 20;
    private static final int LINE_SIZE = 30;
    private static final int LINE_WIDTH = 2;
    private static final double HEIGHT_OFFSET = 2.3;
    private static final Color CORRECT_COLOR = Color.GREEN;
    private static final Color INCORRECT_COLOR = Color.RED;
    private static final int BUTTON_GAP = 5;

    int buttonX;
    int buttonY;

    public Panel(MemorySet memorySet, Frame frame) {
        this.memorySet = memorySet;
        this.frame = frame;
        this.interaction = new Interaction(memorySet, this);
        buttonX = STARTING_X * 2 + memorySet.getVisible().getXSize() * LINE_SIZE;
        buttonY = STARTING_Y;
        setLayout(null);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                interaction.click(e);
            }
        });

        createButton("Check Accuracy", e -> interaction.checkAccuracy());
        createButton("Autosolve - testing only!", e -> interaction.autoSolve());
        createButton("Check for Errors - testing only!", e-> interaction.checkForErrors());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(LINE_WIDTH));

        drawHighlights(g2d);
        drawNumbers(g2d);
        drawLines(g2d);
        drawDiagonals(g2d);
        drawDots(g2d);

    }

    private void drawDots(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize() + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize() + 1; x++) {
                g.fillOval(getDotCoords(x, y)[0] - DOT_DIAMETER / 2,
                        getDotCoords(x, y)[1] - DOT_DIAMETER / 2,
                        DOT_DIAMETER,
                        DOT_DIAMETER);
            }
        }
    }
    private void drawNumbers(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                String text = memorySet.getVisible().getNumbers().get(x, y).toString(true);
                int textWidth = g.getFontMetrics().stringWidth(text);
                g.drawString(text,
                        getSquareCenterCoords(x, y)[0] - textWidth / 2,
                        getSquareCenterCoords(x, y)[1] + (int) (getFont().getSize() / HEIGHT_OFFSET));
            }
        }
    }
    private void drawLines(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getYSize() + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize() + 1; x++) {
                Line eastLine = memorySet.getVisible().getLines().getPoint(x, y, CardinalDirection.EAST);
                Line eastLineAnswer = memorySet.getCalculation().getLines().getPoint(x, y, CardinalDirection.EAST);
                Line southLine = memorySet.getVisible().getLines().getPoint(x, y, CardinalDirection.SOUTH);
                Line southLineAnswer = memorySet.getCalculation().getLines().getPoint(x, y, CardinalDirection.SOUTH);

                if (checkAccuracy) {
                    if (eastLineAnswer == Line.EMPTY) {
                        g.setColor(startingColor);
                    } else if (eastLine == eastLineAnswer) {
                        g.setColor(CORRECT_COLOR);
                    } else {
                        g.setColor(INCORRECT_COLOR);
                    }
                }

                if (eastLine == Line.LINE && x != memorySet.getVisible().getXSize()) {
                    g.drawLine(getDotCoords(x, y)[0],
                            getDotCoords(x, y)[1],
                            getDotCoords(x + 1, y)[0],
                            getDotCoords(x + 1, y)[1]);
                } else if (eastLine == Line.X && x != memorySet.getVisible().getXSize()) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getSquareCenterCoords(x, y)[0] - textWidth / 2,
                            getDotCoords(x, y)[1] + (int) (getFont().getSize() / HEIGHT_OFFSET));
                }

                if (checkAccuracy) {
                    if (southLineAnswer == Line.EMPTY) {
                        g.setColor(startingColor);
                    } else if (southLine == southLineAnswer) {
                        g.setColor(CORRECT_COLOR);
                    } else {
                        g.setColor(INCORRECT_COLOR);
                    }
                }

                if (southLine == Line.LINE && y != memorySet.getVisible().getYSize()) {
                    g.drawLine(getDotCoords(x, y)[0],
                            getDotCoords(x, y)[1],
                            getDotCoords(x, y + 1)[0],
                            getDotCoords(x, y + 1)[1]);
                } else if (southLine == Line.X && y != memorySet.getVisible().getYSize()) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getDotCoords(x, y)[0] - textWidth / 2,
                            getSquareCenterCoords(x, y)[1] + (int) (getFont().getSize() / HEIGHT_OFFSET));
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawHighlights(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                Highlight highlight = memorySet.getVisible().getHighlights().get(x, y);
                if (highlight != Highlight.EMPTY) {
                    if (highlight == Highlight.INSIDE) {
                        g.setColor(new Color(193, 255, 176));
                    } else if (highlight == Highlight.OUTSIDE) {
                        g.setColor(new Color(198, 255, 255));
                    }
                    g.fillRect(getDotCoords(x, y)[0],
                            getDotCoords(x, y)[1],
                            LINE_SIZE,
                            LINE_SIZE);
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawDiagonals(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                    Diagonal diagonal = memorySet.getVisible().getDiagonals().getSquare(x, y, diagonalDirection);
                    int[] start = new int[0];
                    int[] end = new int[0];
                    switch (diagonalDirection) {
                        case NORTHEAST -> {
                            start = new int[]{getDotCoords(x + 1, y)[0] - LINE_SIZE / 4, getDotCoords(x + 1, y)[1]};
                            end = new int[]{getDotCoords(x + 1, y)[0], getDotCoords(x + 1, y)[1] + LINE_SIZE / 4};

                        }
                        case SOUTHEAST -> {
                            start = new int[]{getDotCoords(x + 1, y + 1)[0] - LINE_SIZE / 4, getDotCoords(x + 1, y + 1)[1]};
                            end = new int[]{getDotCoords(x + 1, y + 1)[0], getDotCoords(x + 1, y + 1)[1] - LINE_SIZE / 4};
                        }
                        case SOUTHWEST -> {
                            start = new int[]{getDotCoords(x, y + 1)[0] + LINE_SIZE / 4, getDotCoords(x, y + 1)[1]};
                            end = new int[]{getDotCoords(x, y + 1)[0], getDotCoords(x, y + 1)[1] - LINE_SIZE / 4};
                        }
                        case NORTHWEST -> {
                            start = new int[]{getDotCoords(x, y)[0] + LINE_SIZE / 4, getDotCoords(x, y)[1]};
                            end = new int[]{getDotCoords(x, y)[0], getDotCoords(x, y)[1] + LINE_SIZE / 4};
                        }
                    }

                    if (diagonal == Diagonal.EITHER_OR) {
                        g.drawLine(start[0], start[1], end[0], end[1]);
                    } else if (diagonal == Diagonal.BOTH_OR_NEITHER) {
                        g.drawLine(start[0], start[1], start[0], end[1]);
                        g.drawLine(start[0], end[1], end[0], end[1]);
                    }
                }
            }
        }
    }

    public int[] getDotCoords(int x, int y) {
        return new int[]{STARTING_X + LINE_SIZE * x, STARTING_Y + LINE_SIZE * y};
    }
    public int[] getSquareCenterCoords(int x, int y) {
        return new int[]{STARTING_X + LINE_SIZE / 2 + x * LINE_SIZE, STARTING_Y + LINE_SIZE / 2 + y * LINE_SIZE};
    }
    public int[] getSquareIndex(int xCoord, int yCoord) {
        return new int[]{(xCoord - STARTING_X) / LINE_SIZE, (yCoord - STARTING_Y) / LINE_SIZE};
    }
    public int getLineSize() {
        return LINE_SIZE;
    }

    public void toggleCheckAccuracy() {
        checkAccuracy = !checkAccuracy;
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