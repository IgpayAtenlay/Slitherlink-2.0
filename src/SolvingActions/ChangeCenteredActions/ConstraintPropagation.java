package SolvingActions.ChangeCenteredActions;

import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.Item;

import java.util.ArrayDeque;
import java.util.Queue;

public class ConstraintPropagation {
    public static void constraintPropagation(Item origionalItem, Memory memory) {
        Queue<Item> targets = new ArrayDeque<>();
        targets.add(origionalItem);
        while (targets.size() > 0) {
            Item previousItem = targets.remove();
            for (Item currentItem : previousItem.getNeighbors(memory)) {
//                change to only target relevent rules
                boolean changeMade = currentItem.add(memory);
                if (changeMade) targets.add(currentItem);
            }
        }
    }
}
