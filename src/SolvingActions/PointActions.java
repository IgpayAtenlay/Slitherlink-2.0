package SolvingActions;

import Enums.CardinalDirection;
import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.FullMemory;

public class PointActions {
    static public void run(FullMemory memory) {
        System.out.println("starting " + PointActions.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
                Coords coords = new Coords(x, y);
                fillSides(memory, coords);
                fillDiagonals(memory, coords);
                copyDiagonals(memory, coords);
                useDiagonals(memory, coords);
            }
        }

        System.out.println(PointActions.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void fillSides(FullMemory memory, Coords coords) {
        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(false, coords, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLine(false, coords, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == 2 || xs == 3) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.setLine(false, Line.X, coords, direction, false));
            }
        } else if (xs == 2 && lines == 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.setLine(false, Line.LINE, coords, direction, false));
            }
        }
    }
    public static void fillDiagonals(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Line lineOne = memory.getLine(false, coords, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLine(false, coords, direction.getCardinalDirections()[1]);
            if (lineOne != Line.EMPTY && lineTwo != Line.EMPTY) {
                if (lineOne == lineTwo) {
                    memory.change(memory.getDiagonals().setPoint(Diagonal.BOTH_OR_NEITHER, coords, direction, false));
                } else if (lineOne.getOpposite() == lineTwo) {
                    memory.change(memory.getDiagonals().setPoint(Diagonal.EXACTLY_ONE, coords, direction, false));
                }
            } else if (lineOne == Line.LINE || lineTwo == Line.LINE) {
                memory.change(memory.getDiagonals().setPoint(Diagonal.AT_LEAST_ONE, coords, direction, false));
            } else if (lineOne == Line.X || lineTwo == Line.X) {
                memory.change(memory.getDiagonals().setPoint(Diagonal.AT_MOST_ONE, coords, direction, false));
            }
        }
    }
    public static void useDiagonals(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getPoint(coords, direction);
            Line lineOne = memory.getLine(false, coords, direction.getCardinalDirections()[0]);
            Line lineTwo = memory.getLine(false, coords, direction.getCardinalDirections()[1]);

            if (diagonal != Diagonal.EMPTY) {
                if (diagonal == Diagonal.EXACTLY_ONE) {
                    memory.change(memory.setLine(false, lineOne.getOpposite(), coords, direction.getCardinalDirections()[1], false));
                    memory.change(memory.setLine(false, lineTwo.getOpposite(), coords, direction.getCardinalDirections()[0], false));
                } else if (diagonal == Diagonal.BOTH_OR_NEITHER) {
                    memory.change(memory.setLine(false, lineOne, coords, direction.getCardinalDirections()[1], false));
                    memory.change(memory.setLine(false, lineTwo, coords, direction.getCardinalDirections()[0], false));
                }
            }
        }
    }
    public static void copyDiagonals(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getPoint(coords, direction);
            switch (diagonal) {
                case BOTH_OR_NEITHER, EXACTLY_ONE -> memory.change(memory.getDiagonals().setPoint(diagonal, coords, direction.getOpposite(), false));
                case AT_LEAST_ONE -> memory.change(memory.getDiagonals().setPoint(Diagonal.AT_MOST_ONE, coords, direction.getOpposite(), false));
            }

        }
    }
}
