package Memory;

import Enums.Diagonal;
import Enums.DiagonalDirection;

import java.util.Arrays;

public class DiagonalMemory {
    private final Dimentions dimentions;
    private final Diagonal[] memory;

    public DiagonalMemory(Dimentions dimentions, Diagonal[] memory) {
        this.dimentions = dimentions;
        this.memory = memory;
    }
    public DiagonalMemory(Dimentions dimentions) {
        this(dimentions, new Diagonal[(dimentions.xSize + 1) * (dimentions.ySize + 1) * 4]);
        Arrays.fill(memory, Diagonal.EMPTY);
    }
    public DiagonalMemory copy() {
        return new DiagonalMemory(dimentions.copy(), Arrays.copyOf(memory, memory.length));
    }
    
    public Diagonal getSquare(Coords coords, DiagonalDirection direction) {
        return getPoint(coords.squareToPoint(direction), direction.getOpposite());
    }
    public Diagonal getPoint(Coords coords, DiagonalDirection direction) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize + 1 && y < dimentions.ySize + 1 && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTHEAST -> memory[2 * x + 1 + 2 * y * getLengthRow()];
                case SOUTHEAST -> memory[2 * x + 1 + (2 * y + 1) * getLengthRow()];
                case SOUTHWEST -> memory[2 * x + (2 * y + 1) * getLengthRow()];
                case NORTHWEST -> memory[2 * x + 2 * y * getLengthRow()];
            };
        }
        return Diagonal.BOTH_OR_NEITHER;
    }
    public Changes setSquare(Diagonal diagonal, Coords coords, DiagonalDirection direction, boolean override) {
        return setPoint(diagonal,
                coords.squareToPoint(direction),
                direction.getOpposite(),
                override);
    }
    public Changes setPoint(Diagonal diagonal, Coords coords, DiagonalDirection direction, boolean override) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize + 1 && y < dimentions.ySize + 1 && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTHEAST -> set(diagonal, 2 * x + 1 + 2 * y * getLengthRow(), override);
                case SOUTHEAST -> set(diagonal, 2 * x + 1 + (2 * y + 1) * getLengthRow(), override);
                case SOUTHWEST -> set(diagonal, 2 * x + (2 * y + 1) * getLengthRow(), override);
                case NORTHWEST -> set(diagonal, 2 * x + 2 * y * getLengthRow(), override);
            };
        }
        return null;
    }
    private Changes set(Diagonal diagonal, int i, boolean override) {
        if (memory[i] != diagonal && (memory[i] == Diagonal.EMPTY || override)) {
//            System.out.println("changing diagonal " + i + " to " + diagonal);
            memory[i] = diagonal;
            return new Changes(diagonal, i);
        }
        return null;
    }
    
    private int getLengthRow() {
        return dimentions.xSize * 2 + 2;
    }
}
