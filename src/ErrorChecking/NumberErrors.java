package ErrorChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class NumberErrors {
    public static boolean run(Memory memory) {
//        System.out.println("starting " + NumberErrors.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                Number number = memory.getNumber(coords);
                if (number != Number.EMPTY) {
                    int lines = 0;
                    int xs = 0;
                    for (CardinalDirection direction : CardinalDirection.values()) {
                        if (memory.getLine(true, coords, direction) == Line.LINE) {
                            lines++;
                        } else if (memory.getLine(true, coords, direction) == Line.X) {
                            xs++;
                        }
                    }
                    if (lines > memory.getNumber(coords).value ||
                            xs > 4 - memory.getNumber(coords).value
                    ) {
                        return true;
                    }
                }
            }
        }

//        System.out.println(NumberErrors.class.getSimpleName() + " finished");
        return false;
    }
}
