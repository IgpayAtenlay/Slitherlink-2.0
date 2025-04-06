package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.FullMemory;
import Util.ConvertCoordinates;

public class LoopErrors {
    static public boolean run(FullMemory memory) {
        // this doesn't work
        System.out.println("starting " + LoopErrors.class.getSimpleName());

        int totalLines = memory.getLines().getTotalLines();
        boolean[][] visited = new boolean[memory.getDimentions().xSize + 1][memory.getDimentions().ySize + 1];

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                if (!visited[x][y]) {
                    visited[x][y] = true;
                    if (isThisLoopProblem(memory, x, y, totalLines, visited)) {
                        System.out.println(LoopErrors.class.getSimpleName() + " finished");
                        return true;
                    }
                }
            }
        }

        System.out.println(LoopErrors.class.getSimpleName() + " finished");
        return false;
    }
    public static boolean isThisLoopProblem(FullMemory memory, int startingX, int startingY, int totalLines, boolean[][] visited) {
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
        if (linesInLoop == 2) {
            visited[currentX][currentY] = true;
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
                    return linesInLoop != totalLines;
                }
            }
        }

        return false;
    }
}
