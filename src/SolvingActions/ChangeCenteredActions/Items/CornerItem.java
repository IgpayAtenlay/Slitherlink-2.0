package SolvingActions.ChangeCenteredActions.Items;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Highlight;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.CornerGeneration;

import java.util.ArrayList;

public class CornerItem extends Item {
    public final DiagonalDirection direction;
    public CornerItem(Coords coords, DiagonalDirection direction) {
        super(coords);
        this.direction = direction;
    }

    @Override
    public boolean add(Memory memory) {
        return CornerGeneration.addCorner(memory, coords, direction);
    }

    @Override
    public ArrayList<Item> getNeighbors(Memory memory) {
        ArrayList<Item> result = new ArrayList<>();

//        lines
        for (CardinalDirection direction : this.direction.getCardinalDirections()) {
            if (memory.getLine(true, this.coords, direction) == Line.EMPTY) {
                result.add(new LineItem(this.coords, direction));
            }
        }

//        highlights
        for (CardinalDirection direction : this.direction.getCardinalDirections()) {
            Coords coords =  this.coords.addDirection(direction);
            if (memory.getHighlight(coords) == Highlight.EMPTY) {
                result.add(new HighlightItem(coords));
            }
        }

//        corners
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (direction != this.direction) {
                if (!memory.getCorner(true, this.coords, direction).oneOption()) {
                    result.add(new CornerItem(this.coords, direction));
                }
            }
        }
        Coords coords = this.coords.addDirection(this.direction);
        DiagonalDirection direction = this.direction.getOpposite();
        if (!memory.getCorner(true, coords, direction).oneOption()) {
            result.add(new CornerItem(coords, direction));
        }
        coords = this.coords.addDirection(this.direction.getCardinalDirections()[0]);
        direction = this.direction.getClockwise();
        if (!memory.getCorner(true, coords, direction).oneOption()) {
            result.add(new CornerItem(coords, direction));
        }
        coords = this.coords.addDirection(this.direction.getCardinalDirections()[1]);
        direction = this.direction.getCounterClockwise();
        if (!memory.getCorner(true, coords, direction).oneOption()) {
            result.add(new CornerItem(coords, direction));
        }

        return result;
    }
}
