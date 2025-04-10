package PuzzleLoading;

import Enums.Number;
import Memory.Coords;
import Memory.Dimentions;
import Memory.Memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class ParseData {
    public static ArrayList<ArrayList<TextData>> splitLists(List<TextData> data) {
        ArrayList<ArrayList<TextData>> puzzles = new ArrayList<>();
        int currentPuzzleStart = 0;

        boolean puzzleStarted = false;
        for (int i = 0; i < data.size(); i++) {
            if (!puzzleStarted && "0123".contains(data.get(i).text)) {
                puzzleStarted = true;
                currentPuzzleStart = i;
                puzzles.add(new ArrayList<>());
            }
            if (puzzleStarted) {
                if ("0123".contains(data.get(i).text)) {
                    puzzles.get(puzzles.size() - 1).add(data.get(i));
                } else {
                    if (i - currentPuzzleStart < 5) {
                        puzzles.remove(puzzles.size() - 1);
                    }
                    puzzleStarted = false;
                }
            }
        }

        return puzzles;
    }
    public static Memory parsePuzzle(ArrayList<TextData> puzzleList) {
        TreeSet<Float> xValues = new TreeSet<>();
        TreeSet<Float> yValues = new TreeSet<>();

        for(TextData data : puzzleList) {
            xValues.add(data.x);
            yValues.add(data.y);
        }

        double inbetweenSpace = Double.MAX_VALUE;
        double previousValue;
        double currentValue;

        Iterator<Float> iterator = xValues.iterator();
        previousValue = iterator.next();

        while (iterator.hasNext()) {
            currentValue = iterator.next();
            inbetweenSpace = Math.min(inbetweenSpace, currentValue - previousValue);
            previousValue = currentValue;
        }

        iterator = yValues.iterator();
        previousValue = iterator.next();

        while (iterator.hasNext()) {
            currentValue = iterator.next();
            inbetweenSpace = Math.min(inbetweenSpace, currentValue - previousValue);
            previousValue = currentValue;
        }

        Dimentions dimentions = new Dimentions(
                (int) ((xValues.last() - xValues.first()) / inbetweenSpace) + 1,
                (int) ((yValues.last() - yValues.first()) / inbetweenSpace) + 1
        );

        Memory memory = new Memory(dimentions);
        for(TextData data : puzzleList) {
            Number number = Number.getNumber(Integer.parseInt(data.text));
            Coords coords = new Coords(
                    (int) (data.x / inbetweenSpace - xValues.first() / inbetweenSpace),
                    (int) (data.y / inbetweenSpace - yValues.first() / inbetweenSpace)
            );
            memory.setNumber(number, coords, true);
        }

        return memory;
    }
}
