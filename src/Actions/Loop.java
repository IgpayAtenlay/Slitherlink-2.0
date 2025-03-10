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
        int linesInLoop = 0;
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getPoint(startingX, startingY, direction) == Line.LINE) {
                exit = direction;
                linesInLoop++;
            }
        }

        int currentX = startingX;
        int currentY = startingY;
        currentX = ConvertCoordinates.addDirection(currentX, currentY, exit)[0];
        currentY = ConvertCoordinates.addDirection(currentX, currentY, exit)[1];

        // loop start
        if (linesInLoop == 1) {
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
                    System.out.println("Error: endless loop. Exiting");
                    break;
                }
            }

            if (linesInLoop != totalLines) {
                if (startingX == currentX && startingY == currentY - 1) {
                    memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.SOUTH);
                } else if (startingX == currentX && startingY == currentY + 1) {
                    memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.NORTH);
                } else if (startingX == currentX - 1 && startingY == currentY) {
                    memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.EAST);
                } else if (startingX == currentX + 1 && startingY == currentY) {
                    memory.getLines().setPoint(Line.X, startingX, startingY, CardinalDirection.WEST);
                }
            }
        }
    }
}
