package SolvingActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class LineIntoBlock {
    static public void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
            lineIntoTwo(memory, coords);
            lineIntoThree(memory, coords);
        }
    }

    static public void lineIntoTwo(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.TWO) {
            for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                // if there is a LINE entering the TWO
                CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();
                if (
                        memory.getLine(false, coords.squareToPoint(diagonalDirection), cardinalDirections[0]) == Line.LINE
                        || memory.getLine(false, coords.squareToPoint(diagonalDirection), cardinalDirections[1]) == Line.LINE
                ) {
                    // and there is an X on the TWO opposite the LINE
                    if (memory.getLine(true, coords, cardinalDirections[0].getOpposite()) == Line.X ||
                            memory.getLine(true, coords, cardinalDirections[1].getOpposite()) == Line.X
                    ) {
                        // set other opposite side to LINE
                        memory.setLine(true, Line.LINE, coords, cardinalDirections[0].getOpposite(), false);
                        memory.setLine(true, Line.LINE, coords, cardinalDirections[1].getOpposite(), false);
                    }
                }
            }
        }
    }
    static public void lineIntoThree(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.THREE) {
            for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                // if there is a LINE entering the THREE
                CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();
                if (
                        memory.getLine(false, coords.squareToPoint(diagonalDirection), cardinalDirections[0]) == Line.LINE
                        || memory.getLine(false, coords.squareToPoint(diagonalDirection), cardinalDirections[1]) == Line.LINE
                ) {
                    // set opposite sides of the THREE to LINE
                    memory.setLine(true, Line.LINE, coords, cardinalDirections[0].getOpposite(), false);
                    memory.setLine(true, Line.LINE, coords, cardinalDirections[1].getOpposite(), false);
                }
            }
        }
    }
}
