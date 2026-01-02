package Autogen;

import Autogen.Numbers.GenerateNumbers;
import Autogen.Shape.GenerateShape;
import Memory.Memory;

public class Generate {
    public static void generate(Memory puzzle) {
        GenerateShape.generate(puzzle);
        GenerateNumbers.generate(puzzle);
    }
}
