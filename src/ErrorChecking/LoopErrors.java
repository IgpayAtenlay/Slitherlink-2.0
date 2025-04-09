package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.FullMemory;

public class LoopErrors {
    static public boolean run(FullMemory memory) {
        // this doesn't work
        System.out.println("starting " + LoopErrors.class.getSimpleName());

        int totalLines = memory.getNumLines();
        boolean[][] visited = new boolean[memory.getDimentions().xSize + 1][memory.getDimentions().ySize + 1];

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                if (!visited[x][y]) {
                    visited[x][y] = true;
                    if (isThisLoopProblem(memory, new Coords(x, y), totalLines, visited)) {
                        System.out.println(LoopErrors.class.getSimpleName() + " finished");
                        return true;
                    }
                }
            }
        }

        System.out.println(LoopErrors.class.getSimpleName() + " finished");
        return false;
    }
    public static boolean isThisLoopProblem(FullMemory memory, Coords start, int totalLines, boolean[][] visited) {
        int linesInLoop = 0;
        CardinalDirection exit = CardinalDirection.NORTH;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(false, start, direction) == Line.LINE) {
                exit = direction;
                linesInLoop++;
            }
        }

        Coords currentCoord = start.addDirection(exit);

        // loop start
        if (linesInLoop == 2) {
            linesInLoop--;
            visited[currentCoord.x][currentCoord.y] = true;
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
                    return linesInLoop != totalLines;
                }
            }
        }

        return false;
    }
}
