package SolvingActions;

import Enums.DiagonalDirection;
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
        for(DiagonalDirection diagonalDirection : new DiagonalDirection[]{DiagonalDirection.NORTHWEST, DiagonalDirection.SOUTHEAST}) {
            if (memory.getNumbers().get(coords.addDirection(diagonalDirection)) == Number.THREE) {
                memory.change(memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[0].getOpposite(), false));
                memory.change(memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[1].getOpposite(), false));
                memory.change(memory.setLine(true, Line.LINE, coords.addDirection(diagonalDirection), diagonalDirection.getCardinalDirections()[0], false));
                memory.change(memory.setLine(true, Line.LINE, coords.addDirection(diagonalDirection), diagonalDirection.getCardinalDirections()[1], false));
            }
        }
    }
}
