package SolvingActions;

import Enums.*;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class AdjacentBlocks {
    static public void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
            doubleThrees(memory, coords);
            twoBetweenIdenticalHighlights(memory, coords);
            lineExtendsHighlight(memory, coords);
            separateHighlightsWithLines(memory, coords);
        }
    }

    static public void doubleThrees(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.THREE) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (memory.getNumber(coords.addDirection(direction)) == Number.THREE) {
                    memory.setLine(true, Line.LINE, coords, direction.getOpposite(), false);
                    memory.setLine(true, Line.LINE, coords, direction, false);
                    memory.setLine(true, Line.X, coords.addDirection(direction.getClockwise()), direction, false);
                }
            }
        }
    }
    static public void lineExtendsHighlight(Memory memory, Coords coords) {
        if (memory.getHighlight(coords) == Highlight.EMPTY) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                Highlight adjacentHighlight = memory.getHighlight(coords.addDirection(direction));
                Line line = memory.getLine(true, coords, direction);
                if (line == Line.LINE) {
                    memory.setHighlight(adjacentHighlight.getOpposite(), coords, false);
                } else if (line == Line.X) {
                    memory.setHighlight(adjacentHighlight, coords, false);
                }
            }
        }
    }
    static public void separateHighlightsWithLines(Memory memory, Coords coords) {
        Highlight currentHighlight = memory.getHighlight(coords);
        if (currentHighlight != Highlight.EMPTY) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                Highlight adjacentHighlight = memory.getHighlight(coords.addDirection(direction));
                if (currentHighlight == adjacentHighlight) {
                    memory.setLine(true, Line.X, coords, direction, false);
                } else if (currentHighlight.isOpposite(adjacentHighlight)) {
                    memory.setLine(true, Line.LINE, coords, direction, false);
                }
            }
        }
    }
    static public void twoBetweenIdenticalHighlights(Memory memory, Coords coords) {
        if (memory.getNumber(coords) == Number.TWO) {
            for (CardinalDirection direction : new CardinalDirection[] {CardinalDirection.EAST, CardinalDirection.SOUTH}) {
                Highlight highlightDirectionOne = memory.getHighlight(coords.addDirection(direction));
                Highlight highlightDirectionTwo = memory.getHighlight(coords.addDirection(direction.getOpposite()));
                if (
                        highlightDirectionOne == highlightDirectionTwo &&
                        highlightDirectionOne != Highlight.EMPTY
                ) {
                    for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                        memory.setCorner(true, Corner.DIFFERENT, coords, diagonalDirection, false);
                    }
                }
            }
        }
    }
}
