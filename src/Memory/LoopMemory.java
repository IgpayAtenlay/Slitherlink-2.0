package Memory;

public class LoopMemory {
    private final Dimentions dimentions;
    private final Loop[] memory;

    public LoopMemory(Loop[] memory, Dimentions dimentions) {
        this.memory = memory;
        this.dimentions = dimentions;
    }
    public LoopMemory(Dimentions dimentions) {
        this(new Loop[(dimentions.xSize + 1) * (dimentions.ySize + 1)], dimentions);
    }
    public LoopMemory copy() {
        Loop[] memory = new Loop[this.memory.length];
        for (int i = 0; i < memory.length; i++) {
            if (this.memory[i] == null) {
                memory[i] = null;
            } else {
                memory[i] = this.memory[i].copy();
            }
        }
        return new LoopMemory(memory, dimentions);
    }

    public void setLoop(Coords coordOne, Coords coordTwo) {
        if (getIndex(coordOne) < 0 || getIndex(coordOne) >= memory.length || getIndex(coordTwo) < 0 || getIndex(coordTwo) >= memory.length) {
            return;
        }
        if (wouldCreateLoop(coordOne, coordTwo)) {
            memory[getIndex(coordOne)] = null;
            memory[getIndex(coordTwo)] = null;
            return;
        }

        Loop oneLoopEnd = memory[getIndex(coordOne)];
        Loop twoLoopEnd = memory[getIndex(coordTwo)];

        int length = 1;
        if (oneLoopEnd != null) {
            length += oneLoopEnd.length;
        }
        if (twoLoopEnd != null) {
            length += twoLoopEnd.length;
        }

        Loop newOneLoop = new Loop(twoLoopEnd == null ? coordTwo : twoLoopEnd.coords, length);
        Loop newTwoLoop = new Loop(oneLoopEnd == null ? coordOne : oneLoopEnd.coords, length);

        if (oneLoopEnd == null) {
            memory[getIndex(coordOne)] = newOneLoop;
        } else {
            memory[getIndex(coordOne)] = null;
            memory[getIndex(oneLoopEnd.coords)] = newOneLoop;
        }
        if (twoLoopEnd == null) {
            memory[getIndex(coordTwo)] = newTwoLoop;
        } else {
            memory[getIndex(coordTwo)] = null;
            memory[getIndex(twoLoopEnd.coords)] = newTwoLoop;
        }
    }
    public int getLoopLength(Coords coords) {
        return memory[getIndex(coords)].length;
    }
    public boolean wouldCreateLoop(Coords coordOne, Coords coordTwo) {
        if (getIndex(coordOne) < 0 || getIndex(coordOne) >= memory.length || getIndex(coordTwo) < 0 || getIndex(coordTwo) >= memory.length) {
            return false;
        }
        return memory[getIndex(coordOne)].coords.equals(coordTwo);
    }

    public int getIndex(Coords coords) {
        return coords.x + coords.y * (dimentions.xSize + 1);
    }
}
