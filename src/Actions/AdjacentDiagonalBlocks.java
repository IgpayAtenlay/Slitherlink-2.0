package Actions;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class AdjacentDiagonalBlocks {
    static public int run(FullMemory memory) {
        System.out.println("starting " + AdjacentDiagonalBlocks.class.getSimpleName());

        int changes = 0;

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                Number num = memory.getNumbers().get(x, y);
                if (num == Number.THREE) {
                    changes += doubleThrees(memory, x, y);
                }
            }
        }

        System.out.println(AdjacentDiagonalBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    static public int doubleThrees(FullMemory memory, int x, int y) {
        int changes = 0;
        if (memory.getNumbers().get(x + 1, y - 1) == Number.THREE) {
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.SOUTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x + 1, y - 1, CardinalDirection.NORTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x + 1, y - 1, CardinalDirection.EAST, false) ? 1 : 0;
        }
        if (memory.getNumbers().get(x + 1, y + 1) == Number.THREE) {
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.NORTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x + 1, y + 1, CardinalDirection.SOUTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x + 1, y + 1, CardinalDirection.EAST, false) ? 1 : 0;
        }
        return changes;
    }
}
