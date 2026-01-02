package SolvingActions;

import Memory.Memory;

public class Control {
    public static int step = 0;
    public static void autoSolve(Memory memory, boolean guessAndCheck) {
//        System.out.println("starting autoSolve");
        int roundNum = 0;
        int startingChanges = memory.getNumChanges();
//        System.out.println(startingChanges);
        int roundChanges;
        do {
//        System.out.println("starting autoSolve round " + roundNum);
            roundChanges = memory.getNumChanges();
            step = 0;
            do {
                oneRoundAutosolve(memory, guessAndCheck);
            } while (step != 0);

//        System.out.println("autoSolve round " + roundNum++ + " finished");
//        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
        } while (roundChanges != memory.getNumChanges());

//        System.out.println("autoSolve finished");
//        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
    }

    public static void oneRoundAutosolve(Memory memory, boolean guessAndCheck) {
        int startingChanges = memory.getNumChanges();

//        System.out.println(step);
        switch (step) {
            case 0 -> SingleBlock.run(memory);
            case 1 -> PointActions.run(memory);
            case 2 -> AdjacentBlocks.run(memory);
            case 3 -> AdjacentDiagonalBlocks.run(memory);
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
                step = -1;
            }
        }
        step++;
    }
}
