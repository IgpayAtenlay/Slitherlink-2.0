import Enums.Difficulty;
import Memory.Dimentions;
import Memory.MemorySet;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.PDFtoFile;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        PDFtoFile.write("sl_d0_07x07_b002");
//        new Frame();
//        testingEnviroment();
//        solvePuzzle();
//        write();
//        read("christmas puzzle for dad");
    }

    public static void testingEnviroment() {
        MemorySet memory = new MemorySet(new Dimentions(9, 3));
//        new Frame(memory);
    }

    public static void solvePuzzle() {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
//        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzles.get(2)));
//        memory.autoSolve(false);
//        memory.linesCalculationToVisible();
//        new Frame(memory);
    }
}
