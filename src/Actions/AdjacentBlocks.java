package Actions;

import Enums.CardinalDirection;
import Enums.Highlight;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class AdjacentBlocks {
    static public void run(FullMemory memory) {
        System.out.println("starting " + AdjacentBlocks.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                if (memory.getNumbers().get(x, y) == Number.THREE) {
                    doubleThrees(memory, x, y);
                }

                if (memory.getHighlights().get(x, y) == Highlight.EMPTY) {
                    createHighlight(memory, x, y);
                }
                if (memory.getHighlights().get(x, y) != Highlight.EMPTY) {
                    useHighlight(memory, x, y);
                }
            }
        }

        System.out.println(AdjacentBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void doubleThrees(FullMemory memory, int x, int y) {
        if (memory.getNumbers().get(x + 1, y) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.WEST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.EAST, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x + 1, y, CardinalDirection.EAST, false));
        }
        if (memory.getNumbers().get(x, y + 1) == Number.THREE) {
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.NORTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x, y, CardinalDirection.SOUTH, false));
            memory.change(memory.getLines().setSquare(Line.LINE, x, y + 1, CardinalDirection.SOUTH, false));
        }
    }
    static public void createHighlight(FullMemory memory, int x, int y) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(x, y, direction);
            Line line = memory.getLines().getSquare(x, y, direction);
            if (line == Line.LINE) {
                memory.change(memory.getHighlights().set(adjacentHighlight.getOpposite(), x, y, false));
            } else if (line == Line.X) {
                memory.change(memory.getHighlights().set(adjacentHighlight, x, y, false));
            }
        }
    }
    static public void useHighlight(FullMemory memory, int x, int y) {
        Highlight currentHighlight = memory.getHighlights().get(x, y);
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(x, y, direction);
            if (currentHighlight == adjacentHighlight) {
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction, false));
            } else if (currentHighlight != Highlight.EMPTY && adjacentHighlight != Highlight.EMPTY) {
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction, false));
            }
        }
    }
}
