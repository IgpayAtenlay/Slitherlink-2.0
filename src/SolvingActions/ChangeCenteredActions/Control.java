package SolvingActions.ChangeCenteredActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.CornerItem;
import SolvingActions.ChangeCenteredActions.Items.HighlightItem;
import SolvingActions.ChangeCenteredActions.Items.Item;
import SolvingActions.ChangeCenteredActions.Items.LineItem;

public class Control {
    public static void autoSolve(Memory memory) {
        int changes;
        do {
            changes = memory.getNumChanges();
            iterateAllItems(memory);
        } while (changes != memory.getNumChanges());
    }
    public static void iterateAllItems(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
            changeItem(new HighlightItem(coords), memory);
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                changeItem(new CornerItem(coords, direction), memory);
            }
        }
        for (Coords coords : memory.getDimentions().allHorizontalLineCoords()) {
            changeItem(new LineItem(coords, CardinalDirection.NORTH), memory);
        }
        for (Coords coords : memory.getDimentions().allVerticalLineCoords()) {
            changeItem(new LineItem(coords, CardinalDirection.NORTH), memory);
        }
    }
    public static void changeItem(Item item, Memory memory) {
        boolean changeMade = item.add(memory);
        if (!changeMade) return;
        ConstraintPropagation.constraintPropagation(item, memory);
    }
}
