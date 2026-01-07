package ErrorChecking;

import Memory.Memory;

public class Errors {
    public static boolean hasErrors(Memory memory) {
        if (NumberErrors.hasNumberError(memory)) {
            return true;
        }
        if (LineErrors.run(memory)) {
            return true;
        }
        if (LoopErrors.hasLoopProblem(memory)) {
            return true;
        }
        return false;
    }
}
