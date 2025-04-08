package Util;

import Enums.CardinalDirection;
import Memory.Coords;
import Memory.Dimentions;

public class Indexes {
    public static int line(boolean square, Coords coords, CardinalDirection direction, Dimentions dimentions) {
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
                case NORTH -> line(true, coords.addDirection(CardinalDirection.NORTH), CardinalDirection.WEST, dimentions);
                case EAST -> line(true, coords, CardinalDirection.NORTH, dimentions);
                case SOUTH -> line(true, coords, CardinalDirection.WEST, dimentions);
                case WEST -> line(true, coords.addDirection(CardinalDirection.WEST), CardinalDirection.NORTH, dimentions);
            };
        }
    }
}
