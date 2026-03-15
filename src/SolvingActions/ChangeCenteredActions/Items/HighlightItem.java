package SolvingActions.ChangeCenteredActions.Items;

import Enums.*;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.HighlightGeneration;

import java.util.ArrayList;

public class HighlightItem extends Item {
    public HighlightItem(Coords coords) {
        super(coords, DataType.HIGHLIGHT);
    }

    @Override
    public boolean executeAll(Memory memory) {
        return HighlightGeneration.addHighlight(memory, coords);
    }

    @Override
    public boolean executeTargeted(Memory memory, Item item) {
        return false;
    }

    @Override
    public ArrayList<Item> getNeighbors(Memory memory) {
        ArrayList<Item> result = new ArrayList<>();

//        lines
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, this.coords, direction) == Line.EMPTY) {
                result.add(new LineItem(this.coords, direction));
            }
        }

//        highlights
        for (CardinalDirection direction : CardinalDirection.values()) {
            Coords coords = this.coords.addDirection(direction);
            if (memory.getHighlight(coords) == Highlight.EMPTY) {
                result.add(new HighlightItem(coords));
            }
        }
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Coords coords = this.coords.addDirection(direction);
            if (memory.getHighlight(coords) == Highlight.EMPTY) {
                result.add(new HighlightItem(coords));
            }
        }

//        corners
        for (CardinalDirection cardinalDirection : CardinalDirection.values()) {
            Coords coords = this.coords.addDirection(cardinalDirection);
            for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                if (!memory.getCorner(true, coords, diagonalDirection).oneOption()) {
                    result.add(new CornerItem(coords, diagonalDirection));
                }
            }
        }

        return result;
    }
}
