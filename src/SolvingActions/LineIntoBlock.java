package SolvingActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class LineIntoBlock {
    static public void run(FullMemory memory) {
        System.out.println("starting " + LineIntoBlock.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Number number = memory.getNumbers().get(x, y);
                switch (number) {
                    case TWO -> lineIntoTwo(memory, x, y);
                    case THREE -> lineIntoThree(memory, x, y);
                }
            }
        }

        System.out.println(LineIntoBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void lineIntoTwo(FullMemory memory, int x, int y) {
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {

            // if there is a line entering the TWO
            CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();
            if (memory.getLines().getPoint(ConvertCoordinates.squareToPointX(x, diagonalDirection),
                    ConvertCoordinates.squareToPointY(y, diagonalDirection),
                    cardinalDirections[0]) == Line.LINE ||
                    memory.getLines().getPoint(ConvertCoordinates.squareToPointX(x, diagonalDirection),
                            ConvertCoordinates.squareToPointY(y, diagonalDirection),
                            cardinalDirections[1]) == Line.LINE
            ) {
                // and there is an X opposite the line
                if (memory.getLines().getSquare(x, y, cardinalDirections[0].getOpposite()) == Line.X ||
                        memory.getLines().getSquare(x, y, cardinalDirections[1].getOpposite()) == Line.X
                ) {
                    // set opposite sides to LINE
                    memory.change(memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[0].getOpposite(), false));
                    memory.change(memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[1].getOpposite(), false));
                }
            }
        }
    }
    static public void lineIntoThree(FullMemory memory, int x, int y) {
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
            // if there is a line entering the THREE
            CardinalDirection[] cardinalDirections = diagonalDirection.getCardinalDirections();

            if (memory.getLines().getPoint(ConvertCoordinates.squareToPointX(x, diagonalDirection),
                    ConvertCoordinates.squareToPointY(y, diagonalDirection),
                    cardinalDirections[0]) == Line.LINE ||
                memory.getLines().getPoint(ConvertCoordinates.squareToPointX(x, diagonalDirection),
                        ConvertCoordinates.squareToPointY(y, diagonalDirection),
                        cardinalDirections[1]) == Line.LINE
            ) {
                // set opposite sides to LINE
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[0].getOpposite(), false));
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[1].getOpposite(), false));
            }
        }
    }
}
