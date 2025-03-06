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
        int changes = 1;
        int roundNum = 0;
        while (changes != 0) {
            changes = autoSolve(roundNum++);
        }
        System.out.println("autoSolve finished");
    }
    public int autoSolve(int roundNum) {
        System.out.println("starting autoSolve round " + roundNum);
        int changes = 0;
        changes += SingleBlock.run(memory);
        changes += PointActions.run(memory);
        changes += AdjacentBlocks.run(memory);
        changes += AdjacentDiagonalBlocks.run(memory);
        changes += LineIntoBlock.run(memory);
        System.out.println("autoSolve round " + roundNum + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    public FullMemory getMemory() {
        return memory;
    }
}
