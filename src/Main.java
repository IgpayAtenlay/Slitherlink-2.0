import Enums.Difficulty;
import Memory.Dimentions;
import Memory.MemorySet;
import PuzzleLoading.*;
import Visuals.Frame;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
//        testingEnviroment();
        solvePuzzle();
//        write();
//        read("christmas puzzle for dad");
    }

    public static void read(String fileName) {
        MemorySet memorySet = Read.read(fileName);
        if (memorySet != null) {
            new Frame(memorySet);
        }
    }

    public static void write() {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(2)));
        Write.write(memory);
    }

    public static void testingEnviroment() {
        MemorySet memory = new MemorySet(new Dimentions(9, 3));
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
