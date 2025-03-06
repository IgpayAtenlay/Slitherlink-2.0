package Memory;

import Enums.CardinalDirection;
import Enums.Line;

import java.util.Arrays;

public class LineMemory {
    private final int xSize;
    private final int ySize;
    private final Line[] memory;

    public LineMemory(int xSize, int ySize, Line[] memory) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.memory = memory;
    }
    public LineMemory(int xSize, int ySize) {
        this(xSize, ySize, new Line[xSize * (ySize + 1) + ySize * (xSize + 1)]);
        Arrays.fill(memory, Line.EMPTY);
    }
    public LineMemory copy() {
        return new LineMemory(xSize, ySize, Arrays.copyOf(memory, memory.length));
    }

    private int numHorzLines() {
        return xSize * (ySize + 1);
    }

    public Line getSquare(int x, int y, CardinalDirection direction) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTH -> memory[x + y * xSize];
                case EAST -> memory[numHorzLines() + (x + 1) + y * (xSize + 1)];
                case SOUTH -> memory[x + (y + 1) * xSize];
                case WEST -> memory[numHorzLines() + x + y * (xSize + 1)];
            };
        } else if (x == -1 && y < ySize && y >= 0) {
            if (direction == CardinalDirection.EAST) {
                return memory[numHorzLines() + y * (xSize + 1)];
            } else {
                return Line.X;
            }
        } else if (x < xSize && x >= 0 && y == -1) {
            if (direction == CardinalDirection.SOUTH) {
                return memory[x];
            } else {
                return Line.X;
            }
        } else if (x == xSize && y < ySize && y >= 0) {
            if (direction == CardinalDirection.WEST) {
                return memory[numHorzLines() + x + y * (xSize + 1)];
            } else {
                return Line.X;
            }
        } else if (x < xSize && x >= 0 && y == ySize) {
            if (direction == CardinalDirection.NORTH) {
                return memory[x + y * xSize];
            } else {
                return Line.X;
            }
        } else {
            return Line.X;
        }
    }
    public Line getPoint(int x, int y, CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> getSquare(x, y - 1, CardinalDirection.WEST);
            case EAST -> getSquare(x, y, CardinalDirection.NORTH);
            case SOUTH -> getSquare(x, y, CardinalDirection.WEST);
            case WEST -> getSquare(x - 1, y, CardinalDirection.NORTH);
        };
    }
    public boolean setSquare(Line line, int x, int y, CardinalDirection direction, boolean override) {
        if (x < xSize && y < ySize && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTH -> set(line, x + y * xSize, override);
                case EAST -> set(line, numHorzLines() + (x + 1) + y * (xSize + 1), override);
                case SOUTH -> set(line, x + (y + 1) * xSize, override);
                case WEST -> set(line, numHorzLines() + x + y * (xSize + 1), override);
            };
        } else if (x == -1 && y < ySize && y >= 0) {
            if (direction == CardinalDirection.EAST) {
                return set(line, numHorzLines() + y * (xSize + 1), override);
            }
        } else if (x == xSize && y < ySize && y >= 0) {
            if (direction == CardinalDirection.WEST) {
                return set(line, numHorzLines() + x + y * (xSize + 1), override);
            }
        } else if (y == -1 && x < xSize && x >= 0) {
            if (direction == CardinalDirection.SOUTH) {
                return set(line, x, override);
            }
        } else if (y == ySize && x < xSize && x >= 0) {
            if (direction == CardinalDirection.NORTH) {
                return set(line, x + y * xSize, override);
            }
        }
        return false;
    }
    public boolean setSquare(Line line, int x, int y, CardinalDirection direction) {
        return setSquare(line, x, y, direction, false);
    }
    public boolean setPoint(Line line, int x, int y, CardinalDirection direction, boolean override) {
        return switch (direction) {
            case NORTH -> setSquare(line, x, y - 1, CardinalDirection.WEST, override);
            case EAST -> setSquare(line, x, y, CardinalDirection.NORTH, override);
            case SOUTH -> setSquare(line, x, y, CardinalDirection.WEST, override);
            case WEST -> setSquare(line, x - 1, y, CardinalDirection.NORTH, override);
        };
    }
    public boolean setPoint(Line line, int x, int y, CardinalDirection direction) {
        return setPoint(line, x, y, direction, false);
    }
    private boolean set(Line line, int i, boolean override) {
        if (memory[i] != line && (memory[i] == Line.EMPTY || override)) {
            memory[i] = line;
            System.out.println("changing line " + i + " to " + line);
            return true;
        }
        return false;
    }
}
