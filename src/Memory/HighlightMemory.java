package Memory;

import Enums.CardinalDirection;
import Enums.Highlight;

import java.util.Arrays;

public class HighlightMemory {
    private final int xSize;
    private final int ySize;
    private final Highlight[] memory;

    public HighlightMemory(int xSize, int ySize, Highlight[] memory) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.memory = memory;
    }
    public HighlightMemory(int xSize, int ySize) {
        this(xSize, ySize, new Highlight[xSize * ySize]);
        Arrays.fill(memory, Highlight.EMPTY);
    }
    public HighlightMemory copy() {
        return new HighlightMemory(xSize, ySize, Arrays.copyOf(memory, memory.length));
    }

    public Changes set(Highlight highlight, int x, int y, boolean override) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0 &&
                (memory[x + y * xSize] != highlight && (memory[x + y * xSize] == Highlight.EMPTY || override))
        ) {
            int i = x + y * xSize;
            memory[i] = highlight;
            return new Changes(highlight, i);
        }
        return null;
    }
    public Highlight get(int x, int y) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return memory[x + y * xSize];
        } else {
            return Highlight.OUTSIDE;
        }
    }
    public Highlight get(int x, int y, CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> get(x, y - 1);
            case EAST -> get(x + 1, y);
            case SOUTH -> get(x, y + 1);
            case WEST -> get(x - 1, y);
        };
    }
}
