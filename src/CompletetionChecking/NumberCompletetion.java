package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class NumberCompletetion {
    public static boolean run(Memory memory) {
        System.out.println("starting " + NumberCompletetion.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                Number number = memory.getNumber(coords);
                if (number != Number.EMPTY) {
                    int lines = 0;
                    for (CardinalDirection direction : CardinalDirection.values()) {
                        if (memory.getLine(true, coords, direction) == Line.LINE) {
                            lines++;
                        }
                    }
                    if (lines != memory.getNumber(coords).value) {
                        System.out.println(NumberCompletetion.class.getSimpleName() + " finished");
                        return false;
                    }
                }
            }
        }

        System.out.println(NumberCompletetion.class.getSimpleName() + " finished");
        return true;
    }
}
