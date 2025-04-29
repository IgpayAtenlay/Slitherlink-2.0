package Memory;

import Enums.CardinalDirection;

public class DirectionCoords {
    public final Coords coords;
    public final CardinalDirection direction;
    public DirectionCoords(Coords coords, CardinalDirection cardinalDirection) {
        this.coords = coords;
        this.direction = cardinalDirection;
    }
}
