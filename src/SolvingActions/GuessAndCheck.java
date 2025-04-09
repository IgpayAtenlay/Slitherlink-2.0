package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.FullMemory;

public class GuessAndCheck {
    static public void run(FullMemory memory) {
        System.out.println("starting " + GuessAndCheck.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize + 1; x++) {
            for (int y = 0; y < memory.getDimentions().ySize + 1; y++) {
                Coords coords = new Coords(x, y);
                for (CardinalDirection direction : new CardinalDirection[]{CardinalDirection.EAST, CardinalDirection.SOUTH}) {
                    if (memory.getLine(true, coords, direction) == Line.EMPTY) {
                        for (Line line : new Line[] {Line.LINE, Line.X}) {
                            guess(memory, coords, direction, line);
                            if (memory.getChanges().size() - startingChanges > 0) {
                                System.out.println(GuessAndCheck.class.getSimpleName() + " finished");
                                System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
                                return;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(GuessAndCheck.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void guess(FullMemory memory, Coords coords, CardinalDirection direction, Line line) {
//        System.out.println("guessing " + coords + " " + direction + " " + line);
        FullMemory workingMemory = memory.copy();
        Control control = new Control(workingMemory);
        workingMemory.setLine(true, line, coords, direction, false);
        if (Errors.hasErrors(workingMemory)) {
            memory.change(memory.setLine(true, line.getOpposite(), coords, direction, false));
            return;
        } else if (Errors.isComplete(workingMemory)) {
            memory.change(memory.setLine(true, line, coords, direction, false));
            return;
        }
        control.autoSolve(false);
        if (Errors.hasErrors(workingMemory)) {
            memory.change(memory.setLine(true, line.getOpposite(), coords, direction, false));
            return;
        } else if (Errors.isComplete(workingMemory)) {
            memory.change(memory.setLine(true, line, coords, direction, false));
            return;
        }
    }
}
