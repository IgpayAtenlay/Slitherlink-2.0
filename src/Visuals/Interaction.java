package Visuals;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
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
        Coords clickCoords = new Coords(e.getX(), e.getY());
        Coords squareIndex = panel.getSquareIndex(clickCoords);
        Coords dotCoords = panel.getDotCoords(squareIndex);
        Coords relativeCoords = new Coords(clickCoords.x - dotCoords.y, clickCoords.x - dotCoords.y);

        CardinalDirection direction;
        if (relativeCoords.x > relativeCoords.y) {
            if (relativeCoords.x + relativeCoords.y > panel.getLineSize()) {
                direction = CardinalDirection.EAST;
            } else {
                direction = CardinalDirection.NORTH;
            }
        } else {
            if (relativeCoords.x + relativeCoords.y > panel.getLineSize()) {
                direction = CardinalDirection.SOUTH;
            } else {
                direction = CardinalDirection.WEST;
            }
        }
        Line currentLine = memorySet.getVisible().getLines().getSquare(squareIndex, direction);
        memorySet.getVisible().getLines().setSquare(currentLine.cycle(), squareIndex, direction, true);
        panel.repaint();
    }

    public void checkAccuracy() {
        panel.toggleCheckAccuracy();
        panel.repaint();
    }
    public void autoSolve() {
        System.out.println("autosolve button");
        // testing only!!!
        for (int y = 0; y < memorySet.getCalculation().getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memorySet.getCalculation().getDimentions().xSize + 1; x++) {
                Coords coords = new Coords(x, y);
                memorySet.getCalculation().change(memorySet.getCalculation().getLines().setSquare(memorySet.getVisible().getLines().getSquare(coords, CardinalDirection.NORTH), coords, CardinalDirection.NORTH, true));
                memorySet.getCalculation().change(memorySet.getCalculation().getLines().setSquare(memorySet.getVisible().getLines().getSquare(coords, CardinalDirection.WEST), coords, CardinalDirection.WEST, true));
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
