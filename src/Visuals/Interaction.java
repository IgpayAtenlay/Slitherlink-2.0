package Visuals;

import Enums.CardinalDirection;
import Enums.Line;
import ErrorChecking.Errors;
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
        Coords relativeCoords = new Coords(clickCoords.x - dotCoords.x, clickCoords.y - dotCoords.y);

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
        Line currentLine = memorySet.getVisible().getLine(true, squareIndex, direction);
        memorySet.getVisible().setLine(true, currentLine.cycle(), squareIndex, direction, true);
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
                memorySet.getCalculation().setLine(true, memorySet.getVisible().getLine(true, coords, CardinalDirection.NORTH), coords, CardinalDirection.NORTH, true);
                memorySet.getCalculation().setLine(true, memorySet.getVisible().getLine(true, coords, CardinalDirection.WEST), coords, CardinalDirection.WEST, true);
            }
        }
        memorySet.autoSolve(false);
        memorySet.linesCalculationToVisible();
        panel.repaint();
    }
    public void checkForErrors() {
        if (Errors.hasErrors(memorySet.getVisible())) {
            System.out.println("Error");
        } else {
            System.out.println("No errors");
        }
    }
    public void checkForCompletetion() {
        if (Errors.isComplete(memorySet.getVisible())) {
            System.out.println("Complete");
        } else {
            System.out.println("Not Complete");
        }
    }
    public void fillInHighlight() {
        // don't do this
    }
    public void undo(int reps) {
        memorySet.getVisible().undo(reps);
        panel.repaint();
    }
    public void undo() {
        undo(1);
    }
    public void redo(int reps) {
        memorySet.getVisible().redo(reps);
        panel.repaint();
    }
    public void redo() {
        redo(1);
    }
}
