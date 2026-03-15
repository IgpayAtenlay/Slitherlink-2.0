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
    public CardinalDirection getClockwise() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }
    public CardinalDirection getCounterClockwise() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public DiagonalDirection[] getDiagonalDirections() {
        return switch (this) {
            case NORTH -> new DiagonalDirection[] {DiagonalDirection.NORTHWEST, DiagonalDirection.NORTHEAST};
            case EAST -> new DiagonalDirection[] {DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST};
            case SOUTH -> new DiagonalDirection[] {DiagonalDirection.SOUTHEAST, DiagonalDirection.SOUTHWEST};
            case WEST -> new DiagonalDirection[] {DiagonalDirection.SOUTHWEST, DiagonalDirection.NORTHWEST};
        };
    }
    public DiagonalDirection getDiagonalDirection(CardinalDirection directionTwo)
        throws RuntimeException
    {
        return switch (this) {
            case NORTH -> switch (directionTwo) {
                case EAST -> DiagonalDirection.NORTHEAST;
                case WEST -> DiagonalDirection.NORTHWEST;
                default -> throw new RuntimeException("Bad directions");
            };
            case EAST -> switch (directionTwo) {
                case NORTH -> DiagonalDirection.NORTHEAST;
                case SOUTH -> DiagonalDirection.SOUTHEAST;
                default -> throw new RuntimeException("Bad directions");
            };
            case SOUTH -> switch (directionTwo) {
                case EAST -> DiagonalDirection.SOUTHEAST;
                case WEST -> DiagonalDirection.SOUTHWEST;
                default -> throw new RuntimeException("Bad directions");
            };
            case WEST -> switch (directionTwo) {
                case NORTH -> DiagonalDirection.NORTHWEST;
                case SOUTH -> DiagonalDirection.SOUTHWEST;
                default -> throw new RuntimeException("Bad directions");
            };
        };
    }
}
