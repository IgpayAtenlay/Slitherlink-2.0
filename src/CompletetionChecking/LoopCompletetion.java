package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class LoopCompletetion {
    static public boolean hasExactlyOneLoop(Memory memory) {
        int totalLines = memory.getNumLines();
        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                if (hasLine(memory, coords)) {
                    boolean isGoodLoop = isGoodLoop(memory, coords, totalLines);
                    return isGoodLoop;
                }
            }
        }
        return true;
    }

    public static boolean hasLine(Memory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(false, coords, direction) == Line.LINE) {
                return true;
            }
        }
        return false;
    }
    public static boolean isGoodLoop(Memory memory, Coords start, int totalLines) {
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(false, start, direction) == Line.LINE) {
                exit = direction;
            }
        }

        int linesInLoop = 1;
        Coords currentCoord = start.addDirection(exit);

        // loop start
        CardinalDirection enterence = exit.getOpposite();
        boolean newLine = true;

        while(newLine) {
            newLine = false;
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (memory.getLine(false, currentCoord, direction) == Line.LINE) {
                    if (enterence != direction) {
                        exit = direction;
                        newLine = true;
                        linesInLoop++;
                    }
                }
            }

            if (newLine) {
                currentCoord = currentCoord.addDirection(exit);
                enterence = exit.getOpposite();
            }

            if (start.equals(currentCoord)) {
                return linesInLoop == totalLines;
            }
        }

        return false;
    }
}
