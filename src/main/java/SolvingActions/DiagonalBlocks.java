package SolvingActions;

import Enums.Corner;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class DiagonalBlocks {
    static public void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
            diagonalThrees(memory, coords);
            corneredDiagonalThrees(memory, coords);
        }
    }

    static public void diagonalThrees(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.THREE) {
            for(DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                if (memory.getNumber(coords.addDirection(diagonalDirection)) == Number.THREE) {
                    memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[0].getOpposite(), false);
                    memory.setLine(true, Line.LINE, coords, diagonalDirection.getCardinalDirections()[1].getOpposite(), false);
                }
            }
        }
    }
    static public void corneredDiagonalThrees(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.THREE) {
            for(DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                if (memory.getNumber(coords.addDirection(diagonalDirection)) == Number.THREE) {
                    Coords cornerCoords = coords.addDirection(diagonalDirection.getCardinalDirections()[0]);
                    DiagonalDirection farCorner = diagonalDirection.getCounterClockwise();
                    if (memory.getCorner(true, cornerCoords, farCorner) == Corner.SAME) {
                        memory.setLine(true, Line.X, cornerCoords, farCorner.getCardinalDirections()[0], false);
                        memory.setLine(true, Line.X, cornerCoords, farCorner.getCardinalDirections()[1], false);
                    }
                }
            }
        }
    }
}
