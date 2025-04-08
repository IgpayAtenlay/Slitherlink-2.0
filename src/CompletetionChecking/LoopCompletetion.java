package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import ErrorChecking.LoopErrors;
import Memory.Coords;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class LoopCompletetion {
    static public boolean run(FullMemory memory) {
        System.out.println("starting " + LoopErrors.class.getSimpleName());

        int totalLines = memory.getLines().getTotalLines();

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                if (hasLine(memory, coords)) {
                    boolean isGoodLoop = isGoodLoop(memory, coords, totalLines);
                    System.out.println(LoopErrors.class.getSimpleName() + " finished");
                    return isGoodLoop;
                }
            }
        }

        System.out.println(LoopErrors.class.getSimpleName() + " finished");
        return true;
    }

    public static boolean hasLine(FullMemory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(coords, direction) == Line.LINE) {
                return true;
            }
        }
        return false;
    }
    public static boolean isGoodLoop(FullMemory memory, Coords start, int totalLines) {
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(start, direction) == Line.LINE) {
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
                if (memory.getLines().getPoint(currentCoord, direction) == Line.LINE) {
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
