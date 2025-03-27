package SolvingActions;

import CompletetionChecking.LoopCompletetion;
import CompletetionChecking.NumberCompletetion;
import CompletetionChecking.PointCompletetion;
import ErrorChecking.LineErrors;
import ErrorChecking.LoopErrors;
import ErrorChecking.NumberErrors;
import Memory.FullMemory;

public class Control {
    private final FullMemory memory;

    public Control(FullMemory memory) {
        this.memory = memory;
    }
    public Control() {
        this(new FullMemory());
    }
    public Control(int xSize, int ySize) {
        this(new FullMemory(xSize, ySize));
    }

    public void autoSolve(boolean guessAndCheck) {
        System.out.println("starting autoSolve");
        int roundNum = 0;
        int startingChanges = memory.getChanges().size();
        System.out.println(startingChanges);
        int roundChanges;
        do {
            System.out.println("starting autoSolve round " + roundNum);
            roundChanges = memory.getChanges().size();

            SingleBlock.run(memory);
            PointActions.run(memory);
            AdjacentBlocks.run(memory);
            AdjacentDiagonalBlocks.run(memory);
            LineIntoBlock.run(memory);
            if (roundChanges == memory.getChanges().size()) {
                Loop.run(memory);
            }
            if (guessAndCheck) {
                if (roundChanges == memory.getChanges().size()) {
                    GuessAndCheck.run(memory);
                }
            }

            System.out.println("autoSolve round " + roundNum++ + " finished");
            System.out.println("changes: " + (memory.getChanges().size() - roundChanges));
        } while (roundChanges != memory.getChanges().size());


        System.out.println("autoSolve finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }
    public boolean hasErrors() {
        if (NumberErrors.run(memory)) {
            System.out.println("number error");
            return true;
        }
        if (LineErrors.run(memory)) {
            System.out.println("line error");
            return true;
        }
        if (LoopErrors.run(memory)) {
            System.out.println("loop error");
            return true;
        }
        System.out.println("No errors");
        return false;
    }
    public boolean isComplete() {
        if (!NumberCompletetion.run(memory)) {
            return false;
        }
        if (!PointCompletetion.run(memory)) {
            return false;
        }
        if (!LoopCompletetion.run(memory)) {
            return false;
        }
        return true;
    }

    public FullMemory getMemory() {
        return memory;
    }
}
