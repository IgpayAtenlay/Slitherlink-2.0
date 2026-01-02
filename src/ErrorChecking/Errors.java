package ErrorChecking;

import Memory.Memory;

public class Errors {
    public static boolean hasErrors(Memory memory) {
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
}
