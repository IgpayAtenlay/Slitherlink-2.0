package Memory;

import SolvingActions.Control;
import Enums.CardinalDirection;
import Enums.Number;

public class MemorySet {
    private final String filePath;
    private final Memory visible;
    private final Memory calculation;

    public MemorySet(Memory visible, Memory calculation, String filePath) {
        this.visible = visible;
        this.calculation = calculation;
        this.filePath = filePath;
    }
    public MemorySet(Memory visible, Memory calculation) {
        this(visible, calculation, "customPuzzles/newPuzzle.json");
    }
    public MemorySet(Memory memory, String filePath) {
        this(memory, memory.copy(), filePath);
    }
    public MemorySet(Dimentions dimentions) {
        this(new Memory(dimentions), new Memory(dimentions.copy()));
    }
    public MemorySet copy() {
        return new MemorySet(visible.copy(), calculation.copy());
    }

    public void autoSolve(boolean guessAndCheck) {
        Control.autoSolve(calculation, guessAndCheck);
    }
    public void linesCalculationToVisible() {
        for (int y = 0; y < calculation.getDimentions().ySize + 1; y++) {
            for (int x = 0; x < calculation.getDimentions().xSize + 1; x++) {
                Coords coords = new Coords(x, y);
                visible.setLine(true, calculation.getLine(true, coords, CardinalDirection.NORTH), coords, CardinalDirection.NORTH, false);
                visible.setLine(true, calculation.getLine(true, coords, CardinalDirection.WEST), coords, CardinalDirection.WEST, false);
            }
        }
    }
    public void print() {
        visible.print();
    }

    public Memory getVisible() {
        return visible;
    }
    public Memory getCalculation() {
        return calculation;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setNumber(Number number, Coords coords) {
        visible.setNumber(number, coords, true);
        calculation.setNumber(number, coords, true);
    }
}
