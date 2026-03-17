package Enums;

public enum DiagonalDirection {
    NORTHEAST,
    SOUTHEAST,
    SOUTHWEST,
    NORTHWEST;

    DiagonalDirection() {
    }

    public DiagonalDirection getOpposite() {
        return switch (this) {
            case NORTHEAST -> SOUTHWEST;
            case SOUTHEAST -> NORTHWEST;
            case SOUTHWEST -> NORTHEAST;
            case NORTHWEST -> SOUTHEAST;
        };
    }
    public DiagonalDirection getClockwise() {
        return switch (this) {
            case NORTHEAST -> SOUTHEAST;
            case SOUTHEAST -> SOUTHWEST;
            case SOUTHWEST -> NORTHWEST;
            case NORTHWEST -> NORTHEAST;
        };
    }
    public DiagonalDirection getCounterClockwise() {
        return switch (this) {
            case NORTHEAST -> NORTHWEST;
            case SOUTHEAST -> NORTHEAST;
            case SOUTHWEST -> SOUTHEAST;
            case NORTHWEST -> SOUTHWEST;
        };
    }

    public CardinalDirection[] getCardinalDirections() {
        return switch (this) {
            case NORTHEAST -> new CardinalDirection[] {CardinalDirection.NORTH, CardinalDirection.EAST};
            case SOUTHEAST -> new CardinalDirection[] {CardinalDirection.EAST, CardinalDirection.SOUTH};
            case SOUTHWEST -> new CardinalDirection[] {CardinalDirection.SOUTH, CardinalDirection.WEST};
            case NORTHWEST -> new CardinalDirection[] {CardinalDirection.WEST, CardinalDirection.NORTH};
        };
    }
    public CardinalDirection getOtherCardinalDirection(CardinalDirection direction)
        throws RuntimeException
    {
        if (getCardinalDirections()[0] == direction) {
            return getCardinalDirections()[1];
        } else if (getCardinalDirections()[1] == direction) {
            return getCardinalDirections()[0];
        }
        throw new RuntimeException("Cardinal direction not part of diagonal direction");
    }
}
