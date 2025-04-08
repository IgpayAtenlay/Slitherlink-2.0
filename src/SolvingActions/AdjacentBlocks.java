package SolvingActions;

import Enums.CardinalDirection;
import Enums.Highlight;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.FullMemory;

public class AdjacentBlocks {
    static public void run(FullMemory memory) {
        System.out.println("starting " + AdjacentBlocks.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                if (memory.getNumbers().get(coords) == Number.THREE) {
                    doubleThrees(memory, coords);
                }

                if (memory.getHighlights().get(coords) == Highlight.EMPTY) {
                    createHighlight(memory, coords);
                }
                if (memory.getHighlights().get(coords) != Highlight.EMPTY) {
                    useHighlight(memory, coords);
                }
            }
        }

        System.out.println(AdjacentBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void doubleThrees(FullMemory memory, Coords coords) {
        for (CardinalDirection direction : new CardinalDirection[] {CardinalDirection.EAST, CardinalDirection.SOUTH}) {
            if (memory.getNumbers().get(coords.addDirection(direction)) == Number.THREE) {
                memory.change(memory.setLine(true, Line.LINE, coords, direction.getOpposite(), false));
                memory.change(memory.setLine(true, Line.LINE, coords, direction, false));
                memory.change(memory.setLine(true, Line.LINE, coords.addDirection(direction), direction, false));
            }
        }
    }
    static public void createHighlight(FullMemory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(coords.addDirection(direction));
            Line line = memory.getLine(true, coords, direction);
            if (line == Line.LINE) {
                memory.change(memory.getHighlights().set(adjacentHighlight.getOpposite(), coords, false));
            } else if (line == Line.X) {
                memory.change(memory.getHighlights().set(adjacentHighlight, coords, false));
            }
        }
    }
    static public void useHighlight(FullMemory memory, Coords coords) {
        Highlight currentHighlight = memory.getHighlights().get(coords);
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlights().get(coords.addDirection(direction));
            if (currentHighlight == adjacentHighlight) {
                memory.change(memory.setLine(true, Line.X, coords, direction, false));
            } else if (currentHighlight != Highlight.EMPTY && adjacentHighlight != Highlight.EMPTY) {
                memory.change(memory.setLine(true, Line.LINE, coords, direction, false));
            }
        }
    }
}
