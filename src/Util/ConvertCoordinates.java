package Util;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;

public class ConvertCoordinates {
    public static int squareToPointX(int x, DiagonalDirection direction) {
        if (direction == DiagonalDirection.NORTHEAST || direction == DiagonalDirection.SOUTHEAST) {
            return x + 1;
        } else {
            return x;
        }
    }
    public static int squareToPointY(int y, DiagonalDirection direction) {
        if (direction == DiagonalDirection.NORTHEAST || direction == DiagonalDirection.NORTHWEST) {
            return y;
        } else {
            return y + 1;
        }
    }
    public static int pointToSquareX(int x, DiagonalDirection direction) {
        if (direction == DiagonalDirection.NORTHEAST || direction == DiagonalDirection.SOUTHEAST) {
            return x;
        } else {
            return x + 1;
        }
    }
    public static int pointToSquareY(int y, DiagonalDirection direction) {
        if (direction == DiagonalDirection.NORTHEAST || direction == DiagonalDirection.NORTHWEST) {
            return y + 1;
        } else {
            return y;
        }
    }
    public static int[] addDirection(int x, int y, CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> new int[] {x, y - 1};
            case EAST -> new int[] {x + 1, y};
            case SOUTH -> new int[] {x, y + 1};
            case WEST -> new int[] {x - 1, y};
        };
    }
}
