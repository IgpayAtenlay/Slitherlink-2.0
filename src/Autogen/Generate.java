package Autogen;

import Memory.Coords;
import Memory.MemorySet;

public class Generate {
    public static void generate(MemorySet memorySet) {
        GenerateShape.generate(memorySet.getCalculation());
        GenerateLines.generate(memorySet.getCalculation());
        GenerateNumbers.generate(memorySet.getCalculation());
        for (int x = 0; x < memorySet.getCalculation().getDimentions().xSize; x++) {
            for (int y = 0; y < memorySet.getCalculation().getDimentions().ySize; y++) {
                Coords currentCoord = new Coords(x, y);
                memorySet.getStart().setNumber(memorySet.getCalculation().getNumber(currentCoord), currentCoord, true);
            }
        }
        TrimNumbers.trim(memorySet.getStart());
        memorySet.reset();
    }
}
