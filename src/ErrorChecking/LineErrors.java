package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.FullMemory;

public class LineErrors {
    public static boolean run(FullMemory memory) {
        System.out.println("starting " + LineErrors.class.getSimpleName());

        for (int x = 0; x < memory.getXSize() + 1; x++) {
            for (int y = 0; y < memory.getYSize() + 1; y++) {
                int lines = 0;
                int xs = 0;
                for (CardinalDirection direction : CardinalDirection.values()) {
                    if (memory.getLines().getPoint(x, y, direction) == Line.LINE) {
                        lines++;
                    } else if (memory.getLines().getPoint(x, y, direction) == Line.X) {
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

        System.out.println(LineErrors.class.getSimpleName() + " finished");
        return false;
    }
}
