package SolvingActions.ChangeCenteredActions;

import Enums.Number;
import Enums.*;
import Memory.Coords;
import Memory.Loop;
import Memory.Memory;

public class LineGeneration {
    public static void addLine(Memory memory, Coords coords, CardinalDirection direction)
        throws RuntimeException
    {
        Line line = Line.EMPTY;

//        neighbors
        Corner insideCornerOne = memory.getCorner(true, coords, direction.getDiagonalDirections()[0]);
        Line insideLineOne = memory.getLine(true, coords, direction.getCounterClockwise());
        Corner insideCornerTwo = memory.getCorner(true, coords, direction.getDiagonalDirections()[1]);
        Line insideLineTwo = memory.getLine(true, coords, direction.getClockwise());
        Corner outsideCornerOne = memory.getCorner(true, coords.addDirection(direction), direction.getOpposite().getDiagonalDirections()[0]);
        Line outsideLineOne = memory.getLine(true, coords.addDirection(direction), direction.getClockwise());
        Corner outsideCornerTwo = memory.getCorner(true, coords.addDirection(direction), direction.getOpposite().getDiagonalDirections()[1]);
        Line outsideLineTwo = memory.getLine(true, coords.addDirection(direction), direction.getCounterClockwise());
        Highlight highlightOne = memory.getHighlight(coords);
        Highlight highlightTwo = memory.getHighlight(coords.addDirection(direction));
//        logic variables
        Loop loop = memory.getLoop(coords, direction.getDiagonalDirections()[0]);
        int totalLines = memory.getNumOfLines();

//        only run first time
//        3 3 |
        Number one = memory.getNumber(coords.addDirection(direction.getOpposite()));
//        3 |
        Number two = memory.getNumber(coords);
//        | 3
        Number three = memory.getNumber(coords.addDirection(direction));
//        | 3 3
        Number four = memory.getNumber(coords.addDirection(direction).addDirection(direction));
        Number topOne = memory.getNumber(coords.addDirection(direction.getCounterClockwise()));
        Number topTwo = memory.getNumber(coords.addDirection(direction.getCounterClockwise()).addDirection(direction));
        Number bottomOne = memory.getNumber(coords.addDirection(direction.getClockwise()));
        Number bottomTwo = memory.getNumber(coords.addDirection(direction.getClockwise()).addDirection(direction));

//        constraints
        try {
            line = line.combine(cornerDefinition(insideCornerOne, insideLineOne));
            line = line.combine(cornerDefinition(insideCornerTwo, insideLineTwo));
            line = line.combine(cornerDefinition(outsideCornerOne, outsideLineOne));
            line = line.combine(cornerDefinition(outsideCornerTwo, outsideLineTwo));
        } catch (Exception e) {
            throw new RuntimeException("No solution");
        }
        line = line.combine(highlightDefinition(highlightOne, highlightTwo));
        line = line.combine(loop(loop, coords, direction, totalLines));
//        only run first time
        line = line.combine(adjacentThreesLine(one, two, three, four));
        line = line.combine(adjacentThreesX(topOne, topTwo, bottomOne, bottomTwo));

        if(line != Line.EMPTY) {
            memory.setLine(true, line, coords, direction, false);
        }
    }

    public static Line cornerDefinition(Corner corner, Line otherLine)
        throws RuntimeException
    {
        boolean line =  false;
        boolean x = false;

        if (corner.zero) x = true;
        if (corner.one) {
            switch (otherLine) {
                case EMPTY -> {
                    x = true;
                    line = true;
                }
                case LINE -> x = true;
                case X -> line = true;
            }
        }
        if (corner.two) line = true;

        if (x && line) {
            return Line.EMPTY;
        } else if (line) {
            return Line.LINE;
        } else if (x)  {
            return Line.X;
        } else {
            throw new RuntimeException("No solution");
        }
    }
    public static Line adjacentThreesLine(Number one, Number two, Number three, Number four) {
        if (one == Number.THREE && two == Number.THREE) return Line.LINE;
        if (two == Number.THREE && three == Number.THREE) return Line.LINE;
        if (three == Number.THREE && four == Number.THREE) return Line.LINE;
        return Line.EMPTY;
    }
    public static Line adjacentThreesX(Number one, Number two, Number three, Number four) {
        if (one == Number.THREE && two == Number.THREE) return Line.X;
        if (three == Number.THREE && four == Number.THREE) return Line.X;
        return Line.EMPTY;
    }
    public static Line highlightDefinition(Highlight one, Highlight two) {
        if (one == Highlight.EMPTY || two == Highlight.EMPTY) return Line.EMPTY;
        if (one != two) return Line.LINE;
        return Line.X;
    }
    public static Line loop(Loop loop, Coords coords, CardinalDirection direction, int totalLines) {
        if (
                loop != null &&
                loop.endPoint.equals(coords.squareToPoint(direction.getDiagonalDirections()[1])) &&
                loop.length != 1 &&
                loop.length != totalLines
        ) {
            return Line.X;
        }
        return Line.EMPTY;
    }
}
