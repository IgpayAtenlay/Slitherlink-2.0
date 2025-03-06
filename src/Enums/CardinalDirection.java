package Enums;

public enum CardinalDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    CardinalDirection() {
    }

    public CardinalDirection getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }
}
