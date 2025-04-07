package Memory;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Number;

import java.util.Arrays;

public class NumberMemory {
    private final Dimentions dimentions;
    private final Number[] memory;
    private boolean locked;

    public NumberMemory(Dimentions dimentions, Number[] memory, boolean locked) {
        this.dimentions = dimentions;
        this.memory = memory;
        this.locked = locked;
    }
    public NumberMemory(Dimentions dimentions) {
        this(dimentions, new Number[dimentions.xSize * dimentions.ySize], false);
        Arrays.fill(memory, Number.EMPTY);
    }
    public NumberMemory copy(boolean locked) {
        return new NumberMemory(dimentions.copy(), Arrays.copyOf(memory, memory.length), locked);
    }
    public NumberMemory copy() {
        return new NumberMemory(dimentions.copy(), Arrays.copyOf(memory, memory.length), locked);
    }

    public void lock() {
        this.locked = true;
    }

    public Changes set(Number number, int x, int y, boolean override) {
        if (!locked && x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0 &&
                memory[x + y * dimentions.xSize] != number && (memory[x + y * dimentions.xSize] == Number.EMPTY || override)) {
            int i = x + y * dimentions.xSize;
            memory[i] = number;
            return new Changes(number, i);
        } else {
            return null;
        }
    }
    public Number get(int x, int y) {
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
            return memory[x + y * dimentions.xSize];
        } else {
            return Number.EMPTY;
        }
    }
    public Number get(int x, int y, CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> get(x, y - 1);
            case EAST -> get(x + 1, y);
            case SOUTH -> get(x, y + 1);
            case WEST -> get(x - 1, y);
        };
    }
    public Number get(int x, int y, DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST -> get(x + 1, y - 1);
            case SOUTHEAST -> get(x + 1, y + 1);
            case SOUTHWEST -> get(x - 1, y + 1);
            case NORTHWEST -> get(x - 1, y - 1);
        };
    }
    public Dimentions getDimentions() {
        return dimentions.copy();
    }
}
