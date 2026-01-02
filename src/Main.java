import Enums.Difficulty;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;
import Visuals.Frame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new Frame();
//        solvePuzzle();
    }

    public static void solvePuzzle() {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
//        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(2)));
//        memory.autoSolve(false);
//        memory.linesCalculationToVisible();
//        new Frame(memory);
    }
}
