package Actions;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class Loop {
    static public void run(FullMemory memory) {
        System.out.println("starting " + Loop.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        int totalLines = memory.getLines().getTotalLines();

        for (int x = 0; x < memory.getXSize() + 1; x++) {
            for (int y = 0; y < memory.getYSize() + 1; y++) {
                checkLoop(memory, x, y, totalLines);
            }
        }

        System.out.println(Loop.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }
    public static void checkLoop(FullMemory memory, int startingX, int startingY, int totalLines) {
        CardinalDirection exit = isStartOfLoop(memory, startingX, startingY);

        // if isn't start of loop, return
        if (exit == null) {
            return;
        }

        // if not near end of another loop, return
        if (!isNearOtherLoop(memory, startingX, startingY)) {
            return;
        }

        // loop start
        int linesInLoop = 1;
        int currentX = ConvertCoordinates.addDirection(startingX, startingY, exit)[0];
        int currentY = ConvertCoordinates.addDirection(startingX, startingY, exit)[1];
        CardinalDirection enterence = exit.getOpposite();
        boolean newLine = true;

        while(newLine) {
            newLine = false;
            int linesAtThisPoint = 0;
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (memory.getLines().getPoint(currentX, currentY, direction) == Line.LINE) {
                    if (enterence != direction) {
                        exit = direction;
                        newLine = true;
                        linesInLoop++;
                    }
                    linesAtThisPoint++;
                }
            }
            if (linesAtThisPoint > 2) {
                return;
            }

            if (newLine) {
                currentX = ConvertCoordinates.addDirection(currentX, currentY, exit)[0];
                currentY = ConvertCoordinates.addDirection(currentX, currentY, exit)[1];
            }
            enterence = exit.getOpposite();

            if (startingX == currentX && startingY == currentY) {
                System.out.println("Error: endless loop. Exiting");
                break;
            }
        }

        if (linesInLoop != totalLines) {
            if (startingX == currentX && startingY == currentY - 1) {
                memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.SOUTH, false);
            } else if (startingX == currentX && startingY == currentY + 1) {
                memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.NORTH, false);
            } else if (startingX == currentX - 1 && startingY == currentY) {
                memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.EAST, false);
            } else if (startingX == currentX + 1 && startingY == currentY) {
                memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.WEST, false);
            }
        }
    }

    public static CardinalDirection isStartOfLoop(FullMemory memory, int x, int y) {
        int linesInLoop = 0;
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(x, y, direction) == Line.LINE) {
                exit = direction;
                linesInLoop++;
            }
        }

        if (linesInLoop == 1) {
            return exit;
        } else {
            return null;
        }
    }

    public static boolean isNearOtherLoop(FullMemory memory, int x, int y) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(x, y, direction) == Line.EMPTY) {
                if (isStartOfLoop(memory,
                        ConvertCoordinates.addDirection(x, y, direction)[0],
                        ConvertCoordinates.addDirection(x, y, direction)[1]
                ) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
