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
        Highlight highlight = Highlight.EMPTY;
        switch (item.dataType) {
            case LINE -> {
                Line adjacentLine = memory.getLine(true, item.coords, ((LineItem)item).direction);
                Highlight adjacentHighlight;
                if (this.coords == item.coords) {
                    adjacentHighlight = memory.getHighlight(this.coords.addDirection(((LineItem)item).direction));
                } else {
                    adjacentHighlight = memory.getHighlight(this.coords.addDirection(((LineItem)item).direction.getOpposite()));
                }
                highlight = highlight.combine(HighlightGeneration.definition(adjacentLine, adjacentHighlight));
            }
            case CORNER -> {
                CardinalDirection cornerCoordDirection = this.coords.whichCardinalDirection(item.coords);
                CardinalDirection cornerOtherDirection = ((CornerItem)item).direction.getOtherCardinalDirection(cornerCoordDirection.getOpposite());
                Corner sourceCorner = memory.getCorner(true, item.coords, ((CornerItem)item).direction);
                Corner otherCorner = memory.getCorner(true, this.coords.addDirection(cornerOtherDirection), ((CornerItem)item).direction.getOpposite());
                Highlight diagonalHighlight = memory.getHighlight(this.coords.addDirection(cornerCoordDirection).addDirection(cornerOtherDirection));

                highlight = highlight.combine(HighlightGeneration.corner(diagonalHighlight, sourceCorner, otherCorner));
            }
            case HIGHLIGHT -> {
                boolean isAdjacent = this.coords.isAdjacent(item.coords);
                Highlight sourceHighlight = memory.getHighlight(item.coords);

                if (isAdjacent) {
                    CardinalDirection highlightDirection = this.coords.whichCardinalDirection(item.coords);
                    Line adjacentLine = memory.getLine(true, this.coords, highlightDirection);
                    highlight = highlight.combine(HighlightGeneration.definition(adjacentLine, sourceHighlight));
                } else {
                    DiagonalDirection highlightDirection = this.coords.whichDiagonalDirection(item.coords);
                    Corner outsideCornerOne = memory.getCorner(true, this.coords.addDirection(highlightDirection.getCardinalDirections()[0]), highlightDirection.getClockwise());
                    Corner outsideCornerTwo = memory.getCorner(true, this.coords.addDirection(highlightDirection.getCardinalDirections()[1]), highlightDirection.getCounterClockwise());
                    highlight = highlight.combine(HighlightGeneration.corner(sourceHighlight, outsideCornerOne, outsideCornerTwo));
                }
            }
        }

        if(highlight != Highlight.EMPTY) {
            memory.setHighlight(highlight, coords, false);
            return true;
        } else {
            return false;
        }
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
