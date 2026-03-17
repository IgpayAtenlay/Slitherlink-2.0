package SolvingActions.ChangeCenteredActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.CornerItem;
import SolvingActions.ChangeCenteredActions.Items.HighlightItem;
import SolvingActions.ChangeCenteredActions.Items.Item;
import SolvingActions.ChangeCenteredActions.Items.LineItem;

public class Control2 {
    static int currentIndex = 0;
    static boolean hitZero = false;
    public static void autoSolve(Memory memory) {
        int changes;
        int numLoops = 0;
        do {
            System.out.println("new loop");
            changes = memory.getNumChanges();
            iterateAllItems(memory);
            numLoops++;
            System.out.println(numLoops);
        } while (changes != memory.getNumChanges());
    }
    public static void iterateAllItems(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
            changeItem(new HighlightItem(coords), memory);
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                changeItem(new CornerItem(coords, direction), memory);
                return;
            }
        }
        for (Coords coords : memory.getDimentions().allHorizontalLineCoords()) {
            changeItem(new LineItem(coords, CardinalDirection.NORTH), memory);
        }
        for (Coords coords : memory.getDimentions().allVerticalLineCoords()) {
            changeItem(new LineItem(coords, CardinalDirection.WEST), memory);
        }
    }
    public static void iterateNextItem(Memory memory) {
        int index = currentIndex;
        boolean success = false;
        while (!success) {
            if (currentIndex == 0) {
                if (hitZero) {
                    hitZero = false;
                    return;
                } else {
                    hitZero = true;
                }
            }
            if (index < memory.getDimentions().allSquareCoords().size()) {
                Coords coords = memory.getDimentions().allSquareCoords().get(index);
                success = changeItem(new HighlightItem(coords), memory);
            } else {
                index -= memory.getDimentions().allSquareCoords().size();
                if (index < memory.getDimentions().allSquareCoords().size() * 4) {
                    Coords coords = memory.getDimentions().allSquareCoords().get(index / 4);
                    DiagonalDirection direction = DiagonalDirection.values()[index % 4];
                    success = changeItem(new CornerItem(coords, direction), memory);
                } else {
                    index -= memory.getDimentions().allSquareCoords().size() * 4;
                    if (index<memory.getDimentions().allHorizontalLineCoords().size()) {
                        Coords coords = memory.getDimentions().allHorizontalLineCoords().get(index);
                        success = changeItem(new LineItem(coords, CardinalDirection.NORTH), memory);
                    } else {
                        index -= memory.getDimentions().allHorizontalLineCoords().size();
                        if (index < memory.getDimentions().allVerticalLineCoords().size()) {
                            Coords coords = memory.getDimentions().allVerticalLineCoords().get(index);
                            success = changeItem(new LineItem(coords, CardinalDirection.WEST), memory);
                        } else {
                            currentIndex = -1;
                        }
                    }
                }
            }
            currentIndex++;
            index = currentIndex;
        }
    }
    public static boolean changeItem(Item item, Memory memory) {
        boolean changeMade = item.executeAll(memory);
        if (!changeMade) return false;
        System.out.println("change???");
        ConstraintPropagation.constraintPropagation(item, memory);
        return true;
    }
}
