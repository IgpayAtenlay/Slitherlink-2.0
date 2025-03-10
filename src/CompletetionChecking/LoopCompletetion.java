package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import ErrorChecking.LoopErrors;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class LoopCompletetion {
    static public boolean run(FullMemory memory) {
        System.out.println("starting " + LoopErrors.class.getSimpleName());

        int totalLines = memory.getLines().getTotalLines();

        for (int x = 0; x < memory.getXSize() + 1; x++) {
            for (int y = 0; y < memory.getYSize() + 1; y++) {
                if (hasLine(memory, x, y)) {
                    boolean isGoodLoop = isGoodLoop(memory, x, y, totalLines);
                    System.out.println(LoopErrors.class.getSimpleName() + " finished");
                    return isGoodLoop;
                }
            }
        }

        System.out.println(LoopErrors.class.getSimpleName() + " finished");
        return true;
    }

    public static boolean hasLine(FullMemory memory, int x, int y) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(x, y, direction) == Line.LINE) {
                return true;
            }
        }
        return false;
    }
    public static boolean isGoodLoop(FullMemory memory, int startingX, int startingY, int totalLines) {
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(startingX, startingY, direction) == Line.LINE) {
                exit = direction;
            }
        }

        int linesInLoop = 1;
        int currentX = startingX;
        int currentY = startingY;
        currentX = ConvertCoordinates.addDirection(currentX, currentY, exit)[0];
        currentY = ConvertCoordinates.addDirection(currentX, currentY, exit)[1];

        // loop start
        CardinalDirection enterence = exit.getOpposite();
        boolean newLine = true;

        while(newLine) {
            newLine = false;
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (memory.getLines().getPoint(currentX, currentY, direction) == Line.LINE) {
                    if (enterence != direction) {
                        exit = direction;
                        newLine = true;
                        linesInLoop++;
                    }
                }
            }

            if (newLine) {
                currentX = ConvertCoordinates.addDirection(currentX, currentY, exit)[0];
                currentY = ConvertCoordinates.addDirection(currentX, currentY, exit)[1];
                enterence = exit.getOpposite();
            }

            if (startingX == currentX && startingY == currentY) {
                return linesInLoop == totalLines;
            }
        }

        return false;
    }
}
