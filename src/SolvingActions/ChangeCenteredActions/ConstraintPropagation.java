package SolvingActions.ChangeCenteredActions;

import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.Item;

import java.util.Queue;

public class ConstraintPropagation {
    public static boolean iterateTarget(Memory memory, Queue<Item> targets) {
        if (targets.size() == 0) return false;
        Item previousItem = targets.remove();
        boolean atLeastOneChange = false;
        for (Item currentItem : previousItem.getNeighbors(memory)) {
            boolean changeMade = currentItem.executeTargeted(memory, previousItem);
            if (changeMade) {
                atLeastOneChange = true;
                targets.add(currentItem);
            };
        }
        return atLeastOneChange;
    }
}
