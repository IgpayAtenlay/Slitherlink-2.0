package CompletetionChecking;

import Memory.Memory;

public class Complete {
    public static boolean isComplete(Memory memory) {
        if (!NumberCompletetion.isCorrectNumbers(memory)) {
            return false;
        }
        if (!PointCompletetion.isCorrectPoints(memory)) {
            return false;
        }
        if (!LoopCompletetion.hasExactlyOneLoop(memory)) {
            return false;
        }
        return true;
    }
}
