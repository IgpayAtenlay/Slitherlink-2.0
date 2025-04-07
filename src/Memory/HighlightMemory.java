package Memory;

import Enums.CardinalDirection;
import Enums.Highlight;

import java.util.Arrays;

public class HighlightMemory {
    private final Dimentions dimentions;
    private final Highlight[] memory;

    public HighlightMemory(Dimentions dimentions, Highlight[] memory) {
        this.dimentions = dimentions;
        this.memory = memory;
    }
    public HighlightMemory(Dimentions dimentions) {
        this(dimentions, new Highlight[dimentions.xSize * dimentions.ySize]);
        Arrays.fill(memory, Highlight.EMPTY);
    }
    public HighlightMemory copy() {
        return new HighlightMemory(dimentions.copy(), Arrays.copyOf(memory, memory.length));
    }

    public Changes set(Highlight highlight, int x, int y, boolean override) {
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0 &&
                (memory[x + y * dimentions.xSize] != highlight && (memory[x + y * dimentions.xSize] == Highlight.EMPTY || override))
        ) {
            int i = x + y * dimentions.xSize;
            memory[i] = highlight;
            return new Changes(highlight, i);
        }
        return null;
    }
    public Highlight get(int x, int y) {
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
            return memory[x + y * dimentions.xSize];
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
