package CompletetionChecking;

import Memory.Memory;

public class Complete {
    public static boolean isComplete(Memory memory) {
        if (!NumberCompletetion.run(memory)) {
            return false;
        }
        if (!PointCompletetion.run(memory)) {
            return false;
        }
        if (!LoopCompletetion.run(memory)) {
            return false;
        }
        return true;
    }
}
