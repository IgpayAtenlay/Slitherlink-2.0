import Enums.Difficulty;
import Memory.MemorySet;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.EASY, 7, 1, 1));
        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(5)));
        memory.autoSolve();
        memory.linesCalculationToVisible();
        memory.print();
    }
}
