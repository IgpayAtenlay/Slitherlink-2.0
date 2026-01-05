package Visuals.Interactions;

import CompletetionChecking.Complete;
import Enums.CardinalDirection;
import Enums.Highlight;
import Enums.Line;
import Enums.Number;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.Memory;
import Memory.MemorySet;
import PuzzleLoading.Write;
import SolvingActions.AdjacentBlocks;
import SolvingActions.Control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;


public class Puzzle {
    private final MemorySet memorySet;
    private final Visuals.Panel.Puzzle panel;

    public Puzzle(MemorySet memorySet, Visuals.Panel.Puzzle panel) {
        this.memorySet = memorySet;
        this.panel = panel;
    }

    public void click(MouseEvent e) {
        Point clickCoords = e.getPoint();

        Coords squareIndex = panel.getSquareIndex(clickCoords);
        CardinalDirection direction = panel.getLineDirection(clickCoords);

        Line currentLine = memorySet.getVisible().getLine(true, squareIndex, direction);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (currentLine == Line.LINE) {
                memorySet.getVisible().setLine(true, Line.EMPTY, squareIndex, direction, true);
            } else {
                memorySet.getVisible().setLine(true, Line.LINE, squareIndex, direction, true);
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (currentLine == Line.X) {
                memorySet.getVisible().setLine(true, Line.EMPTY, squareIndex, direction, true);
            } else {
                memorySet.getVisible().setLine(true, Line.X, squareIndex, direction, true);
            }
        }


        panel.repaint();
    }
    public void numbers(char keyChar, Point mouseCoords) {
        Coords squareIndex = panel.getSquareIndex(mouseCoords);
        Coords dotCoords = panel.getNorthWestDotCoords(squareIndex);
        Coords relativeCoords = new Coords(mouseCoords.x - dotCoords.x, mouseCoords.y - dotCoords.y);
        switch (keyChar) {
            case '0', '1', '2', '3' -> memorySet.getVisible().setNumber(Number.getNumber(Integer.parseInt(String.valueOf(keyChar))), squareIndex, true);
            case java.awt.event.KeyEvent.VK_BACK_SPACE -> memorySet.getVisible().setNumber(Number.EMPTY, squareIndex, true);
            case 'i' -> memorySet.getVisible().setHighlight(Highlight.INSIDE, squareIndex, true);
            case 'o' -> memorySet.getVisible().setHighlight(Highlight.OUTSIDE, squareIndex, true);
            case 'p' -> memorySet.getVisible().setHighlight(Highlight.EMPTY, squareIndex, true);
            default -> {}
        }

        panel.repaint();
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
        Control.oneRoundAutosolve(memorySet.getVisible(), false);
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
