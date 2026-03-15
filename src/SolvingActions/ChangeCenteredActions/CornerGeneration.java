package SolvingActions.ChangeCenteredActions;

import Enums.*;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class CornerGeneration {
    public static boolean addCorner(Memory memory, Coords coords, DiagonalDirection direction)
        throws RuntimeException
    {
        Corner originalCorner = memory.getCorner(true, coords, direction);
        Corner corner = originalCorner;

//        neighbors
        Number squareNum = memory.getNumber(coords);
        Number oppositeAdjacentNumOne = memory.getNumber(coords.addDirection(direction.getCardinalDirections()[0].getOpposite()));
        Number oppositeAdjacentNumTwo = memory.getNumber(coords.addDirection(direction.getCardinalDirections()[1].getOpposite()));
        Corner acrossSquare = memory.getCorner(true, coords, direction.getOpposite());
        Corner adjacentSquareOne = memory.getCorner(true, coords, direction.getClockwise());
        Corner adjacentSquareTwo = memory.getCorner(true, coords, direction.getCounterClockwise());
        Line lineOne = memory.getLine(true, coords, direction.getCardinalDirections()[0]);
        Line lineTwo = memory.getLine(true, coords, direction.getCardinalDirections()[1]);
        Highlight northHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.NORTH));
        Highlight southHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.SOUTH));
        Highlight eastHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.EAST));
        Highlight westHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.WEST));
        Corner acrossPoint = memory.getCorner(true, coords.addDirection(direction), direction.getOpposite());
        Corner adjacentPointOne = memory.getCorner(true, coords.addDirection(direction.getCardinalDirections()[0]), direction.getClockwise());
        Corner adjacentPointTwo = memory.getCorner(true, coords.addDirection(direction.getCardinalDirections()[1]), direction.getCounterClockwise());

//        constraints
        try {
            corner = corner.combine(acrossSquare(squareNum, acrossSquare));
            corner = corner.combine(acrossPoint(acrossPoint));
        } catch (Exception e) {
            throw new RuntimeException("No solution");
        }
        corner = corner.combine(adjacentSquare(squareNum, adjacentSquareOne, adjacentSquareTwo));
        corner = corner.combine(entireSquare(acrossSquare, adjacentSquareOne, adjacentSquareTwo));
        corner = corner.combine(definition(lineOne, lineTwo));
        corner = corner.combine(twoBetweenIdenticalHighlights(squareNum, northHighlight, southHighlight));
        corner = corner.combine(twoBetweenIdenticalHighlights(squareNum, eastHighlight, westHighlight));
        corner = corner.combine(onesCornersBetweenIdenticalHighlights(adjacentSquareOne, adjacentSquareTwo, northHighlight, southHighlight));
        corner = corner.combine(onesCornersBetweenIdenticalHighlights(adjacentSquareOne, adjacentSquareTwo, eastHighlight, westHighlight));
        corner = corner.combine(adjacentPoint(adjacentPointOne, adjacentPointTwo));
        corner = corner.combine(entirePoint(acrossPoint, adjacentPointOne, adjacentPointTwo));
//        only run first time
        corner = corner.combine(number(squareNum));
        corner = corner.combine(diagonalThrees(oppositeAdjacentNumOne, oppositeAdjacentNumTwo));

        if(originalCorner != corner) {
            memory.setCorner(true, corner, coords, direction, false);
            return true;
        } else {
            return false;
        }
    }
    public static Corner number(Number number) {
        return switch (number) {
            case ZERO -> Corner.ZERO;
            case ONE -> Corner.NOT_TWO;
            case THREE -> Corner.NOT_ZERO;
            default -> Corner.ANY;
        };
    }
    public static Corner acrossSquare(Number number, Corner corner)
        throws RuntimeException
    {
        if (number == Number.EMPTY) return Corner.ANY;
        int goal = number.value;
        try {
            return corner.addTo(goal);
        } catch (Exception e) {
            throw new RuntimeException("No solution");
        }
    }
    public static Corner adjacentSquare(Number number, Corner adjacentOne, Corner adjacentTwo) {
        if (number == Number.TWO) {
            if (!adjacentOne.one || !adjacentTwo.one) {
                return Corner.ONE;
            }
        }
        return Corner.ANY;
    }
    public static Corner entireSquare(Corner across, Corner adjacentOne, Corner adjacentTwo) {
        if (adjacentOne == Corner.ONE && adjacentTwo == Corner.ONE)  {
            return Corner.getCorner(across.two,  across.one, across.zero);
        }

        int numEven = (across.even() ? 1 : 0) + (adjacentOne.even() ? 1 : 0) + (adjacentTwo.even() ? 1 : 0);
        int numOdd = (across == Corner.ONE ? 1 : 0) + (adjacentOne == Corner.ONE ? 1 : 0) + (adjacentTwo == Corner.ONE ? 1 : 0);

        if (numEven == 1 && numOdd == 2) return Corner.NOT_ONE;
        if (numEven == 2 && numOdd == 1) return Corner.ONE;
        if (numEven == 3) return Corner.ZERO;

        if (numEven == 2) {
            if (!across.even()) return across.combine(Corner.NOT_TWO);
            if (!adjacentOne.even()) return adjacentOne.combine(Corner.NOT_TWO);
            if (!adjacentTwo.even()) return adjacentTwo.combine(Corner.NOT_TWO);
        }

        return Corner.ANY;
    }
    public static Corner definition(Line lineOne, Line lineTwo) {
        return switch (lineOne) {
            case LINE -> switch (lineTwo) {
                case LINE -> Corner.TWO;
                case X -> Corner.ONE;
                case EMPTY -> Corner.NOT_ZERO;
            };
            case X -> switch (lineTwo) {
                case LINE -> Corner.ONE;
                case X -> Corner.ZERO;
                case EMPTY -> Corner.NOT_TWO;
            };
            case EMPTY -> switch (lineTwo) {
                case LINE -> Corner.NOT_ZERO;
                case X -> Corner.NOT_TWO;
                case EMPTY -> Corner.ANY;
            };
        };
    }
    public static Corner twoBetweenIdenticalHighlights(Number number, Highlight highlight, Highlight highlightAcross) {
        if (number == Number.TWO) {
            if (highlight == highlightAcross && highlight != Highlight.EMPTY) return Corner.ONE;
        }
        return Corner.ANY;
    }
    public static Corner onesCornersBetweenIdenticalHighlights(Corner adjacentOne, Corner adjacentTwo, Highlight highlight, Highlight highlightAcross) {
        if (adjacentOne == Corner.ONE && adjacentTwo == Corner.ONE) {
            if (highlight == highlightAcross && highlight != Highlight.EMPTY) return Corner.ONE;
        }
        return Corner.ANY;
    }
    public static Corner acrossPoint(Corner corner)
        throws RuntimeException
    {
        boolean zero = corner.zero || corner.two;
        boolean one = corner.one;
        boolean two = corner.zero;
        try {
            return Corner.getCorner(zero, one, two);
        } catch (Exception e) {
            throw new RuntimeException("No solution");
        }
    }
    public static Corner adjacentPoint(Corner adjacentOne, Corner adjacentTwo) {
        if (!adjacentOne.one || !adjacentTwo.one) return Corner.NOT_TWO;
        return Corner.ANY;
    }
    public static Corner entirePoint(Corner across, Corner adjacentOne, Corner adjacentTwo) {
        if (!across.two && (!adjacentOne.zero || !adjacentTwo.zero)) return Corner.NOT_ZERO;
        return Corner.ANY;
    }
    public static Corner diagonalThrees(Number oppositeAdjacentNumOne, Number oppositeAdjacentNumTwo) {
        if (oppositeAdjacentNumOne == Number.THREE && oppositeAdjacentNumTwo == Number.THREE) return Corner.NOT_TWO;
        return Corner.ANY;
    }
}
