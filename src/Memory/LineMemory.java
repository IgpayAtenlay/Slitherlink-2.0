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

    public Line getSquare(Coords coords, CardinalDirection direction) {
        int index = getIndex(true, coords, direction, dimentions);
        if (index < 0 || index >= memory.length) {
            return Line.X;
        } else {
            return memory[index];
        }
    }
    public Line getPoint(Coords coords, CardinalDirection direction) {
        int index = getIndex(false, coords, direction, dimentions);
        if (index < 0 || index >= memory.length) {
            return Line.X;
        } else {
            return memory[index];
        }
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
    public Changes setSquare(Line line, Coords coords, CardinalDirection direction, boolean override) {
        return set(line, getIndex(true, coords, direction, new Dimentions(dimentions.xSize, dimentions.ySize)), override);
    }
    public Changes setSquare(Line line, Coords coords, CardinalDirection direction) {
        return setSquare(line, coords, direction, false);
    }
    public Changes setPoint(Line line, Coords coords, CardinalDirection direction, boolean override) {
        return set(line, getIndex(false, coords, direction, new Dimentions(dimentions.xSize, dimentions.ySize)), override);
    }
    public Changes setPoint(Line line, Coords coords, CardinalDirection direction) {
        return setPoint(line, coords, direction, false);
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
                case NORTH -> getIndex(true, coords.addDirection(CardinalDirection.NORTH), CardinalDirection.WEST, dimentions);
                case EAST -> getIndex(true, coords, CardinalDirection.NORTH, dimentions);
                case SOUTH -> getIndex(true, coords, CardinalDirection.WEST, dimentions);
                case WEST -> getIndex(true, coords.addDirection(CardinalDirection.WEST), CardinalDirection.NORTH, dimentions);
            };
        }
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
