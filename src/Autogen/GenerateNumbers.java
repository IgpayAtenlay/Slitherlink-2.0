package Autogen;

import Enums.Number;
import Memory.Coords;
import Memory.Memory;

public class GenerateNumbers {
    public static void generate(Memory puzzle) {
        for (int x = 0; x < puzzle.getDimentions().xSize; x++) {
            for (int y = 0; y < puzzle.getDimentions().ySize; y++) {
                Coords currentCoords = new Coords(x, y);
                puzzle.setNumber(Number.getNumber(puzzle.getNumLines(currentCoords)), currentCoords, true);
            }
        }
    }
}
