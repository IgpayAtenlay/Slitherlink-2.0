package SolvingActions;

import Enums.CardinalDirection;
import Enums.Corner;
import Enums.DiagonalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class PointActions {
    static public void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allPointCoords()) {
            fillLinesOnPoint(memory, coords);
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                linesToCorners(memory, coords, direction);
                copyCornersAcrossPoint(memory, coords, direction);
                bothNeitherCorner(memory, coords, direction);
                minOneCornerAdjacentToMaxOneCorner(memory, coords, direction);
                cornersToLines(memory, coords, direction);
            }
        }
    }

    public static void fillLinesOnPoint(Memory memory, Coords coords) {
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
                memory.setLine(false, Line.X, coords, direction, false);
            }
        } else if (xs == 2 && lines == 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.setLine(false, Line.LINE, coords, direction, false);
            }
        }
    }
    public static void linesToCorners(Memory memory, Coords coords, DiagonalDirection direction) {
        Line lineOne = memory.getLine(false, coords, direction.getCardinalDirections()[0]);
        Line lineTwo = memory.getLine(false, coords, direction.getCardinalDirections()[1]);
        if (lineOne != Line.EMPTY && lineTwo != Line.EMPTY) {
            if (lineOne == lineTwo) {
                memory.setCorner(false, Corner.BOTH_OR_NEITHER, coords, direction, false);
            } else if (lineOne.getOpposite() == lineTwo) {
                memory.setCorner(false, Corner.EXACTLY_ONE, coords, direction, false);
            }
        } else if (lineOne == Line.LINE || lineTwo == Line.LINE) {
            memory.setCorner(false, Corner.AT_LEAST_ONE, coords, direction, false);
        } else if (lineOne == Line.X || lineTwo == Line.X) {
            memory.setCorner(false, Corner.AT_MOST_ONE, coords, direction, false);
        }
    }
    public static void copyCornersAcrossPoint(Memory memory, Coords coords, DiagonalDirection direction) {
        Corner corner = memory.getCorner(false, coords, direction);
        switch (corner) {
            case BOTH_OR_NEITHER, EXACTLY_ONE -> memory.setCorner(false, corner, coords, direction.getOpposite(), false);
            case AT_LEAST_ONE -> memory.setCorner(false, Corner.AT_MOST_ONE, coords, direction.getOpposite(), false);
        }
    }
    public static void bothNeitherCorner(Memory memory, Coords coords, DiagonalDirection direction) {
        if (memory.getCorner(false, coords, direction) == Corner.BOTH_OR_NEITHER) {
            memory.setCorner(false, Corner.AT_MOST_ONE, coords, direction.getClockwise(), false);
            memory.setCorner(false, Corner.AT_MOST_ONE, coords, direction.getCounterClockwise(), false);
        }
    }
    public static void minOneCornerAdjacentToMaxOneCorner(Memory memory, Coords coords, DiagonalDirection direction) {
        Corner corner = memory.getCorner(false, coords, direction);
        if (corner == Corner.AT_LEAST_ONE || corner == Corner.EXACTLY_ONE) {
            if (memory.getCorner(false, coords, direction.getClockwise()) == Corner.AT_MOST_ONE) {
                memory.setCorner(false, Corner.AT_LEAST_ONE, coords, direction.getCounterClockwise(), false);
            } else if (memory.getCorner(false, coords, direction.getCounterClockwise()) == Corner.AT_MOST_ONE) {
                memory.setCorner(false, Corner.AT_LEAST_ONE, coords, direction.getClockwise(), false);
            }
        }
    }
    public static void cornersToLines(Memory memory, Coords coords, DiagonalDirection direction) {
        Corner corner = memory.getCorner(false, coords, direction);
        Line lineOne = memory.getLine(false, coords, direction.getCardinalDirections()[0]);
        Line lineTwo = memory.getLine(false, coords, direction.getCardinalDirections()[1]);

        if (corner != Corner.EMPTY) {
            if (corner == Corner.EXACTLY_ONE) {
                memory.setLine(false, lineOne.getOpposite(), coords, direction.getCardinalDirections()[1], false);
                memory.setLine(false, lineTwo.getOpposite(), coords, direction.getCardinalDirections()[0], false);
            } else if (corner == Corner.BOTH_OR_NEITHER) {
                memory.setLine(false, lineOne, coords, direction.getCardinalDirections()[1], false);
                memory.setLine(false, lineTwo, coords, direction.getCardinalDirections()[0], false);
            }
        }
    }
}
