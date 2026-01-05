package Memory;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;

import java.awt.*;

public class Coords {
    public final int x;
    public final int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coords(Point point) {
        this.x = point.x;
        this.y = point.y;
    }
    public Coords copy() {
        return new Coords(x, y);
    }

    public Coords squareToPoint(DiagonalDirection direction) {
        int x = switch (direction) {
            case NORTHEAST, SOUTHEAST -> this.x + 1;
            case NORTHWEST, SOUTHWEST -> this.x;
        };
        int y = switch (direction) {
            case NORTHEAST, NORTHWEST -> this.y;
            case SOUTHEAST, SOUTHWEST -> this.y + 1;
        };
        return new Coords(x, y);
    }
    public Coords pointToSquare(DiagonalDirection direction) {
        int x = switch (direction) {
            case NORTHEAST, SOUTHEAST -> this.x;
            case SOUTHWEST, NORTHWEST -> this.x + 1;
        };
        int y = switch (direction) {
            case NORTHEAST, NORTHWEST -> this.y + 1;
            case SOUTHEAST, SOUTHWEST -> this.y;
        };

        return new Coords(x, y);
    }
    public Coords addDirection(CardinalDirection direction) {
        return switch (direction) {
            case NORTH -> new Coords(this.x, this.y - 1);
            case EAST -> new Coords(this.x + 1, this.y);
            case SOUTH -> new Coords(this.x, this.y + 1);
            case WEST -> new Coords(this.x - 1, this.y);
        };
    }
    public Coords addDirection(DiagonalDirection direction) {
        return switch (direction) {
            case NORTHEAST -> new Coords(this.x + 1, this.y - 1);
            case SOUTHEAST -> new Coords(this.x + 1, this.y + 1);
            case SOUTHWEST -> new Coords(this.x - 1, this.y + 1);
            case NORTHWEST -> new Coords(this.x - 1, this.y - 1);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coords coord = (Coords) o;
        return x == coord.x && y == coord.y;
    }
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
