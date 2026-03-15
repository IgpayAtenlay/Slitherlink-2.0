package SolvingActions.ChangeCenteredActions.Items;

import Memory.Coords;

public abstract class Item {
    public final Coords coords;
    protected Item(Coords coords) {
        this.coords = coords;
    }
}
