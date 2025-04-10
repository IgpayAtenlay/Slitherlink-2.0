package SolvingActions;

import Enums.*;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class AdjacentBlocks {
    static public void run(Memory memory) {
        System.out.println("starting " + AdjacentBlocks.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                switch (memory.getNumber(coords)) {
                    case THREE -> doubleThrees(memory, coords);
                    case TWO -> twoSandwich(memory, coords);
                }

                if (memory.getHighlight(coords) == Highlight.EMPTY) {
                    createHighlight(memory, coords);
                }
                if (memory.getHighlight(coords) != Highlight.EMPTY) {
                    useHighlight(memory, coords);
                }
            }
        }

        System.out.println(AdjacentBlocks.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    static public void doubleThrees(Memory memory, Coords coords) {
        for (CardinalDirection direction : new CardinalDirection[] {CardinalDirection.EAST, CardinalDirection.SOUTH}) {
            if (memory.getNumber(coords.addDirection(direction)) == Number.THREE) {
                memory.change(memory.setLine(true, Line.LINE, coords, direction.getOpposite(), false));
                memory.change(memory.setLine(true, Line.LINE, coords, direction, false));
                memory.change(memory.setLine(true, Line.LINE, coords.addDirection(direction), direction, false));
                memory.change(memory.setLine(true, Line.X, coords.addDirection(direction.getClockwise()), direction, false));
                memory.change(memory.setLine(true, Line.X, coords.addDirection(direction.getCounterClockwise()), direction, false));
            }
        }
    }
    static public void createHighlight(Memory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlight(coords.addDirection(direction));
            Line line = memory.getLine(true, coords, direction);
            if (line == Line.LINE) {
                memory.change(memory.setHighlight(adjacentHighlight.getOpposite(), coords, false));
            } else if (line == Line.X) {
                memory.change(memory.setHighlight(adjacentHighlight, coords, false));
            }
        }
    }
    static public void useHighlight(Memory memory, Coords coords) {
        Highlight currentHighlight = memory.getHighlight(coords);
        for (CardinalDirection direction : CardinalDirection.values()) {
            Highlight adjacentHighlight = memory.getHighlight(coords.addDirection(direction));
            if (currentHighlight == adjacentHighlight) {
                memory.change(memory.setLine(true, Line.X, coords, direction, false));
            } else if (currentHighlight != Highlight.EMPTY && adjacentHighlight != Highlight.EMPTY) {
                memory.change(memory.setLine(true, Line.LINE, coords, direction, false));
            }
        }
    }
    static public void twoSandwich(Memory memory, Coords coords) {
        for (CardinalDirection direction : new CardinalDirection[] {CardinalDirection.EAST, CardinalDirection.SOUTH}) {
            if (
                    memory.getHighlight(coords.addDirection(direction)) == memory.getHighlight(coords.addDirection(direction.getOpposite())) &&
                    memory.getHighlight(coords.addDirection(direction)) != Highlight.EMPTY
            ) {
                for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                    memory.change(memory.setDiagonal(true, Diagonal.EXACTLY_ONE, coords, diagonalDirection, false));
                }
            }
        }
    }
}
