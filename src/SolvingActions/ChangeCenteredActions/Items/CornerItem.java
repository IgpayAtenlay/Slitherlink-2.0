package SolvingActions.ChangeCenteredActions.Items;

import Enums.Number;
import Enums.*;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.CornerGeneration;

import java.util.ArrayList;

public class CornerItem extends Item {
    public final DiagonalDirection direction;
    public CornerItem(Coords coords, DiagonalDirection direction) {
        super(coords, DataType.CORNER);
        this.direction = direction;
    }

    @Override
    public boolean executeAll(Memory memory) {
        return CornerGeneration.addCorner(memory, coords, direction);
    }

    @Override
    public boolean executeTargeted(Memory memory, Item item) {
        Corner corner = Corner.ANY;
        switch (item.dataType) {
            case LINE -> {
                Line sourceLine = memory.getLine(true, item.coords, ((LineItem)item).direction);
                Line otherLine;
                if (this.coords.equals(item.coords)) {
                    otherLine = memory.getLine(true, this.coords, this.direction.getOtherCardinalDirection(((LineItem)item).direction));
                } else {
                    otherLine = memory.getLine(true, this.coords, this.direction.getOtherCardinalDirection(((LineItem)item).direction.getOpposite()));
                }
                corner = corner.combine(CornerGeneration.definition(sourceLine, otherLine));
            }
            case CORNER -> {
                boolean isAcross = this.direction.getOpposite() == ((CornerItem) item).direction;

                if (this.coords.equals(item.coords)) {
                    Number number = memory.getNumber(this.coords);
                    Corner across = memory.getCorner(true, this.coords, this.direction.getOpposite());
                    Corner adjacentOne = memory.getCorner(true, this.coords, this.direction.getClockwise());
                    Corner adjacentTwo = memory.getCorner(true, this.coords, this.direction.getCounterClockwise());
                    if (isAcross) {
                        corner = corner.combine(CornerGeneration.acrossSquare(number, across));
                    } else {
                        Highlight northHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.NORTH));
                        Highlight southHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.SOUTH));
                        Highlight eastHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.EAST));
                        Highlight westHighlight = memory.getHighlight(coords.addDirection(CardinalDirection.WEST));

                        corner = corner.combine(CornerGeneration.adjacentSquare(number, adjacentOne, adjacentTwo));
                        corner = corner.combine(CornerGeneration.onesCornersBetweenIdenticalHighlights(adjacentOne, adjacentTwo, northHighlight, southHighlight));
                        corner = corner.combine(CornerGeneration.onesCornersBetweenIdenticalHighlights(adjacentOne, adjacentTwo, eastHighlight, westHighlight));

                    }
                    corner = corner.combine(CornerGeneration.entireSquare(across, adjacentOne, adjacentTwo));
                } else {
                    Corner across = memory.getCorner(true, this.coords.addDirection(this.direction), this.direction.getOpposite());
                    Corner adjacentOne = memory.getCorner(true, this.coords.addDirection(this.direction.getCardinalDirections()[0]), this.direction.getClockwise());
                    Corner adjacentTwo = memory.getCorner(true, this.coords.addDirection(this.direction.getCardinalDirections()[1]), this.direction.getCounterClockwise());
                    if (isAcross) {
                        corner = corner.combine(CornerGeneration.acrossPoint(across));
                    } else {
                        corner = corner.combine(CornerGeneration.adjacentPoint(adjacentOne, adjacentTwo));
                    }
                    corner = corner.combine(CornerGeneration.entirePoint(across, adjacentOne, adjacentTwo));
                }
            }
            case HIGHLIGHT -> {
                Highlight sourceHighlight = memory.getHighlight(item.coords);
                Highlight oppositeHighlight = memory.getHighlight(this.coords.addDirection(this.coords.whichCardinalDirection(item.coords).getOpposite()));
                Number number = memory.getNumber(this.coords);
                Corner adjacentOne = memory.getCorner(true, this.coords, this.direction.getClockwise());
                Corner adjacentTwo = memory.getCorner(true, this.coords, this.direction.getCounterClockwise());

                corner = corner.combine(CornerGeneration.twoBetweenIdenticalHighlights(number, sourceHighlight, oppositeHighlight));
                corner = corner.combine(CornerGeneration.onesCornersBetweenIdenticalHighlights(adjacentOne, adjacentTwo, sourceHighlight, oppositeHighlight));
            }
        }

        if(corner != Corner.ANY) {
            memory.setCorner(true, corner, this.coords, this.direction, false);
            return true;
        } else {
            return false;
        }
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
