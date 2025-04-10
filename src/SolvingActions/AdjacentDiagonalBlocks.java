package SolvingActions;

import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class AdjacentDiagonalBlocks {
    static public void run(Memory memory) {
        System.out.println("starting " + AdjacentDiagonalBlocks.class.getSimpleName());
        int startingChanges = memory.getNumChanges();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                if (memory.getNumber(coords) == Number.THREE) {
                    doubleThrees(memory, coords);
                }
            }
        }

        System.out.println(AdjacentDiagonalBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
    }

    static public void doubleThrees(Memory memory, Coords coords) {
        for(DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
            if (memory.getNumber(coords.addDirection(diagonalDirection)) == Number.THREE) {
                memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[0].getOpposite(), false);
                memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[1].getOpposite(), false);
                memory.setLine(true, Line.LINE, coords.addDirection(diagonalDirection), diagonalDirection.getCardinalDirections()[0], false);
                memory.setLine(true, Line.LINE, coords.addDirection(diagonalDirection), diagonalDirection.getCardinalDirections()[1], false);
                // check if there is a nearby EitherOr
                Coords thirdLocation = coords.addDirection(diagonalDirection.getCardinalDirections()[0]);
                DiagonalDirection cornerToCheck = diagonalDirection.getCounterClockwise();
                if (memory.getDiagonal(true, thirdLocation, cornerToCheck) == Diagonal.BOTH_OR_NEITHER) {
                    memory.setLine(true, Line.X, thirdLocation, cornerToCheck.getCardinalDirections()[0], false);
                    memory.setLine(true, Line.X, thirdLocation, cornerToCheck.getCardinalDirections()[1], false);
                }
            }
        }
    }
}
