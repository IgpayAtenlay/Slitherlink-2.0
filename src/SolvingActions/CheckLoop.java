package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;
import Memory.Loop;

public class CheckLoop {
    static public void run(Memory memory) {
        System.out.println("starting " + CheckLoop.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        int totalLines = memory.getNumLines();

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                checkLoop(memory, new Coords(x, y), totalLines);
            }
        }

        System.out.println(CheckLoop.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }
    public static void checkLoop(Memory memory, Coords coords, int totalLines) {
        Loop loop = memory.getLoop(coords);
        if (loop != null && totalLines != loop.length && loop.length != 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (loop.coords.equals(coords.addDirection(direction))) {
                    memory.change(memory.setLine(false, Line.X, coords, direction, false));
                }
            }
        }
    }
}
