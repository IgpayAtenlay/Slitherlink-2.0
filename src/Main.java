import Enums.Difficulty;
import Files.Write;
import Memory.Dimentions;
import Memory.MemorySet;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;
import Visuals.Frame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        testingEnviroment();
//        solvePuzzle();
        write();
    }

    public static void write() {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(2)));
        Write.write(memory);
    }

    public static void testingEnviroment() {
        MemorySet memory = new MemorySet(new Dimentions(2, 2));
        new Frame(memory);
    }

    public static void solvePuzzle() {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(2)));
        memory.autoSolve(false);
        memory.linesCalculationToVisible();
        new Frame(memory);
    }
}
