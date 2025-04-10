package SolvingActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class LineIntoBlock {
    static public void run(Memory memory) {
        System.out.println("starting " + LineIntoBlock.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                switch (memory.getNumber(coords)) {
                    case TWO -> lineIntoTwo(memory, coords);
                    case THREE -> lineIntoThree(memory, coords);
                }
            }
        }

        System.out.println(LineIntoBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void lineIntoTwo(Memory memory, Coords coords) {
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {

            // if there is a line entering the TWO
            CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();
            if (
                    memory.getLine(false, 
                            coords.squareToPoint(diagonalDirection),
                        cardinalDirections[0]
                    ) == Line.LINE || memory.getLine(false, 
                            coords.squareToPoint(diagonalDirection),
                            cardinalDirections[1]
                    ) == Line.LINE
            ) {
                // and there is an X opposite the line
                if (memory.getLine(true, coords, cardinalDirections[0].getOpposite()) == Line.X ||
                        memory.getLine(true, coords, cardinalDirections[1].getOpposite()) == Line.X
                ) {
                    // set opposite sides to LINE
                    memory.change(memory.setLine(true, Line.LINE, coords, cardinalDirections[0].getOpposite(), false));
                    memory.change(memory.setLine(true, Line.LINE, coords, cardinalDirections[1].getOpposite(), false));
                }
            }
        }
    }
    static public void lineIntoThree(Memory memory, Coords coords) {
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
            // if there is a line entering the THREE
            CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();

            if (
                    memory.getLine(false, 
                        coords.squareToPoint(diagonalDirection),
                        cardinalDirections[0]
                    ) == Line.LINE || memory.getLine(false, 
                        coords.squareToPoint(diagonalDirection),
                        cardinalDirections[1]
                    ) == Line.LINE
            ) {
                // set opposite sides to LINE
                memory.change(memory.setLine(true, Line.LINE, coords, cardinalDirections[0].getOpposite(), false));
                memory.change(memory.setLine(true, Line.LINE, coords, cardinalDirections[1].getOpposite(), false));
            }
        }
    }
}
