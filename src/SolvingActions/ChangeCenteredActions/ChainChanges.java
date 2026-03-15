package SolvingActions.ChangeCenteredActions;

import Memory.Memory;
import SolvingActions.ChangeCenteredActions.Items.Item;

import java.util.ArrayDeque;
import java.util.Queue;

public class ChainChanges {
    public static void chainChanges(Item item, Memory memory) {
        Queue<Item> items = new ArrayDeque<>();
        items.add(item);
        while (items.size() > 0) {
            Item currentTarget = items.remove();

        }
    }

    public static boolean targetedChange(Item item, Memory memory) {
        return true;
    }
}
