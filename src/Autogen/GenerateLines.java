package Autogen;

import Enums.CardinalDirection;
import Enums.Highlight;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class GenerateLines {
    public static void generate(Memory puzzle) {
        for (int x = 0; x < puzzle.getDimentions().xSize; x++) {
            for (int y = 0; y < puzzle.getDimentions().ySize; y++) {
                Coords currentCoords = new Coords(x, y);
                Highlight currentHighlight = puzzle.getHighlight(currentCoords);
                for (CardinalDirection direction : CardinalDirection.values()) {
                    Highlight otherSquareHighlight = puzzle.getHighlight(currentCoords.addDirection(direction));
                    if (currentHighlight == otherSquareHighlight) {
                        puzzle.setLine(true, Line.X, currentCoords, direction, true);
                    } else if (currentHighlight.getOpposite() == otherSquareHighlight) {
                        puzzle.setLine(true, Line.LINE, currentCoords, direction, true);
                    }
                }
            }
        }
    }
}
