package Visuals;

import Enums.*;
import Memory.MemorySet;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    MemorySet memorySet;
    JFrame jFrame;
    int dotDiameter = 5;
    int startingX = 20;
    int startingY = 20;
    int lineSize = 30;
    int lineWidth = 2;
    double heightOffsetThing = 2.3;

    public Panel(MemorySet memorySet, JFrame jFrame) {
        this.memorySet = memorySet;
        this.jFrame = jFrame;
        setFont(getFont().deriveFont(Font.BOLD, 14f));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(lineWidth));

        drawHighlights(g2d);
        drawDots(g2d);
        drawNumbers(g2d);
        drawLines(g2d);
        drawDiagonals(g2d);
    }

    public void drawDots(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize() + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize() + 1; x++) {
                g.fillOval(startingX + lineSize * x - dotDiameter / 2,
                        startingY + lineSize * y - dotDiameter / 2,
                        dotDiameter,
                        dotDiameter);
            }
        }
    }
    public void drawNumbers(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                String text = memorySet.getVisible().getNumbers().get(x, y).toString(true);
                int textWidth = g.getFontMetrics().stringWidth(text);
                g.drawString(text,
                        startingX + lineSize / 2 + x * lineSize - textWidth / 2,
                        (int) (startingY + lineSize / 2 + y * lineSize + getFont().getSize() / heightOffsetThing));
            }
        }
    }
    public void drawLines(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize() + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize() + 1; x++) {
                Line eastLine = memorySet.getVisible().getLines().getPoint(x, y, CardinalDirection.EAST);
                Line southLine = memorySet.getVisible().getLines().getPoint(x, y, CardinalDirection.SOUTH);
                if (eastLine == Line.LINE && x != memorySet.getVisible().getXSize()) {
                    g.drawLine(startingX + lineSize * x,
                            startingY + lineSize * y,
                            startingX + lineSize * (x + 1),
                            startingY + lineSize * y);
                } else if (eastLine == Line.X && x != memorySet.getVisible().getXSize()) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            startingX + lineSize / 2 + x * lineSize - textWidth / 2,
                            (int) (startingY + y * lineSize + getFont().getSize() / heightOffsetThing));
                }

                if (southLine == Line.LINE && y != memorySet.getVisible().getYSize()) {
                    g.drawLine(startingX + lineSize * x,
                            startingY + lineSize * y,
                            startingX + lineSize * x,
                            startingY + lineSize * (y + 1));
                } else if (southLine == Line.X && y != memorySet.getVisible().getYSize()) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            startingX + x * lineSize - textWidth / 2,
                            (int) (startingY + lineSize / 2 + y * lineSize +  + getFont().getSize() / heightOffsetThing));
                }
            }
        }
    }
    public void drawHighlights(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                Highlight highlight = memorySet.getCalculation().getHighlights().get(x, y);
                if (highlight != Highlight.EMPTY) {
                    if (highlight == Highlight.INSIDE) {
                        g.setColor(new Color(193, 255, 176));
                    } else if (highlight == Highlight.OUTSIDE) {
                        g.setColor(new Color(198, 255, 255));
                    }
                    g.fillRect(startingX + lineSize * x,
                            startingY + lineSize * y,
                            lineSize,
                            lineSize);
                }
            }
        }
        g.setColor(startingColor);
    }
    public void drawDiagonals(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getYSize(); y++) {
            for (int x = 0; x < memorySet.getVisible().getXSize(); x++) {
                for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                    Diagonal diagonal = memorySet.getVisible().getDiagonals().getSquare(x, y, diagonalDirection);
                    int[] start = new int[0];
                    int[] end = new int[0];
                    switch (diagonalDirection) {
                        case NORTHEAST -> {
                            start = new int[]{startingX + x * lineSize + lineSize * 3 / 4, startingY + y * lineSize};
                            end = new int[]{startingX + (x + 1) * lineSize, startingY + y * lineSize + lineSize / 4};

                        }
                        case SOUTHEAST -> {
                            start = new int[]{startingX + x * lineSize + lineSize * 3 / 4, startingY + (y + 1) * lineSize};
                            end = new int[]{startingX + (x + 1) * lineSize, startingY + y * lineSize + lineSize * 3 / 4};
                        }
                        case SOUTHWEST -> {
                            start = new int[]{startingX + x * lineSize + lineSize / 4, startingY + (y + 1) * lineSize};
                            end = new int[]{startingX + x * lineSize, startingY + y * lineSize + lineSize * 3 / 4};
                        }
                        case NORTHWEST -> {
                            start = new int[]{startingX + x * lineSize + lineSize / 4, startingY + y * lineSize};
                            end = new int[]{startingX + x * lineSize, startingY + y * lineSize + lineSize / 4};
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
}
