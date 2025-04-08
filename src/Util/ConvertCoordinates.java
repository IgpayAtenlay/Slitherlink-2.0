package Util;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Memory.Coords;

public class ConvertCoordinates {
    public static Coords squareToPoint(Coords coords, DiagonalDirection direction) {
        int x = switch (direction) {
            case NORTHEAST, SOUTHEAST -> coords.x + 1;
            case NORTHWEST, SOUTHWEST -> coords.x;
        };
        int y = switch (direction) {
            case NORTHEAST, NORTHWEST -> coords.y;
            case SOUTHEAST, SOUTHWEST -> coords.y + 1;
        };
        return new Coords(x, y);
    }
    public static int squareToPointX(int x, DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST, SOUTHEAST -> x + 1;
            case NORTHWEST, SOUTHWEST -> x;
        };
    }
    public static int squareToPointY(int y, DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST, NORTHWEST -> y;
            case SOUTHEAST, SOUTHWEST -> y + 1;
        };
    }
    public static Coords pointToSquare(Coords coords, DiagonalDirection direction) {
        int x = switch (direction) {
            case NORTHEAST, SOUTHEAST -> coords.x;
            case SOUTHWEST, NORTHWEST -> coords.x + 1;
        };
        int y = switch (direction) {
            case NORTHEAST, NORTHWEST -> coords.y + 1;
            case SOUTHEAST, SOUTHWEST -> coords.y;
        };

        return new Coords(x, y);
    }
    public static int pointToSquareX(int x, DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST, SOUTHEAST -> x;
            case SOUTHWEST, NORTHWEST -> x + 1;
        };
    }
    public static int pointToSquareY(int y, DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST, NORTHWEST -> y + 1;
            case SOUTHEAST, SOUTHWEST -> y;
        };
    }
    public static Coords addDirection(Coords coords, CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> new Coords(coords.x, coords.y - 1);
            case EAST -> new Coords(coords.x + 1, coords.y);
            case SOUTH -> new Coords(coords.x, coords.y + 1);
            case WEST -> new Coords(coords.x - 1, coords.y);
        };
    }
}
