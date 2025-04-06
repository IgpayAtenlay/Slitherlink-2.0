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
    public int getTotalLines() {
        int totalLines = 0;
        for (Line line : memory) {
            if (line == Line.LINE) {
                totalLines++;
            }
        }

        return totalLines;
    }
    public Changes setSquare(Line line, int x, int y, CardinalDirection direction, boolean override) {
        return set(line, getIndex(true, new Coords(x, y), direction, new Dimentions(xSize, ySize)), override);
    }
    public Changes setSquare(Line line, int x, int y, CardinalDirection direction) {
        return setSquare(line, x, y, direction, false);
    }
    public Changes setPoint(Line line, int x, int y, CardinalDirection direction, boolean override) {
        return set(line, getIndex(false, new Coords(x, y), direction, new Dimentions(xSize, ySize)), override);
    }
    public static int getIndex(boolean square, Coords coords, CardinalDirection direction, Dimentions dimention) {
        int x = coords.x;
        int y = coords.y;
        int xSize = dimention.xSize;
        int ySize = dimention.ySize;
        int numHorzLines = xSize * (ySize + 1);
        if (square) {
            if (x < xSize && y < ySize && x >= 0 && y >= 0) {
                return switch (direction) {
                    case NORTH -> x + y * xSize;
                    case EAST -> numHorzLines + (x + 1) + y * (xSize + 1);
                    case SOUTH -> x + (y + 1) * xSize;
                    case WEST -> numHorzLines + x + y * (xSize + 1);
                };
            } else if (x == -1 && y < ySize && y >= 0) {
                if (direction == CardinalDirection.EAST) {
                    return numHorzLines + y * (xSize + 1);
                }
            } else if (x == xSize && y < ySize && y >= 0) {
                if (direction == CardinalDirection.WEST) {
                    return numHorzLines + x + y * (xSize + 1);
                }
            } else if (y == -1 && x < xSize && x >= 0) {
                if (direction == CardinalDirection.SOUTH) {
                    return x;
                }
            } else if (y == ySize && x < xSize && x >= 0) {
                if (direction == CardinalDirection.NORTH) {
                    return x + y * xSize;
                }
            }
            return -1;
        } else {
            return switch (direction) {
                case NORTH -> getIndex(true, new Coords(x, y - 1), CardinalDirection.WEST, dimention);
                case EAST -> getIndex(true, coords, CardinalDirection.NORTH, dimention);
                case SOUTH -> getIndex(true, coords, CardinalDirection.WEST, dimention);
                case WEST -> getIndex(true, new Coords(x - 1, y), CardinalDirection.NORTH, dimention);
            };
        }
    }
    public Changes setPoint(Line line, int x, int y, CardinalDirection direction) {
        return setPoint(line, x, y, direction, false);
    }
    private Changes set(Line line, int i, boolean override) {
        if (i < 0 || i > memory.length) {
            return null;
        }
        if (memory[i] != line && (memory[i] == Line.EMPTY || override)) {
            memory[i] = line;
//            System.out.println("changing line " + i + " to " + line);
            return new Changes(line, i);
        }
        return null;
    }
}
