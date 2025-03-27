package Visuals;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.MemorySet;

import java.awt.event.MouseEvent;

public class Interaction {
    private final MemorySet memorySet;
    private final Panel panel;

    public Interaction(MemorySet memorySet, Panel panel) {
        this.memorySet = memorySet;
        this.panel = panel;
    }

    public void click(MouseEvent e) {
        int[] clickCoords = {e.getX(), e.getY()};
        int[] squareIndex = panel.getSquareIndex(clickCoords[0], clickCoords[1]);
        int[] dotCoords = panel.getDotCoords(squareIndex[0], squareIndex[1]);
        int[] relativeCoords = {clickCoords[0] - dotCoords[0], clickCoords[1] - dotCoords[1]};

        CardinalDirection direction;
        if (relativeCoords[0] > relativeCoords[1]) {
            if (relativeCoords[0] + relativeCoords[1] > panel.getLineSize()) {
                direction = CardinalDirection.EAST;
            } else {
                direction = CardinalDirection.NORTH;
            }
        } else {
            if (relativeCoords[0] + relativeCoords[1] > panel.getLineSize()) {
                direction = CardinalDirection.SOUTH;
            } else {
                direction = CardinalDirection.WEST;
            }
        }
        Line currentLine = memorySet.getVisible().getLines().getSquare(squareIndex[0], squareIndex[1], direction);
        memorySet.getVisible().getLines().setSquare(currentLine.cycle(), squareIndex[0], squareIndex[1], direction, true);
        panel.repaint();
    }

    public void checkAccuracy() {
        panel.toggleCheckAccuracy();
        panel.repaint();
    }
    public void autoSolve() {
        System.out.println("autosolve button");
        // testing only!!!
        for (int y = 0; y < memorySet.getCalculation().getYSize() + 1; y++) {
            for (int x = 0; x < memorySet.getCalculation().getXSize() + 1; x++) {
                memorySet.getCalculation().change(memorySet.getCalculation().getLines().setSquare(memorySet.getVisible().getLines().getSquare(x, y, CardinalDirection.NORTH), x, y, CardinalDirection.NORTH, true));
                memorySet.getCalculation().change(memorySet.getCalculation().getLines().setSquare(memorySet.getVisible().getLines().getSquare(x, y, CardinalDirection.WEST), x, y, CardinalDirection.WEST, true));
            }
        }
        memorySet.autoSolve(false);
        memorySet.linesCalculationToVisible();
        panel.repaint();
    }
    public void checkForErrors() {
        if (memorySet.getControl().hasErrors()) {
            System.out.println("Error");
        } else {
            System.out.println("No errors");
        }
    }
    public void checkForCompletetion() {
        if (memorySet.getControl().isComplete()) {
            System.out.println("Complete");
        } else {
            System.out.println("Not Complete");
        }
    }
    public void fillInHighlight() {
        // don't do this
    }
}
