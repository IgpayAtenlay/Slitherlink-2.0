package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class AdjacentDiagonalBlocks {
    static public void run(FullMemory memory) {
        System.out.println("starting " + AdjacentDiagonalBlocks.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                if (memory.getNumbers().get(x, y) == Number.THREE) {
                    doubleThrees(memory, x, y);
                }
            }
        }

        System.out.println(AdjacentDiagonalBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void doubleThrees(FullMemory memory, int x, int y) {
        if (memory.getNumbers().get(x + 1, y - 1) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.SOUTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x + 1, y - 1, CardinalDirection.NORTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x + 1, y - 1, CardinalDirection.EAST, false));
        }
        if (memory.getNumbers().get(x + 1, y + 1) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.NORTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x + 1, y + 1, CardinalDirection.SOUTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x + 1, y + 1, CardinalDirection.EAST, false));
        }
    }
}
