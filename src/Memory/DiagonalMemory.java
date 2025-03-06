package Memory;

import Enums.Diagonal;
import Enums.DiagonalDirection;
import Util.ConvertCoordinates;

import java.util.Arrays;

public class DiagonalMemory {
    private final int xSize;
    private final int ySize;
    private final Diagonal[] memory;

    public DiagonalMemory(int xSize, int ySize, Diagonal[] memory) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.memory = memory;
    }
    public DiagonalMemory(int xSize, int ySize) {
        this(xSize, ySize, new Diagonal[(xSize + 1) * (ySize + 1) * 4]);
        Arrays.fill(memory, Diagonal.EMPTY);
    }
    public DiagonalMemory copy() {
        return new DiagonalMemory(xSize, ySize, Arrays.copyOf(memory, memory.length));
    }
    
    public Diagonal getSquare(int x, int y, DiagonalDirection direction) {
        return getPoint(ConvertCoordinates.squareToPointX(x, direction),
                ConvertCoordinates.squareToPointY(y, direction),
                direction.getOpposite());
    }
    public Diagonal getPoint(int x, int y, DiagonalDirection direction) {
        if (x < xSize + 1 && y < ySize + 1 && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTHEAST -> memory[2 * x + 1 + 2 * y * getLengthRow()];
                case SOUTHEAST -> memory[2 * x + 1 + (2 * y + 1) * getLengthRow()];
                case SOUTHWEST -> memory[2 * x + (2 * y + 1) * getLengthRow()];
                case NORTHWEST -> memory[2 * x + 2 * y * getLengthRow()];
            };
        }
        return Diagonal.BOTH_OR_NEITHER;
    }
    public Changes setSquare(Diagonal diagonal, int x, int y, DiagonalDirection direction, boolean override) {
        return setPoint(diagonal,
                ConvertCoordinates.squareToPointX(x, direction),
                ConvertCoordinates.squareToPointY(y, direction),
                direction.getOpposite(),
                override);
    }
    public Changes setPoint(Diagonal diagonal, int x, int y, DiagonalDirection direction, boolean override) {
        if (x < xSize + 1 && y < ySize + 1 && x >= 0 && y >= 0) {
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
            System.out.println("changing diagonal " + i + " to " + diagonal);
            memory[i] = diagonal;
            return new Changes(diagonal, i);
        }
        return null;
    }
    
    private int getLengthRow() {
        return xSize * 2 + 2;
    }
}
