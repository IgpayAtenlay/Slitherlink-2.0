import Enums.Difficulty;
import Memory.MemorySet;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;
import Visuals.Frame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        testingEnviroment();
        solvePuzzle(Difficulty.TOUGH, 20, 1, 1);
    }

    public static void testingEnviroment() {
        MemorySet memory = new MemorySet(2, 2);
        Frame frame = new Frame(memory);
    }

    public static void solvePuzzle(Difficulty difficulty, int size, int volume, int book) {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(difficulty, size, volume, book));
        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(0)));
        memory.autoSolve(true);
        memory.linesCalculationToVisible();
        Frame frame = new Frame(memory);
    }
}
