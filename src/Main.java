import Enums.Difficulty;
import Enums.Number;
import Memory.FullMemory;
import PuzzleLoading.ExtractDataToVariable;
import PuzzleLoading.ParseData;
import PuzzleLoading.TextData;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(ExtractDataToVariable.extract(Difficulty.TOUGH, 20, 1, 1));
        FullMemory memory = new FullMemory(ParseData.parsePuzzle(puzzles.get(0)));
        Control control = new Control(memory);
        control.autoSolve();
        control.getMemory().print();
    }

    public static void fullPuzzle() {
        Control control = new Control(5, 5);
        control.getMemory().setNumber(Number.TWO, 1, 0, true);
        control.getMemory().setNumber(Number.ZERO, 2, 0, true);
        control.getMemory().setNumber(Number.THREE, 4, 0, true);
        control.getMemory().setNumber(Number.THREE, 2, 1, true);
        control.getMemory().setNumber(Number.TWO, 1, 2, true);
        control.getMemory().setNumber(Number.TWO, 3, 2, true);
        control.getMemory().setNumber(Number.ONE, 0, 3, true);
        control.getMemory().setNumber(Number.THREE, 3, 3, true);
        control.getMemory().setNumber(Number.TWO, 3, 4, true);
        control.getMemory().print();
        control.autoSolve();
        control.getMemory().print();
    }
}
