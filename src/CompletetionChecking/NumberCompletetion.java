package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class NumberCompletetion {
    public static boolean run(FullMemory memory) {
        System.out.println("starting " + NumberCompletetion.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Number number = memory.getNumbers().get(x, y);
                if (number != Number.EMPTY) {
                    int lines = 0;
                    for (CardinalDirection direction : CardinalDirection.values()) {
                        if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                            lines++;
                        }
                    }
                    if (lines != memory.getNumbers().get(x, y).value) {
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
