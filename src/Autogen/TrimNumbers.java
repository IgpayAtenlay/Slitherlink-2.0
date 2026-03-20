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

    public static ArrayList<Coords> trim(Memory puzzle, ArrayList<Coords> toRemove, int numToRemove) {
        ArrayList<Coords> notRemoved = new ArrayList<>();

        while (toRemove.size() > 0) {
            numToRemove = Math.min(numToRemove, toRemove.size());
            ArrayList<Coords> currentlyTesting = new ArrayList<>();
            Memory testPuzzle = puzzle.copy();
            for (int i = 0; i < numToRemove; i++) {
                int testIndex = (int) (Math.random() * toRemove.size());
                Coords testCoords = toRemove.get(testIndex);
                toRemove.remove(testIndex);
                currentlyTesting.add(testCoords);
                testPuzzle.setNumber(Number.EMPTY, testCoords, true);
            }

            Control.autoSolve(testPuzzle, false);

            if (Complete.isComplete(testPuzzle)) {
                for (Coords coord : currentlyTesting) {
                    puzzle.setNumber(Number.EMPTY, coord, true);
                }
            } else {
                notRemoved.addAll(currentlyTesting);
            }
        }
        return notRemoved;
    }
}
