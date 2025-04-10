package SolvingActions;

import Memory.Dimentions;
import Memory.Memory;

public class Control {
    private final Memory memory;

    public Control(Memory memory) {
        this.memory = memory;
    }
    public Control() {
        this(new Memory());
    }
    public Control(int xSize, int ySize) {
        this(new Memory(new Dimentions(xSize, ySize)));
    }

    public void autoSolve(boolean guessAndCheck) {
        System.out.println("starting autoSolve");
        int roundNum = 0;
        int startingChanges = memory.getNumChanges();
        System.out.println(startingChanges);
        int roundChanges;
        do {
            System.out.println("starting autoSolve round " + roundNum);
            roundChanges = memory.getNumChanges();

            SingleBlock.run(memory);
            PointActions.run(memory);
            AdjacentBlocks.run(memory);
            AdjacentDiagonalBlocks.run(memory);
            LineIntoBlock.run(memory);
            CheckLoop.run(memory);
            if (roundChanges == memory.getNumChanges()) {
                Trapped.run(memory);
            }
            if (guessAndCheck) {
                if (roundChanges == memory.getNumChanges()) {
                    GuessAndCheck.run(memory);
                }
            }

            System.out.println("autoSolve round " + roundNum++ + " finished");
            System.out.println("changes: " + (memory.getNumChanges() - roundChanges));
        } while (roundChanges != memory.getNumChanges());


        System.out.println("autoSolve finished");
        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
    }

    public Memory getMemory() {
        return memory;
    }
}
