package SolvingActions.ChangeCenteredActions.Items;

import Enums.DataType;
import Memory.Coords;
import Memory.Memory;

import java.util.ArrayList;

public abstract class Item {
    public final Coords coords;
    public final DataType dataType;
    protected Item(Coords coords, DataType dataType) {
        this.coords = coords;
        this.dataType = dataType;
    }
    public abstract boolean executeAll(Memory memory);
    public abstract boolean executeTargeted(Memory memory, Item item);
    public abstract ArrayList<Item> getNeighbors(Memory memory);
}
