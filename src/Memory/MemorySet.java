package Memory;

import SolvingActions.Control;
import Enums.CardinalDirection;
import Enums.Number;

public class MemorySet {
    private final Memory visible;
    private final Memory calculation;
    private final Control control;

    public MemorySet(Memory visible, Memory calculation, Control control) {
        this.visible = visible;
        this.calculation = calculation;
        this.control = control;
    }
    public MemorySet(Memory visible, Memory calculation) {
        this(visible, calculation, new Control(calculation));
    }
    public MemorySet(Memory memory) {
        this(memory, memory.copy());
    }
    public MemorySet(Dimentions dimentions) {
        this(new Memory(dimentions), new Memory(dimentions.copy()));
    }
    public MemorySet copy() {
        return new MemorySet(visible.copy(), calculation.copy());
    }

    public void autoSolve(boolean guessAndCheck) {
        control.autoSolve(guessAndCheck);
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
    public Control getControl() {
        return control;
    }
    public void setNumber(Number number, Coords coords) {
        visible.setNumber(number, coords, true);
        calculation.setNumber(number, coords, true);
    }
}
