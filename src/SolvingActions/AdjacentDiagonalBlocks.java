package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.FullMemory;

public class AdjacentDiagonalBlocks {
    static public void run(FullMemory memory) {
        System.out.println("starting " + AdjacentDiagonalBlocks.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                if (memory.getNumbers().get(coords) == Number.THREE) {
                    doubleThrees(memory, coords);
                }
            }
        }

        System.out.println(AdjacentDiagonalBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void doubleThrees(FullMemory memory, Coords coords) {
        if (memory.getNumbers().get(new Coords(coords.x + 1, coords.y - 1)) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, coords, CardinalDirection.WEST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, coords, CardinalDirection.SOUTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, new Coords(coords.x + 1, coords.y - 1), CardinalDirection.NORTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, new Coords(coords.x + 1, coords.y - 1), CardinalDirection.EAST, false));
        }
        if (memory.getNumbers().get(new Coords(coords.x + 1, coords.y + 1)) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, coords, CardinalDirection.WEST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, coords, CardinalDirection.NORTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, new Coords(coords.x + 1, coords.y + 1), CardinalDirection.SOUTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, new Coords(coords.x + 1, coords.y + 1), CardinalDirection.EAST, false));
        }
    }
}
