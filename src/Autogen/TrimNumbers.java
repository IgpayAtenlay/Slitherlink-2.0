package Autogen;

import CompletetionChecking.Complete;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.Control;

import java.util.ArrayList;

public class TrimNumbers {
    public static void trim(Memory puzzle) {
        ArrayList<Coords> coords = puzzle.getDimentions().allSquareCoords();

        while (coords.size() > 0) {
            int testIndex = (int) (Math.random() * coords.size());
            Coords testCoords = coords.get(testIndex);
            coords.remove(testIndex);

            Memory testPuzzle = puzzle.copy();
            testPuzzle.setNumber(Number.EMPTY, testCoords, true);
            Control.autoSolve(testPuzzle, false);
            if (Complete.isComplete(testPuzzle)) {
                puzzle.setNumber(Number.EMPTY, testCoords, true);
            }
        }
    }
}
