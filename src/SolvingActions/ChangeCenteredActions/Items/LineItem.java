package SolvingActions.ChangeCenteredActions.Items;

import Enums.*;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.LineGeneration;

import java.util.ArrayList;

public class LineItem extends Item {
    public final CardinalDirection direction;
    public LineItem(Coords coords, CardinalDirection direction) {
        super(coords, DataType.LINE);
        this.direction = direction;
    }
    @Override
    public boolean executeAll(Memory memory) {
        return LineGeneration.addLine(memory, coords, direction);
    }
    @Override
    public boolean executeTargeted(Memory memory, Item item) {
        Line line = Line.EMPTY;
        switch (item.dataType) {
            case LINE -> {
                Corner corner;
                Line otherLine = memory.getLine(true, item.coords, ((LineItem)item).direction);
                if (this.coords.equals(item.coords)) {
                    corner = memory.getCorner(true, this.coords, this.direction.getDiagonalDirection(((LineItem)item).direction));
                } else {
                    corner = memory.getCorner(true, item.coords, this.direction.getOpposite().getDiagonalDirection(((LineItem)item).direction));
                }
                line = line.combine(LineGeneration.cornerDefinition(corner, otherLine));
            }
            case CORNER -> {
                Corner corner = memory.getCorner(true, item.coords, ((CornerItem)item).direction);
                Line otherLine;
                if (this.coords.equals(item.coords)) {
                    otherLine = memory.getLine(true, this.coords, ((CornerItem)item).direction.getCardinalDirection(this.direction));
                } else {
                    otherLine = memory.getLine(true, item.coords, ((CornerItem)item).direction.getCardinalDirection(this.direction.getOpposite()));
                }
                line = line.combine(LineGeneration.cornerDefinition(corner, otherLine));
            }
            case HIGHLIGHT -> {
                Highlight highlightOne = memory.getHighlight(item.coords);
                Highlight highlightTwo;
                if (this.coords.equals(item.coords)) {
                    highlightTwo = memory.getHighlight(this.coords.addDirection(this.direction));
                } else {
                    highlightTwo = memory.getHighlight(this.coords);
                }
                line = line.combine(LineGeneration.highlightDefinition(highlightOne, highlightTwo));
            }
        }

        if(line != Line.EMPTY) {
            memory.setLine(true, line, coords, direction, false);
            return true;
        } else {
            return false;
        }
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
