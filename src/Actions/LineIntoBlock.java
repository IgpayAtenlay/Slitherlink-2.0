package Actions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class LineIntoBlock {
    static public int run(FullMemory memory) {
        System.out.println("starting " + LineIntoBlock.class.getSimpleName());

        int changes = 0;

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                Number num = memory.getNumbers().get(x, y);
                if (num == Number.THREE) {
                    changes += lineIntoThree(memory, x, y);
                }
            }
        }

        System.out.println(LineIntoBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    static public int lineIntoThree(FullMemory memory, int x, int y) {
        int changes = 0;
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
                changes += memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[0].getOpposite(), false) ? 1 : 0;
                changes += memory.getLines().setSquare(Line.LINE, x, y, cardinalDirections[1].getOpposite(), false) ? 1 : 0;
            }
        }
        return changes;
    }
}
