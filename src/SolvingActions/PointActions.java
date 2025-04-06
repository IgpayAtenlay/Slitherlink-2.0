package SolvingActions;

import Enums.CardinalDirection;
import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Memory.FullMemory;

public class PointActions {
    static public void run(FullMemory memory) {
        System.out.println("starting " + PointActions.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
                fillSides(memory, x, y);
                fillDiagonals(memory, x, y);
                copyDiagonals(memory, x, y);
                useDiagonals(memory, x, y);
            }
        }

        System.out.println(PointActions.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void fillSides(FullMemory memory, int x, int y) {
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
                memory.change(memory.getLines().setPoint(Line.X, x, y, direction, false));
            }
        } else if (xs == 2 && lines == 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.getLines().setPoint(Line.LINE, x, y, direction, false));
            }
        }
    }
    public static void fillDiagonals(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            // something is SSSSUUUUPPPPER broken here, but also not just here
            Line lineOne = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[1]);
            if (lineOne != Line.EMPTY && lineTwo != Line.EMPTY) {
                if (lineOne == lineTwo) {
                    memory.change(memory.getDiagonals().setPoint(Diagonal.BOTH_OR_NEITHER, x, y, direction, false));
                } else if (lineOne.getOpposite() == lineTwo) {
                    memory.change(memory.getDiagonals().setPoint(Diagonal.EITHER_OR, x, y, direction, false));
                }
            }
        }
    }
    public static void useDiagonals(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getPoint(x, y, direction);
            Line lineOne = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLines().getPoint(x, y, direction.getCardinalDirections()[1]);

            if (diagonal != Diagonal.EMPTY) {
                if (diagonal == Diagonal.EITHER_OR) {
                    memory.change(memory.getLines().setPoint(lineOne.getOpposite(), x, y, direction.getCardinalDirections()[1], false));
                    memory.change(memory.getLines().setPoint(lineTwo.getOpposite(), x, y, direction.getCardinalDirections()[0], false));
                } else if (diagonal == Diagonal.BOTH_OR_NEITHER) {
                    memory.change(memory.getLines().setPoint(lineOne, x, y, direction.getCardinalDirections()[1], false));
                    memory.change(memory.getLines().setPoint(lineTwo, x, y, direction.getCardinalDirections()[0], false));
                }
            }
        }
    }
    public static void copyDiagonals(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            memory.change(memory.getDiagonals().setPoint(
                    memory.getDiagonals().getPoint(x, y, direction),
                    x, y,
                    direction.getOpposite(),
                    false
            ));
        }
    }
}
