package Autogen;

import CompletetionChecking.Complete;
import Enums.Number;
import Enums.*;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.Control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrimNumbers {
    public static int trimAll(Memory puzzle) {
        ArrayList<Coords> coords = puzzle.getDimentions().allSquareCoords();

        int totalCoords = coords.size();
        coords = add(puzzle, coords, Math.max(1, totalCoords / 40));
        coords = checkThenTrim(puzzle, coords, Math.max(1, totalCoords / 13));
        return coords.size();
    }

    public static ArrayList<Coords> add(Memory puzzle, ArrayList<Coords> canBeAdded, int numToAdd) {
        Memory testPuzzle = new Memory(puzzle.getDimentions());
        ArrayList<Coords> empty = new ArrayList<>();
        ArrayList<Coords> added = new ArrayList<>();

        while (canBeAdded.size() > 0) {
            for (int i = 0; i < numToAdd && canBeAdded.size() > 0; i++) {
                int testIndex = (int) (Math.random() * canBeAdded.size());
                Coords coord = canBeAdded.remove(testIndex);
                boolean unnecisary = testIfUnnessisary(testPuzzle, coord);
                if (unnecisary) {
                    empty.add(coord);
                    i--;
                } else {
                    testPuzzle.setNumber(puzzle.getNumber(coord), coord, true);
                    added.add(coord);
                }
            }

            Control.autoSolve(testPuzzle, false);

            if (Complete.isComplete(testPuzzle)) {
                empty.addAll(canBeAdded);
                for (Coords coords : empty) {
                    puzzle.setNumber(Number.EMPTY, coords, true);
                }
                return added;
            }
        }
        return added;
    }

    public static boolean testIfUnnessisary(Memory memory, Coords coords) {
        boolean nessisary = false;
        if (memory.getHighlight(coords) == Highlight.EMPTY) nessisary = true;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, coords, direction) == Line.EMPTY) nessisary = true;
        }
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getCorner(true, coords, direction) == Corner.EMPTY) nessisary = true;
        }
        return !nessisary;
    }

    public static ArrayList<Coords> trimAll(Memory puzzle, ArrayList<Coords> toRemove, int numToRemove) {
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
    public static ArrayList<Coords> checkThenTrim(Memory puzzle, ArrayList<Coords> toRemove, int repsPerCheck) {
        ArrayList<Coords> notRemoved = new ArrayList<>();

        while (toRemove.size() > 0) {
            toRemove = findRemovableCoords(puzzle, toRemove);
            for (int i = 0; i < repsPerCheck && toRemove.size() > 0; i++) {
                int testIndex = (int) (Math.random() * toRemove.size());
                Coords testCoords = toRemove.get(testIndex);
                toRemove.remove(testIndex);

                Memory testPuzzle = puzzle.copy();
                testPuzzle.setNumber(Number.EMPTY, testCoords, true);

                Control.autoSolve(testPuzzle, false);

                if (Complete.isComplete(testPuzzle)) {
                    puzzle.setNumber(Number.EMPTY, testCoords, true);
                } else {
                    notRemoved.add(testCoords);
                }
            }
        }

        return notRemoved;
    }
    public static ArrayList<Coords> findRemovableCoords(Memory puzzle, ArrayList<Coords> coordsList) {
        List<Coords> removable = Collections.synchronizedList(new ArrayList<>());

        ExecutorService executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );

        for (Coords coord : coordsList) {
            executor.submit(() -> {
                if (isRemovable(puzzle, coord)) {
                    removable.add(coord);
                }
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(removable);
    }

    public static boolean isRemovable(Memory puzzle, Coords coord) {
        Memory testPuzzle = puzzle.copy();
        testPuzzle.setNumber(Number.EMPTY, coord, true);
        Control.autoSolve(testPuzzle, false);
        return Complete.isComplete(testPuzzle);
    }
}
