package SolvingActions.ChangeCenteredActions;

import Enums.*;
import ErrorChecking.Errors;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.CornerItem;
import SolvingActions.ChangeCenteredActions.Items.HighlightItem;
import SolvingActions.ChangeCenteredActions.Items.Item;
import SolvingActions.ChangeCenteredActions.Items.LineItem;

import java.util.ArrayDeque;

public class ForwardChecking {
    public static void runForwardChecking(Memory memory) {
        int changes;
        int index = 0;
        int numLoops = 0;
        do {
            Control2.autoSolve(memory);
            changes = memory.getNumChanges();
            index = chooseItem(memory, index);
        } while (changes != memory.getNumChanges());
    }
    public static int chooseItem(Memory memory, int startingIndex) {
        int currentIndex = startingIndex;
        boolean success = false;
        do {
            int functionalIndex = currentIndex;
            if (functionalIndex < memory.getDimentions().allSquareCoords().size()) {
                Coords coords = memory.getDimentions().allSquareCoords().get(functionalIndex);
                success = chooseValue(new HighlightItem(coords), memory);
            } else {
//                functionalIndex -= memory.getDimentions().allSquareCoords().size() * 4;
//                if (functionalIndex<memory.getDimentions().allHorizontalLineCoords().size()) {
//                    Coords coords = memory.getDimentions().allHorizontalLineCoords().get(functionalIndex);
//                    success = chooseValue(new LineItem(coords, CardinalDirection.NORTH), memory);
//                } else {
//                    functionalIndex -= memory.getDimentions().allHorizontalLineCoords().size();
//                    if (functionalIndex < memory.getDimentions().allVerticalLineCoords().size()) {
//                        Coords coords = memory.getDimentions().allVerticalLineCoords().get(functionalIndex);
//                        success = chooseValue(new LineItem(coords, CardinalDirection.WEST), memory);
//                    } else {
//                        functionalIndex -= memory.getDimentions().allSquareCoords().size();
//                        if (functionalIndex < memory.getDimentions().allSquareCoords().size() * 4) {
//                            Coords coords = memory.getDimentions().allSquareCoords().get(functionalIndex / 4);
//                            DiagonalDirection direction = DiagonalDirection.values()[functionalIndex % 4];
//                            success = chooseValue(new CornerItem(coords, direction), memory);
//                        } else {
                            currentIndex = -1;
//                        }
//                    }
//                }
            }
            currentIndex++;
        } while (!success && currentIndex != startingIndex);
        return currentIndex;
    }
    public static boolean chooseValue(Item item, Memory memory) {
        Memory testMemory;
        boolean errorFound = false;
        switch (item.dataType) {
            case LINE -> {
                testMemory = memory.copy();
                testMemory.setLine(true, Line.LINE, item.coords, ((LineItem)item).direction, true);
                errorFound = testItem(item, testMemory);
                if (errorFound) {
                    memory.setLine(true, Line.X, item.coords, ((LineItem) item).direction, true);
                } else {
                    testMemory = memory.copy();
                    testMemory.setLine(true, Line.X, item.coords, ((LineItem)item).direction, true);
                    errorFound = testItem(item, testMemory);
                    if (errorFound) {
                        memory.setLine(true, Line.LINE, item.coords, ((LineItem) item).direction, true);
                    }
                }
            }
            case HIGHLIGHT -> {
                testMemory = memory.copy();
                testMemory.setHighlight(Highlight.INSIDE, item.coords, true);
                errorFound = testItem(item, testMemory);
                if (errorFound) {
                    memory.setHighlight(Highlight.OUTSIDE, item.coords, true);
                } else {
                    testMemory = memory.copy();
                    testMemory.setHighlight(Highlight.OUTSIDE, item.coords, true);
                    errorFound = testItem(item, testMemory);
                    if (errorFound) {
                        memory.setHighlight(Highlight.INSIDE, item.coords, true);
                    }
                }
            }
            case CORNER -> {
                Corner startingCorner = memory.getCorner(true, item.coords, ((CornerItem)item).direction);
                if (startingCorner.zero) {
                    testMemory = memory.copy();
                    testMemory.setCorner(true, Corner.ZERO, item.coords, ((CornerItem)item).direction, true);
                    errorFound = testItem(item, testMemory);
                    if (errorFound) {
                        memory.setCorner(true, startingCorner.combine(Corner.NOT_ZERO), item.coords, ((CornerItem)item).direction, true);
                    }
                }
                if (startingCorner.one && !errorFound) {
                    testMemory = memory.copy();
                    testMemory.setCorner(true, Corner.ONE, item.coords, ((CornerItem)item).direction, true);
                    errorFound = testItem(item, testMemory);
                    if (errorFound) {
                        memory.setCorner(true, startingCorner.combine(Corner.NOT_ONE), item.coords, ((CornerItem)item).direction, true);
                    }
                }
                if (startingCorner.two && !errorFound) {
                    testMemory = memory.copy();
                    testMemory.setCorner(true, Corner.TWO, item.coords, ((CornerItem)item).direction, true);
                    errorFound = testItem(item, testMemory);
                    if (errorFound) {
                        memory.setCorner(true, startingCorner.combine(Corner.NOT_TWO), item.coords, ((CornerItem)item).direction, true);
                    }
                }
            }
        }
        return errorFound;
    }
    public static boolean testItem(Item item, Memory memory) {
        ArrayDeque<Item> targets = new ArrayDeque<>();
        targets.add(item);
        while(targets.size() > 0) {
            try {
                ConstraintPropagation.iterateTarget(memory, targets);
            } catch (Exception e) {
                return true;
            }
        }
        return Errors.hasErrors(memory);
    }
}
