package Visuals.Interactions;

import CompletetionChecking.Complete;
import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.Memory;
import Memory.MemorySet;
import PuzzleLoading.Write;
import SolvingActions.AdjacentBlocks;
import SolvingActions.Control;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;


public class Puzzle {
    private final MemorySet memorySet;
    private final Visuals.Panel.Puzzle panel;
    static private String mode = "default";

    public Puzzle(MemorySet memorySet, Visuals.Panel.Puzzle panel) {
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
        if (mode == "default") {
            memorySet.getVisible().setLine(true, currentLine.cycle(), squareIndex, direction, true);
        } else if (mode == "empty") {
            memorySet.getVisible().setNumber(Number.EMPTY, squareIndex, true);
        } else {
            memorySet.getVisible().setNumber(Number.getNumber(Integer.parseInt(mode)), squareIndex, true);
        }

        panel.repaint();
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
        panel.toggleCheckAccuracy();
        panel.repaint();
    }
    public void autoSolve() {
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
    public void autoSolveOneStep() {
        Control.oneRoundAutosolve(memorySet.getVisible(), false, 0);
        panel.repaint();
    }
    public void checkForErrors() {
        panel.setErrorChecking(Errors.hasErrors(memorySet.getVisible()));
        panel.repaint();
    }
    public void checkForCompletetion() {
        panel.setCompletionChecking(Complete.isComplete(memorySet.getVisible()));
        panel.repaint();
    }
    public void highlight() {
        Memory visible = memorySet.getVisible();
        int changes;
        do {
            changes = visible.getNumChanges();
            for (Coords coords : visible.getDimentions().allSquareCoords()) {
                AdjacentBlocks.createHighlight(visible, coords);
            }
        } while (changes != visible.getNumChanges());

        panel.repaint();
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
