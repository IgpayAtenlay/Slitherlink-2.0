package Visuals;

import Enums.*;
import Memory.Coords;
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
        buttonX = STARTING_X * 2 + memorySet.getVisible().getDimentions().xSize * LINE_SIZE;
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
        createButton("Check for Errors - testing only!", e -> interaction.checkForErrors());
        createButton("Check for Completetion - testing only!", e -> interaction.checkForCompletetion());
        createButton("Fill in Highlight - not implimented", e -> interaction.fillInHighlight());
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
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize + 1; x++) {
                Coords dotCoords = getDotCoords(new Coords(x, y));
                g.fillOval(dotCoords.x - DOT_DIAMETER / 2,
                        dotCoords.y - DOT_DIAMETER / 2,
                        DOT_DIAMETER,
                        DOT_DIAMETER);
            }
        }
    }
    private void drawNumbers(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                String text = memorySet.getVisible().getNumbers().get(coords).toString(true);
                int textWidth = g.getFontMetrics().stringWidth(text);
                g.drawString(text,
                        getSquareCenterCoords(coords).x - textWidth / 2,
                        getSquareCenterCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
            }
        }
    }
    private void drawLines(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize + 1; x++) {
                Coords coords = new Coords(x, y);
                Line eastLine = memorySet.getVisible().getLine(false, coords, CardinalDirection.EAST);
                Line eastLineAnswer = memorySet.getCalculation().getLine(false, coords, CardinalDirection.EAST);
                Line southLine = memorySet.getVisible().getLine(false, coords, CardinalDirection.SOUTH);
                Line southLineAnswer = memorySet.getCalculation().getLine(false, coords, CardinalDirection.SOUTH);

                if (checkAccuracy) {
                    if (eastLineAnswer == Line.EMPTY) {
                        g.setColor(startingColor);
                    } else if (eastLine == eastLineAnswer) {
                        g.setColor(CORRECT_COLOR);
                    } else {
                        g.setColor(INCORRECT_COLOR);
                    }
                }

                if (eastLine == Line.LINE && x != memorySet.getVisible().getDimentions().xSize) {
                    g.drawLine(getDotCoords(coords).x,
                            getDotCoords(coords).y,
                            getDotCoords(coords.addDirection(CardinalDirection.EAST)).x,
                            getDotCoords(coords.addDirection(CardinalDirection.EAST)).y);
                } else if (eastLine == Line.X && x != memorySet.getVisible().getDimentions().xSize) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getSquareCenterCoords(coords).x - textWidth / 2,
                            getDotCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
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

                if (southLine == Line.LINE && y != memorySet.getVisible().getDimentions().ySize) {
                    g.drawLine(getDotCoords(coords).x,
                            getDotCoords(coords).y,
                            getDotCoords(coords.addDirection(CardinalDirection.SOUTH)).x,
                            getDotCoords(coords.addDirection(CardinalDirection.SOUTH)).y);
                } else if (southLine == Line.X && y != memorySet.getVisible().getDimentions().ySize) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getDotCoords(coords).x - textWidth / 2,
                            getSquareCenterCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawHighlights(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                Highlight highlight = memorySet.getVisible().getHighlights().get(coords);
                if (highlight != Highlight.EMPTY) {
                    if (highlight == Highlight.INSIDE) {
                        g.setColor(new Color(193, 255, 176));
                    } else if (highlight == Highlight.OUTSIDE) {
                        g.setColor(new Color(198, 255, 255));
                    }
                    g.fillRect(getDotCoords(coords).x,
                            getDotCoords(coords).y,
                            LINE_SIZE,
                            LINE_SIZE);
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawDiagonals(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                    // switch back to visual only
                    Diagonal diagonal = memorySet.getCalculation().getDiagonals().getSquare(coords, diagonalDirection);
                    Coords start = null;
                    Coords end = null;
                    switch (diagonalDirection) {
                        case NORTHEAST -> {
                            Coords dotCoords = getDotCoords(coords.addDirection(CardinalDirection.EAST));
                            start = new Coords(dotCoords.x - LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y + LINE_SIZE / 4);

                        }
                        case SOUTHEAST -> {
                            Coords dotCoords = getDotCoords(coords.addDirection(DiagonalDirection.SOUTHEAST));
                            start = new Coords(dotCoords.x - LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y - LINE_SIZE / 4);
                        }
                        case SOUTHWEST -> {
                            Coords dotCoords = getDotCoords(coords.addDirection(CardinalDirection.SOUTH));
                            start = new Coords(dotCoords.x + LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y - LINE_SIZE / 4);
                        }
                        case NORTHWEST -> {
                            Coords dotCoords = getDotCoords(coords);
                            start = new Coords(dotCoords.x + LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y + LINE_SIZE / 4);
                        }
                    }

                    switch (diagonal) {
                        case EXACTLY_ONE -> {
                            g.drawLine(start.x, start.y, end.x, end.y);
                        }
                        case BOTH_OR_NEITHER -> {
                            g.drawLine(start.x, start.y, start.x, end.y);
                            g.drawLine(start.x, end.y, end.x, end.y);
                        }
                        case AT_LEAST_ONE -> {
                            g.drawLine(start.x, end.y, end.x, start.y);
                        }
                        case AT_MOST_ONE -> {
                            g.drawOval(start.x, end.y, 2, 2);
                            g.drawOval(end.x, start.y, 2, 2);
                        }
                    }
                }
            }
        }
    }

    public Coords getDotCoords(Coords coords) {
        return new Coords(STARTING_X + LINE_SIZE * coords.x, STARTING_Y + LINE_SIZE * coords.y);
    }
    public Coords getSquareCenterCoords(Coords coords) {
        return new Coords(STARTING_X + LINE_SIZE / 2 + coords.x * LINE_SIZE, STARTING_Y + LINE_SIZE / 2 + coords.y * LINE_SIZE);
    }
    public Coords getSquareIndex(Coords coords) {
        return new Coords((coords.x - STARTING_X) / LINE_SIZE, (coords.y - STARTING_Y) / LINE_SIZE);
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