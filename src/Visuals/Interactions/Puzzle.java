package Visuals.Interactions;

import CompletetionChecking.Complete;
import Enums.Number;
import Enums.*;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.Memory;
import Memory.MemorySet;
import PuzzleLoading.Write;
import SolvingActions.AdjacentBlocks;
import SolvingActions.Control;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Puzzle {
    private final MemorySet memorySet;
    private final Visuals.Panel.Puzzle panel;
    private Line recentLine = Line.EMPTY;
    private Highlight recentHighlight = Highlight.EMPTY;
    private Number recentNumber = Number.EMPTY;
    private Corner recentCorner = Corner.EMPTY;

    public Puzzle(MemorySet memorySet, Visuals.Panel.Puzzle panel) {
        this.memorySet = memorySet;
        this.panel = panel;
    }

    public void click(MouseEvent e) {
        Coords clickCoords = new Coords(e.getPoint());

        Coords squareIndex = panel.getSquareIndex(clickCoords);
        CardinalDirection direction = panel.getLineDirection(clickCoords);

        Line currentLine = memorySet.getVisible().getLine(true, squareIndex, direction);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (currentLine == Line.LINE) {
                memorySet.getVisible().setLine(true, Line.EMPTY, squareIndex, direction, true);
                recentLine = Line.EMPTY;
            } else {
                memorySet.getVisible().setLine(true, Line.LINE, squareIndex, direction, true);
                recentLine = Line.LINE;
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            if (currentLine == Line.X) {
                memorySet.getVisible().setLine(true, Line.EMPTY, squareIndex, direction, true);
                recentLine = Line.EMPTY;
            } else {
                memorySet.getVisible().setLine(true, Line.X, squareIndex, direction, true);
                recentLine = Line.X;
            }
        }

        panel.repaint();
    }
    public void drag(MouseEvent e) {
        Coords clickCoords = new Coords(e.getPoint());

        boolean nearEdge = panel.nearCenterEdge(clickCoords);
        if (nearEdge) {
            Coords squareIndex = panel.getSquareIndex(clickCoords);
            CardinalDirection direction = panel.getLineDirection(clickCoords);

            memorySet.getVisible().setLine(true, recentLine, squareIndex, direction, true);

            panel.repaint();
        }
    }
    public void numbers(int keyCode, Coords mouseCoords, boolean initial) {
        Coords squareIndex = panel.getSquareIndex(mouseCoords);
        DiagonalDirection cornerDirection = panel.getCornerDirection(mouseCoords);

        switch (keyCode) {
            case KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3 -> {
                Number currentNum = Number.getNumber(Integer.parseInt(KeyEvent.getKeyText(keyCode)));
                if (initial) {
                    if (memorySet.getVisible().getNumber(squareIndex) == currentNum) {
                        recentNumber = Number.EMPTY;
                    } else {
                        recentNumber = currentNum;
                    }
                }
                memorySet.getVisible().setNumber(recentNumber, squareIndex, true);
            }
            case KeyEvent.VK_I, KeyEvent.VK_O -> {
                Highlight currentHighlight = keyCode == KeyEvent.VK_I ? Highlight.INSIDE : Highlight.OUTSIDE;
                if (initial) {
                    if (memorySet.getVisible().getHighlight(squareIndex) == currentHighlight) {
                        recentHighlight = Highlight.EMPTY;
                    } else {
                        recentHighlight = currentHighlight;
                    }
                }
                memorySet.getVisible().setHighlight(recentHighlight, squareIndex, true);
            }
            default -> {
                Corner currentCorner = switch (keyCode) {
                    case KeyEvent.VK_W -> Corner.BOTH_OR_NEITHER;
                    case KeyEvent.VK_S -> Corner.EXACTLY_ONE;
                    case KeyEvent.VK_A -> Corner.AT_LEAST_ONE;
                    case KeyEvent.VK_D -> Corner.AT_MOST_ONE;
                    default -> Corner.EMPTY;
                };
                if (currentCorner == Corner.EMPTY) return;
                if (initial) {
                    if (memorySet.getVisible().getCorner(true, squareIndex, cornerDirection) == currentCorner) {
                        recentCorner = Corner.EMPTY;
                    } else {
                        recentCorner = currentCorner;
                    }
                }
                memorySet.getVisible().setCorner(true, recentCorner, squareIndex, cornerDirection, true);
            }
        }

        panel.repaint();
    }

    public void save() {
        Write.write(memorySet);
    }
    public void saveAs() {
        String origionalFolder = memorySet.getFolderPath();
        JFileChooser fileChooser = new JFileChooser(new File(origionalFolder));
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "JSON Files", "json"
        ));

        int result = fileChooser.showSaveDialog(panel);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();

            if (!absolutePath.toLowerCase().endsWith(".json")) {
                selectedFile = new File(absolutePath + ".json");
            }

            if (selectedFile.exists()) {
                int choice = JOptionPane.showConfirmDialog(
                        panel,
                        "File already exists. Overwrite?",
                        "Confirm Save",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            Path base = Paths.get("").toAbsolutePath();
            Path target = selectedFile.toPath().toAbsolutePath();
            String finalPath = String.valueOf(base.relativize(target));

            System.out.println(finalPath);

            Write.write(memorySet, finalPath);
        }
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
        if (reps > 0) {
            memorySet.getVisible().undo(reps);
            panel.repaint();
        }
    }
    public void undo() {
        undo(1);
    }
    public void redo(int reps) {
        if (reps > 0) {
            memorySet.getVisible().redo(reps);
            panel.repaint();
        }
    }
    public void reset() {
        memorySet.reset();
        panel.repaint();
    }
    public void redo() {
        redo(1);
    }
}
