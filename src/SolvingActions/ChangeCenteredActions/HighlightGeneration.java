package SolvingActions.ChangeCenteredActions;

import Enums.*;
import Memory.Coords;
import Memory.Memory;

public class HighlightGeneration {
    public static boolean addHighlight(Memory memory, Coords coords) {
        Highlight highlight = Highlight.EMPTY;

//        neighbors
        Line[] adjacentLines = new Line[4];
        Highlight[] adjacentHighlights = new Highlight[4];
        for (int i = 0; i < 4; i++) {
            CardinalDirection cardinalDirection = CardinalDirection.values()[i];
            adjacentLines[i] = memory.getLine(true, coords, cardinalDirection);
            adjacentHighlights[i] = memory.getHighlight(coords.addDirection(cardinalDirection));
        }
        Highlight[] diagonalHighlights = new Highlight[4];
        Corner[] outsideCornerOne = new Corner[4];
        Corner[] outsideCornerTwo = new Corner[4];
        for (int i = 0; i < 4; i++) {
            DiagonalDirection diagonalDirection = DiagonalDirection.values()[i];
            diagonalHighlights[i] = memory.getHighlight(coords.addDirection(diagonalDirection));
            outsideCornerOne[i] = memory.getCorner(true, coords.addDirection(diagonalDirection.getCardinalDirections()[0]), diagonalDirection.getClockwise());
            outsideCornerTwo[i] = memory.getCorner(true, coords.addDirection(diagonalDirection.getCardinalDirections()[1]), diagonalDirection.getCounterClockwise());
        }

//        constraints
        for (int i = 0; i < 4; i++) {
            highlight = highlight.combine(definition(adjacentLines[i], adjacentHighlights[i]));
            highlight = highlight.combine(corner(diagonalHighlights[i], outsideCornerOne[i], outsideCornerTwo[i]));
        }

        if(highlight != Highlight.EMPTY) {
            memory.setHighlight(highlight, coords, false);
            return true;
        } else {
            return false;
        }
    }

    public static Highlight definition(Line line, Highlight highlight) {
        if (line == Line.LINE) return highlight.getOpposite();
        if (line == Line.X) return highlight;
        return Highlight.EMPTY;
    }
    public static Highlight corner(Highlight diagonalHighlight, Corner outsideCornerOne, Corner outsideCornerTwo) {
        if (outsideCornerOne == Corner.ONE && outsideCornerTwo == Corner.ONE) {
            return diagonalHighlight.getOpposite();
        }
        return Highlight.EMPTY;
    }
}
