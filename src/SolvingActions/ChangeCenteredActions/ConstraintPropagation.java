package SolvingActions.ChangeCenteredActions;

import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.Item;

import java.util.ArrayDeque;
import java.util.Queue;

public class ConstraintPropagation {
    static Queue<Item> targets = new ArrayDeque<>();
    public static void constraintPropagation(Item origionalItem, Memory memory) {
//        Queue<Item> targets = new ArrayDeque<>();
        targets.add(origionalItem);
//        while (targets.size() > 0) {
//            iterateTarget(memory);
//        }
    }
    public static boolean iterateTarget(Memory memory) {
        if (targets.size() == 0) return false;
        Item previousItem = targets.remove();
        System.out.println(previousItem);
        boolean atLeastOneChange = false;
        for (Item currentItem : previousItem.getNeighbors(memory)) {
            boolean changeMade = currentItem.executeTargeted(memory, previousItem);
            if (changeMade) {
                atLeastOneChange = true;
                targets.add(currentItem);
                System.out.println("change made: " + currentItem);
            };
        }
        return atLeastOneChange;
    }
}
