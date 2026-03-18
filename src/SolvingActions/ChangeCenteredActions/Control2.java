package SolvingActions.ChangeCenteredActions;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Memory.Coords;
import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.CornerItem;
import SolvingActions.ChangeCenteredActions.Items.HighlightItem;
import SolvingActions.ChangeCenteredActions.Items.Item;
import SolvingActions.ChangeCenteredActions.Items.LineItem;

import java.util.ArrayDeque;
import java.util.Queue;

public class Control2 {
    public static void autoSolve(Memory memory) {
        int changes;
        int index = 0;
        int numLoops = 0;
        do {
            changes = memory.getNumChanges();
            ArrayDeque<Item> targets = new ArrayDeque<>();
            index = queueNextItem(memory, index, targets);
            while(targets.size() > 0) {
                ConstraintPropagation.iterateTarget(memory, targets);
            }
            numLoops++;
        } while (changes != memory.getNumChanges());
    }
    public static int queueNextItem(Memory memory, int startingIndex, Queue<Item> targets) {
        int currentIndex = startingIndex;
        boolean success = false;
        do {
            int functionalIndex = currentIndex;
            if (functionalIndex < memory.getDimentions().allSquareCoords().size()) {
                Coords coords = memory.getDimentions().allSquareCoords().get(functionalIndex);
                success = queueItem(new HighlightItem(coords), memory, targets);
            } else {
                functionalIndex -= memory.getDimentions().allSquareCoords().size();
                if (functionalIndex < memory.getDimentions().allSquareCoords().size() * 4) {
                    Coords coords = memory.getDimentions().allSquareCoords().get(functionalIndex / 4);
                    DiagonalDirection direction = DiagonalDirection.values()[functionalIndex % 4];
                    success = queueItem(new CornerItem(coords, direction), memory, targets);
                } else {
                    functionalIndex -= memory.getDimentions().allSquareCoords().size() * 4;
                    if (functionalIndex<memory.getDimentions().allHorizontalLineCoords().size()) {
                        Coords coords = memory.getDimentions().allHorizontalLineCoords().get(functionalIndex);
                        success = queueItem(new LineItem(coords, CardinalDirection.NORTH), memory, targets);
                    } else {
                        functionalIndex -= memory.getDimentions().allHorizontalLineCoords().size();
                        if (functionalIndex < memory.getDimentions().allVerticalLineCoords().size()) {
                            Coords coords = memory.getDimentions().allVerticalLineCoords().get(functionalIndex);
                            success = queueItem(new LineItem(coords, CardinalDirection.WEST), memory, targets);
                        } else {
                            currentIndex = -1;
                        }
                    }
                }
            }
            currentIndex++;
        } while (!success && currentIndex != startingIndex);
        return currentIndex;
    }
    public static boolean queueItem(Item item, Memory memory, Queue<Item> targets) {
        boolean changeMade = item.executeAll(memory);
        if (!changeMade) return false;
        targets.add(item);
        return ConstraintPropagation.iterateTarget(memory, targets);
    }
}
