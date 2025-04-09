package ErrorChecking;

import CompletetionChecking.LoopCompletetion;
import CompletetionChecking.NumberCompletetion;
import CompletetionChecking.PointCompletetion;
import Memory.FullMemory;

public class Errors {
    public static boolean hasErrors(FullMemory memory) {
        if (NumberErrors.run(memory)) {
            System.out.println("number error");
            return true;
        }
        if (LineErrors.run(memory)) {
            System.out.println("line error");
            return true;
        }
        if (LoopErrors.run(memory)) {
            System.out.println("loop error");
            return true;
        }
        System.out.println("No errors");
        return false;
    }
    public static boolean isComplete(FullMemory memory) {
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
