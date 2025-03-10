package SolvingActions;

import Enums.CardinalDirection;
import Enums.Line;
import Memory.FullMemory;

public class GuessAndCheck {
    static public void run(FullMemory memory) {
        System.out.println("starting " + GuessAndCheck.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getXSize() + 1; x++) {
            for (int y = 0; y < memory.getYSize() + 1; y++) {
                for (CardinalDirection direction : new CardinalDirection[]{CardinalDirection.EAST, CardinalDirection.SOUTH}) {
                    if (memory.getLines().getSquare(x, y, direction) == Line.EMPTY) {
                        for (Line line : new Line[] {Line.LINE, Line.X}) {
                            guess(memory, x, y, direction, line);
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

    public static void guess(FullMemory memory, int x, int y, CardinalDirection direction, Line line) {
        System.out.println("guessing (" + x + ", " + y + ") " + direction + " " + line);
        FullMemory workingMemory = memory.copy();
        Control control = new Control(workingMemory);
        workingMemory.getLines().setSquare(line, x, y, direction);
        control.autoSolve(false);
        if (control.hasErrors()) {
            memory.change(memory.getLines().setSquare(line.getOpposite(), x, y, direction));
        } else if (control.isComplete()) {
            memory.change(memory.getLines().setSquare(line, x, y, direction));
        }
    }
}
