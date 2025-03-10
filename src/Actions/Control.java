package Actions;

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
        int startingChanges;
        do {
            startingChanges = memory.getChanges().size();
            System.out.println("starting autoSolve round " + roundNum);
            int roundChanges = memory.getChanges().size();

            SingleBlock.run(memory);
            PointActions.run(memory);
            AdjacentBlocks.run(memory);
            AdjacentDiagonalBlocks.run(memory);
            LineIntoBlock.run(memory);
            if (startingChanges != memory.getChanges().size()) {
                Loop.run(memory);
            }
            if (startingChanges != memory.getChanges().size() && guessAndCheck) {
                GuessAndCheck.run(memory);
            }

            System.out.println("autoSolve round " + roundNum + " finished");
            System.out.println("changes: " + (memory.getChanges().size() - roundChanges));
        } while (startingChanges != memory.getChanges().size());


        System.out.println("autoSolve finished");
    }
    public void autoSolve() {
        autoSolve(true);
    }
    public void autoSolve(int roundNum) {

    }
    public boolean hasErrors() {
        if (NumberErrors.run(memory)) {
            return true;
        }
        if (LineErrors.run(memory)) {
            return true;
        }
        if (LoopErrors.run(memory)) {
            return true;
        }
        return false;
    }

    public FullMemory getMemory() {
        return memory;
    }
}
