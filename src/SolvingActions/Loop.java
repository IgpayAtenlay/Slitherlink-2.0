package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.FullMemory;

public class Loop {
    static public void run(FullMemory memory) {
        System.out.println("starting " + Loop.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        int totalLines = memory.getLines().getTotalLines();

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                checkLoop(memory, new Coords(x, y), totalLines);
            }
        }

        System.out.println(Loop.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }
    public static void checkLoop(FullMemory memory, Coords start, int totalLines) {
        CardinalDirection exit = isStartOfLoop(memory, start);

        // if isn't start of loop, return
        if (exit == null) {
            return;
        }

        // if not near end of another loop, return
        if (!isNearOtherLoop(memory, start)) {
            return;
        }

        // loop start
        int linesInLoop = 1;
        Coords currentCoord = start.addDirection(exit);
        CardinalDirection enterence = exit.getOpposite();
        boolean newLine = true;

        while(newLine) {
            newLine = false;
            int linesAtThisPoint = 0;
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (memory.getLines().getPoint(currentCoord, direction) == Line.LINE) {
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
                currentCoord = currentCoord.addDirection(exit);
            }
            enterence = exit.getOpposite();

            if (start.equals(currentCoord)) {
                System.out.println("Error: endless loop. Exiting");
                break;
            }
        }

        if (linesInLoop != totalLines) {
            if (start.x == currentCoord.x && start.y == currentCoord.y - 1) {
                memory.change(memory.getLines().setPoint(Line.X, start, CardinalDirection.SOUTH, false));
            } else if (start.x == currentCoord.x && start.y == currentCoord.y + 1) {
                memory.change(memory.getLines().setPoint(Line.X, start, CardinalDirection.NORTH, false));
            } else if (start.x == currentCoord.x - 1 && start.y == currentCoord.y) {
                memory.change(memory.getLines().setPoint(Line.X, start, CardinalDirection.EAST, false));
            } else if (start.x == currentCoord.x + 1 && start.y == currentCoord.y) {
                memory.change(memory.getLines().setPoint(Line.X, start, CardinalDirection.WEST, false));
            }
        }
    }

    public static CardinalDirection isStartOfLoop(FullMemory memory, Coords coords) {
        int linesInLoop = 0;
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(coords, direction) == Line.LINE) {
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

    public static boolean isNearOtherLoop(FullMemory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(coords, direction) == Line.EMPTY) {
                if (isStartOfLoop(memory, coords.addDirection(direction)) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
