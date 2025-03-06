package Actions;

import Enums.CardinalDirection;
import Enums.Highlight;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class AdjacentBlocks {
    static public int run(FullMemory memory) {
        System.out.println("starting " + AdjacentBlocks.class.getSimpleName());

        int changes = 0;

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                Number num = memory.getNumbers().get(x, y);
                if (num == Number.THREE) {
                    changes += doubleThrees(memory, x, y);
                }

                if (memory.getHighlights().get(x, y) == Highlight.EMPTY) {
                    changes += createHighlight(memory, x, y);
                }
                if (memory.getHighlights().get(x, y) != Highlight.EMPTY) {
                    changes += useHighlight(memory, x, y);
                }
            }
        }

        System.out.println(AdjacentBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    static public int doubleThrees(FullMemory memory, int x, int y) {
        int changes = 0;
        if (memory.getNumbers().get(x + 1, y) == Number.THREE) {
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.EAST, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x + 1, y, CardinalDirection.EAST, false) ? 1 : 0;
        }
        if (memory.getNumbers().get(x, y + 1) == Number.THREE) {
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.NORTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.SOUTH, false) ? 1 : 0;
            changes += memory.getLines().setSquare(Line.LINE, x, y + 1, CardinalDirection.SOUTH, false) ? 1 : 0;
        }
        return changes;
    }
    static public int createHighlight(FullMemory memory, int x, int y) {
        int changes = 0;

        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(x, y, direction);
            Line line = memory.getLines().getSquare(x, y, direction);
            if (line == Line.LINE) {
                changes += memory.getHighlights().set(adjacentHighlight.getOpposite(), x, y, false) ? 1 : 0;
            } else if (line == Line.X) {
                changes += memory.getHighlights().set(adjacentHighlight, x, y, false) ? 1 : 0;
            }
        }

        return changes;
    }
    static public int useHighlight(FullMemory memory, int x, int y) {
        int changes = 0;

        Highlight currentHighlight = memory.getHighlights().get(x, y);
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(x, y, direction);
            if (currentHighlight == adjacentHighlight) {
                changes += memory.getLines().setSquare(Line.X, x, y, direction, false) ? 1 : 0;
            } else if (currentHighlight != Highlight.EMPTY && adjacentHighlight != Highlight.EMPTY) {
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction, false) ? 1 : 0;
            }
        }

        return changes;
    }
}
