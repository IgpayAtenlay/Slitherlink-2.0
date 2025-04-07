package Memory;

import Enums.CardinalDirection;
import Enums.Line;

import java.util.Arrays;

public class LineMemory {
    private final Dimentions dimentions;
    private final Line[] memory;

    public LineMemory(Dimentions dimentions, Line[] memory) {
        this.dimentions = dimentions;
        this.memory = memory;
    }
    public LineMemory(Dimentions dimentions) {
        this(dimentions, new Line[dimentions.xSize * (dimentions.ySize + 1) + dimentions.ySize * (dimentions.xSize + 1)]);
        Arrays.fill(memory, Line.EMPTY);
    }
    public LineMemory copy() {
        return new LineMemory(dimentions.copy(), Arrays.copyOf(memory, memory.length));
    }

    private int numHorzLines() {
        return dimentions.xSize * (dimentions.ySize + 1);
    }

    public Line getSquare(int x, int y, CardinalDirection direction) {
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
            return switch (direction) {
                case NORTH -> memory[x + y * dimentions.xSize];
                case EAST -> memory[numHorzLines() + (x + 1) + y * (dimentions.xSize + 1)];
                case SOUTH -> memory[x + (y + 1) * dimentions.xSize];
                case WEST -> memory[numHorzLines() + x + y * (dimentions.xSize + 1)];
            };
        } else if (x == -1 && y < dimentions.ySize && y >= 0) {
            if (direction == CardinalDirection.EAST) {
                return memory[numHorzLines() + y * (dimentions.xSize + 1)];
            } else {
                return Line.X;
            }
        } else if (x < dimentions.xSize && x >= 0 && y == -1) {
            if (direction == CardinalDirection.SOUTH) {
                return memory[x];
            } else {
                return Line.X;
            }
        } else if (x == dimentions.xSize && y < dimentions.ySize && y >= 0) {
            if (direction == CardinalDirection.WEST) {
                return memory[numHorzLines() + x + y * (dimentions.xSize + 1)];
            } else {
                return Line.X;
            }
        } else if (x < dimentions.xSize && x >= 0 && y == dimentions.ySize) {
            if (direction == CardinalDirection.NORTH) {
                return memory[x + y * dimentions.xSize];
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
        return set(line, getIndex(true, new Coords(x, y), direction, new Dimentions(dimentions.xSize, dimentions.ySize)), override);
    }
    public Changes setSquare(Line line, int x, int y, CardinalDirection direction) {
        return setSquare(line, x, y, direction, false);
    }
    public Changes setPoint(Line line, int x, int y, CardinalDirection direction, boolean override) {
        return set(line, getIndex(false, new Coords(x, y), direction, new Dimentions(dimentions.xSize, dimentions.ySize)), override);
    }
    public static int getIndex(boolean square, Coords coords, CardinalDirection direction, Dimentions dimentions) {
        int x = coords.x;
        int y = coords.y;
        int numHorzLines = dimentions.xSize * (dimentions.ySize + 1);
        if (square) {
            if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
                return switch (direction) {
                    case NORTH -> x + y * dimentions.xSize;
                    case EAST -> numHorzLines + (x + 1) + y * (dimentions.xSize + 1);
                    case SOUTH -> x + (y + 1) * dimentions.xSize;
                    case WEST -> numHorzLines + x + y * (dimentions.xSize + 1);
                };
            } else if (x == -1 && y < dimentions.ySize && y >= 0) {
                if (direction == CardinalDirection.EAST) {
                    return numHorzLines + y * (dimentions.xSize + 1);
                }
            } else if (x == dimentions.xSize && y < dimentions.ySize && y >= 0) {
                if (direction == CardinalDirection.WEST) {
                    return numHorzLines + x + y * (dimentions.xSize + 1);
                }
            } else if (y == -1 && x < dimentions.xSize && x >= 0) {
                if (direction == CardinalDirection.SOUTH) {
                    return x;
                }
            } else if (y == dimentions.ySize && x < dimentions.xSize && x >= 0) {
                if (direction == CardinalDirection.NORTH) {
                    return x + y * dimentions.xSize;
                }
            }
            return -1;
        } else {
            return switch (direction) {
                case NORTH -> getIndex(true, new Coords(x, y - 1), CardinalDirection.WEST, dimentions);
                case EAST -> getIndex(true, coords, CardinalDirection.NORTH, dimentions);
                case SOUTH -> getIndex(true, coords, CardinalDirection.WEST, dimentions);
                case WEST -> getIndex(true, new Coords(x - 1, y), CardinalDirection.NORTH, dimentions);
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
