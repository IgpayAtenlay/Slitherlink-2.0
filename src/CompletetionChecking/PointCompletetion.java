package CompletetionChecking;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.Coords;
import Memory.FullMemory;

public class PointCompletetion {
    public static boolean run(FullMemory memory) {
        System.out.println("starting " + PointCompletetion.class.getSimpleName());

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                int lines = 0;
                for (CardinalDirection direction : CardinalDirection.values()) {
                    if (memory.getLines().getPoint(coords, direction) == Line.LINE) {
                        lines++;
                    }
                }
                if (lines == 1 || lines > 2) {
                    System.out.println(PointCompletetion.class.getSimpleName() + " finished");
                    return false;
                }
            }
        }

        System.out.println(PointCompletetion.class.getSimpleName() + " finished");
        return true;
    }
}
