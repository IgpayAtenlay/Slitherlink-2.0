package Actions;

import Enums.CardinalDirection;
import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Memory.FullMemory;

public class PointActions {
    static public int run(FullMemory memory) {
        System.out.println("starting " + PointActions.class.getSimpleName());

        int changes = 0;

        for (int y = 0; y < memory.getYSize() + 1; y++) {
            for (int x = 0; x < memory.getXSize() + 1; x++) {
                changes += fillSides(memory, x, y);
                changes += fillDiagonals(memory, x, y);
                changes += copyDiagonals(memory, x, y);
                changes += useDiagonals(memory, x, y);
            }
        }

        System.out.println(PointActions.class.getSimpleName() + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    public static int fillSides(FullMemory memory, int x, int y) {
        int changes = 0;

        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(x, y, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLines().getPoint(x, y, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == 2 || xs == 3) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                changes += memory.getLines().setPoint(Line.X, x, y, direction, false) ? 1 : 0;
            }
        } else if (xs == 2 && lines == 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                changes += memory.getLines().setPoint(Line.LINE, x, y, direction, false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int fillDiagonals(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            // something is SSSSUUUUPPPPER broken here, but also not just here
            Line lineOne = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[1]);
            if (lineOne != Line.EMPTY && lineTwo != Line.EMPTY) {
                if (lineOne == lineTwo) {
                    changes += memory.getDiagonals().setPoint(Diagonal.BOTH_OR_NEITHER, x, y, direction, false) ? 1 : 0;
                } else if (lineOne.getOpposite() == lineTwo) {
                    changes += memory.getDiagonals().setPoint(Diagonal.EITHER_OR, x, y, direction, false) ? 1 : 0;
                }
            }
        }

        return changes;
    }
    public static int useDiagonals(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getPoint(x, y, direction);
            Line lineOne = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[1]);

            if (diagonal != Diagonal.EMPTY) {
                if (diagonal == Diagonal.EITHER_OR) {
                    changes += memory.getLines().setPoint(lineOne.getOpposite(), x, y, direction.getCardinalDirections()[1], false) ? 1 : 0;
                    changes += memory.getLines().setPoint(lineTwo.getOpposite(), x, y, direction.getCardinalDirections()[0], false) ? 1 : 0;
                } else if (diagonal == Diagonal.BOTH_OR_NEITHER) {
                    changes += memory.getLines().setPoint(lineOne, x, y, direction.getCardinalDirections()[1], false) ? 1 : 0;
                    changes += memory.getLines().setPoint(lineTwo, x, y, direction.getCardinalDirections()[0], false) ? 1 : 0;
                }
            }
        }

        return changes;
    }
    public static int copyDiagonals(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            changes += memory.getDiagonals().setPoint(
                    memory.getDiagonals().getPoint(x, y, direction),
                    x, y,
                    direction.getOpposite(),
                    false
            ) ? 1 : 0;
        }

        return changes;
    }
}
