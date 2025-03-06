package Util;

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
}
