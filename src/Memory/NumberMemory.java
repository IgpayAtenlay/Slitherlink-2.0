package Memory;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Number;

import java.util.Arrays;

public class NumberMemory {
    private final int xSize;
    private final int ySize;
    private final Number[] memory;
    private boolean locked;

    public NumberMemory(int xSize, int ySize, Number[] memory, boolean locked) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.memory = memory;
        this.locked = locked;
    }
    public NumberMemory(int xSize, int ySize) {
        this(xSize, ySize, new Number[xSize * ySize], false);
        Arrays.fill(memory, Number.EMPTY);
    }
    public NumberMemory copy(boolean locked) {
        return new NumberMemory(xSize, ySize, Arrays.copyOf(memory, memory.length), locked);
    }
    public NumberMemory copy() {
        return new NumberMemory(xSize, ySize, Arrays.copyOf(memory, memory.length), locked);
    }

    public void lock() {
        this.locked = true;
    }

    public boolean set(Number number, int x, int y, boolean override) {
        if (!locked && x < xSize && y < ySize && x >= 0 && y >= 0 &&
                memory[x + y * xSize] != number && (memory[x + y * xSize] == Number.EMPTY || override)) {
            memory[x + y * xSize] = number;
            return true;
        } else {
            return false;
        }
    }
    public Number get(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return memory[x + y * xSize];
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
    public int getXSize() {
        return xSize;
    }
    public int getYSize() {
        return ySize;
    }
}
