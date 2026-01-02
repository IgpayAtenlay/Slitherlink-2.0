package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class Filled {
    public static boolean run(Memory memory) {
//        System.out.println("starting " + Filled.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                for (CardinalDirection direction : CardinalDirection.values()) {
                    if (memory.getLine(false, coords, direction) == Line.EMPTY) {
//                        System.out.println(Filled.class.getSimpleName() + " finished");
                        return false;
                    }
                }
            }
        }

//        System.out.println(Filled.class.getSimpleName() + " finished");
        return true;
    }
}
