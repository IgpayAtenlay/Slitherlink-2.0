package SolvingActions.ChangeCenteredActions.Items;

import Memory.Coords;
import Memory.Memory;

import java.util.ArrayList;

public abstract class Item {
    public final Coords coords;
    protected Item(Coords coords) {
        this.coords = coords;
    }
    public abstract boolean add(Memory memory);
    public abstract ArrayList<Item> getNeighbors(Memory memory);
}
