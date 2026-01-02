package Autogen;

import Memory.Memory;

public class Generate {
    public static void generate(Memory puzzle) {
        GenerateShape.generate(puzzle);
        GenerateLines.generate(puzzle);
        GenerateNumbers.generate(puzzle);
    }
}
