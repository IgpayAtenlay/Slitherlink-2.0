package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;
import Memory.Loop;

public class CheckLoop {
    static public void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allPointCoords()) {
            checkLoop(memory, coords);
        }
    }
    public static void checkLoop(Memory memory, Coords coords) {
        Loop loop = memory.getLoop(coords);
        if (loop != null && memory.getNumOfLines() != loop.length && loop.length != 1) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (loop.endPoint.equals(coords.addDirection(direction))) {
                    memory.setLine(false, Line.X, coords, direction, false);
                }
            }
        }
    }
}
