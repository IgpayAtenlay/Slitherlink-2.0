package SolvingActions.ChangeCenteredActions.Items;

import Enums.DiagonalDirection;
import Memory.Coords;

public class CornerItem extends Item {
    public final DiagonalDirection direction;
    public CornerItem(Coords coords, DiagonalDirection direction) {
        super(coords);
        this.direction = direction;
    }
}
