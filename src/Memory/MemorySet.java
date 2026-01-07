package Memory;

import Enums.CardinalDirection;
import Enums.Number;
import SolvingActions.Control;

import java.nio.file.Path;
import java.nio.file.Paths;

public class MemorySet {
    private final String filePath;
    private final Memory visible;
    private final Memory calculation;
    private final Memory start;

    public MemorySet(Memory visible, Memory calculation, Memory start, String filePath) {
        this.visible = visible;
        this.calculation = calculation;
        this.start = start;
        this.filePath = filePath;
    }
    public MemorySet(Memory visible, Memory calculation, Memory start) {
        this(visible, calculation, start, "customPuzzles/newPuzzle.json");
    }
    public MemorySet(Memory memory, String filePath) {
        this(memory, memory.copy(), memory.copy(), filePath);
    }
    public MemorySet(Memory memory) {
        this(memory, memory.copy(), memory.copy());
    }
    public MemorySet(Dimentions dimentions) {
        this(new Memory(dimentions));
    }
    public MemorySet copy() {
        return new MemorySet(visible.copy(), calculation.copy(), start.copy(), filePath);
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

    public Memory getStart() {
        return start;
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
    public String getFolderPath() {
        Path path = Paths.get("public/puzzles/" + getFilePath());
        Path folder = path.getParent();
        return folder.toString();
    }
    public void setNumber(Number number, Coords coords) {
        visible.setNumber(number, coords, true);
        calculation.setNumber(number, coords, true);
    }
}
