package SolvingActions.ChangeCenteredActions.Items;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Highlight;
import Enums.Line;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.LineGeneration;

import java.util.ArrayList;

public class LineItem extends Item {
    public final CardinalDirection direction;
    public LineItem(Coords coords, CardinalDirection direction) {
        super(coords);
        this.direction = direction;
    }
    @Override
    public boolean add(Memory memory) {
        return LineGeneration.addLine(memory, coords, direction);
    }
    @Override
    public ArrayList<Item> getNeighbors(Memory memory) {
        ArrayList<Item> result = new ArrayList<>();

//        lines
        for (Coords coords : new Coords[] {this.coords, this.coords.addDirection(this.direction)}) {
            for (CardinalDirection direction : new CardinalDirection[] {this.direction.getClockwise(), this.direction.getCounterClockwise()}) {
                if (memory.getLine(true, coords, direction) == Line.EMPTY) {
                    result.add(new LineItem(coords, direction));
                }
            }
        }

//        highlights
        for (Coords coords : new Coords[] {this.coords, this.coords.addDirection(this.direction)}) {
            if (memory.getHighlight(coords) == Highlight.EMPTY) {
                result.add(new HighlightItem(coords));
            }
        }

//        corners
        Coords coords = this.coords;
        for (DiagonalDirection direction : this.direction.getDiagonalDirections()) {
            if (!memory.getCorner(true, coords, direction).oneOption()) {
                result.add(new CornerItem(coords, direction));
            }
        }
        coords = this.coords.addDirection(this.direction);
        for (DiagonalDirection direction : this.direction.getOpposite().getDiagonalDirections()) {
            if (!memory.getCorner(true, coords, direction).oneOption()) {
                result.add(new CornerItem(coords, direction));
            }
        }

        return result;
    }
}
