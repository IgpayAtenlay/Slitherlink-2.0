package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class NumberErrors {
    public static boolean run(FullMemory memory) {
        System.out.println("starting " + NumberErrors.class.getSimpleName());

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                Number number = memory.getNumbers().get(x, y);
                if (number != Number.EMPTY) {
                    int lines = 0;
                    int xs = 0;
                    for (CardinalDirection direction : CardinalDirection.values()) {
                        if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                            lines++;
                        } else if (memory.getLines().getSquare(x, y, direction) == Line.X) {
                            xs++;
                        }
                    }
                    if (lines > memory.getNumbers().get(x, y).value ||
                            xs > 4 - memory.getNumbers().get(x, y).value
                    ) {
                        return true;
                    }
                }
            }
        }

        System.out.println(NumberErrors.class.getSimpleName() + " finished");
        return false;
    }
}
