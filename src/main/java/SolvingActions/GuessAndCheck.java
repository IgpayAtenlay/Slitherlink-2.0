package SolvingActions;

import CompletetionChecking.Complete;
import Enums.CardinalDirection;
import Enums.Line;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.Memory;

public class GuessAndCheck {
    static public void run(Memory memory) {
//        System.out.println("starting " + GuessAndCheck.class.getSimpleName());
        int startingChanges = memory.getNumChanges();

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                for (CardinalDirection direction : new CardinalDirection[]{CardinalDirection.EAST, CardinalDirection.SOUTH}) {
                    if (memory.getLine(true, coords, direction) == Line.EMPTY) {
                        for (Line line : new Line[] {Line.LINE, Line.X}) {
                            guess(memory, coords, direction, line);
                            if (memory.getNumChanges() - startingChanges > 0) {
//                                System.out.println(GuessAndCheck.class.getSimpleName() + " finished");
//                                System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
                                return;
                            }
                        }
                    }
                }
            }
        }

//        System.out.println(GuessAndCheck.class.getSimpleName() + " finished");
//        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
    }

    public static void guess(Memory memory, Coords coords, CardinalDirection direction, Line line) {
//        System.out.println("guessing " + coords + " " + direction + " " + line);
        Memory workingMemory = memory.copy();
        workingMemory.setLine(true, line, coords, direction, false);
        if (Errors.hasErrors(workingMemory)) {
            memory.setLine(true, line.getOpposite(), coords, direction, false);
            return;
        } else if (Complete.isComplete(workingMemory)) {
            memory.setLine(true, line, coords, direction, false);
            return;
        }
        Control.autoSolve(workingMemory, false);
        if (Errors.hasErrors(workingMemory)) {
            memory.setLine(true, line.getOpposite(), coords, direction, false);
            return;
        } else if (Complete.isComplete(workingMemory)) {
            memory.setLine(true, line, coords, direction, false);
            return;
        }
    }
}
