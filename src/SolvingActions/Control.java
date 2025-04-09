package SolvingActions;

import Memory.Dimentions;
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
        this(new FullMemory(new Dimentions(xSize, ySize)));
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
                CheckLoop.run(memory);
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

    public FullMemory getMemory() {
        return memory;
    }
}
