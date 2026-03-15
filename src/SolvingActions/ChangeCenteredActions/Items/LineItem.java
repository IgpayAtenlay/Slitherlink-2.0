package SolvingActions.ChangeCenteredActions.Items;

import Enums.CardinalDirection;
import Memory.Coords;

public class LineItem extends Item {
    public final CardinalDirection direction;
    public LineItem(Coords coords, CardinalDirection direction) {
        super(coords);
        this.direction = direction;
    }
}
