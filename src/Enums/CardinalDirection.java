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
    public DiagonalDirection[] getDiagonalDirections() {
        return switch (this) {
            case NORTH -> new DiagonalDirection[] {DiagonalDirection.NORTHEAST, DiagonalDirection.NORTHWEST};
            case EAST -> new DiagonalDirection[] {DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST};
            case SOUTH -> new DiagonalDirection[] {DiagonalDirection.SOUTHEAST, DiagonalDirection.SOUTHWEST};
            case WEST -> new DiagonalDirection[] {DiagonalDirection.SOUTHWEST, DiagonalDirection.NORTHWEST};
        };
    }
}
