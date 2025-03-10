package Actions;

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

    public void autoSolve() {
        System.out.println("starting autoSolve");
        int roundNum = 0;
        int startingChanges;
        do {
            startingChanges = memory.getChanges().size();
            autoSolve(roundNum++);
        } while (startingChanges != memory.getChanges().size());
        System.out.println("autoSolve finished");
    }
    public void autoSolve(int roundNum) {
        System.out.println("starting autoSolve round " + roundNum);
        int startingChanges = memory.getChanges().size();

        SingleBlock.run(memory);
        PointActions.run(memory);
        AdjacentBlocks.run(memory);
        AdjacentDiagonalBlocks.run(memory);
        LineIntoBlock.run(memory);
        Loop.run(memory);

        System.out.println("autoSolve round " + roundNum + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public FullMemory getMemory() {
        return memory;
    }
}
