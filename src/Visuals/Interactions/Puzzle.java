package Visuals.Interactions;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.MemorySet;
import PuzzleLoading.Write;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Puzzle {
    private final MemorySet memorySet;
    private final Visuals.Panel.Puzzle puzzle;
    static private String mode = "default";

    public Puzzle(MemorySet memorySet, Visuals.Panel.Puzzle puzzle) {
        this.memorySet = memorySet;
        this.puzzle = puzzle;
    }

    public void click(MouseEvent e) {
        Coords clickCoords = new Coords(e.getX(), e.getY());
        Coords squareIndex = puzzle.getSquareIndex(clickCoords);
        Coords dotCoords = puzzle.getDotCoords(squareIndex);
        Coords relativeCoords = new Coords(clickCoords.x - dotCoords.x, clickCoords.y - dotCoords.y);

        CardinalDirection direction;
        if (relativeCoords.x > relativeCoords.y) {
            if (relativeCoords.x + relativeCoords.y > puzzle.getLineSize()) {
                direction = CardinalDirection.EAST;
            } else {
                direction = CardinalDirection.NORTH;
            }
        } else {
            if (relativeCoords.x + relativeCoords.y > puzzle.getLineSize()) {
                direction = CardinalDirection.SOUTH;
            } else {
                direction = CardinalDirection.WEST;
            }
        }
        Line currentLine = memorySet.getVisible().getLine(true, squareIndex, direction);
        if (mode == "default") {
            memorySet.getVisible().setLine(true, currentLine.cycle(), squareIndex, direction, true);
        } else if (mode == "empty") {
            memorySet.getVisible().setNumber(Number.EMPTY, squareIndex, true);
        } else {
            memorySet.getVisible().setNumber(Number.getNumber(Integer.parseInt(mode)), squareIndex, true);
        }

        puzzle.repaint();
    }
    public void numbers(KeyEvent e) {
        char keyChar = e.getKeyChar();
        switch (keyChar) {
            case '0', '1', '2', '3' -> mode = String.valueOf(keyChar);
            case java.awt.event.KeyEvent.VK_BACK_SPACE -> mode = "empty";
            default -> mode = "default";
        }
    }

    public void save() {
        Write.write(memorySet);
    }
    public void checkAccuracy() {
        puzzle.toggleCheckAccuracy();
        puzzle.repaint();
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
        puzzle.repaint();
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
        puzzle.repaint();
    }
    public void undo() {
        undo(1);
    }
    public void redo(int reps) {
        memorySet.getVisible().redo(reps);
        puzzle.repaint();
    }
    public void redo() {
        redo(1);
    }
}
