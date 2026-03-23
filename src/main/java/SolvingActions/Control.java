package SolvingActions;

import Memory.Memory;

public class Control {
    public static void autoSolve(Memory memory, boolean guessAndCheck) {
        int roundChanges;
        do {
            roundChanges = memory.getNumChanges();
            int step = 0;
            do {
                step = oneRoundAutosolve(memory, guessAndCheck, step);
            } while (step != 0);
        } while (roundChanges != memory.getNumChanges());
    }

    public static int oneRoundAutosolve(Memory memory, boolean guessAndCheck, int step) {
        int startingChanges = memory.getNumChanges();

        switch (step) {
            case 0 -> SingleBlock.run(memory);
            case 1 -> PointActions.run(memory);
            case 2 -> AdjacentBlocks.run(memory);
            case 3 -> DiagonalBlocks.run(memory);
            default -> {
                LineIntoBlock.run(memory);
                CheckLoop.run(memory);
                if (startingChanges == memory.getNumChanges()) {
                    Trapped.run(memory);
                }
                if (guessAndCheck) {
                    if (startingChanges == memory.getNumChanges()) {
                        GuessAndCheck.run(memory);
                    }
                }
                return 0;
            }
        }
        return step + 1;
    }
}
