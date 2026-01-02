package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;

public class LineErrors {
    public static boolean run(Memory memory) {
//        System.out.println("starting " + LineErrors.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                int lines = 0;
                int xs = 0;
                for (CardinalDirection direction : CardinalDirection.values()) {
                    if (memory.getLine(false, coords, direction) == Line.LINE) {
                        lines++;
                    } else if (memory.getLine(false, coords, direction) == Line.X) {
                        xs++;
                    }
                }
                if (lines > 2 ||
                        (xs == 3 && lines == 1)
                ) {
                    return true;
                }
            }
        }

//        System.out.println(LineErrors.class.getSimpleName() + " finished");
        return false;
    }
}
