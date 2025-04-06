package Memory;

import SolvingActions.Control;
import Enums.CardinalDirection;
import Enums.Number;

public class MemorySet {
    private final FullMemory visible;
    private final FullMemory calculation;
    private final Control control;

    public MemorySet(FullMemory visible, FullMemory calculation, Control control) {
        this.visible = visible;
        this.calculation = calculation;
        this.control = control;
    }
    public MemorySet(FullMemory visible, FullMemory calculation) {
        this(visible, calculation, new Control(calculation));
    }
    public MemorySet(NumberMemory realNumbers) {
        this(new FullMemory(realNumbers), new FullMemory(realNumbers.copy(false)));
    }
    public MemorySet(int xSize, int ySize) {
        this(new FullMemory(new Dimentions(xSize, ySize)), new FullMemory(new Dimentions(xSize, ySize)));
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
                visible.change(visible.getLines().setSquare(calculation.getLines().getSquare(x, y, CardinalDirection.NORTH), x, y, CardinalDirection.NORTH));
                visible.change(visible.getLines().setSquare(calculation.getLines().getSquare(x, y, CardinalDirection.WEST), x, y, CardinalDirection.WEST));
            }
        }
    }
    public void print() {
        visible.print();
    }

    public FullMemory getVisible() {
        return visible;
    }
    public FullMemory getCalculation() {
        return calculation;
    }
    public Control getControl() {
        return control;
    }
    public void setNumber(Number number, int x, int y) {
        visible.getNumbers().set(number, x, y, true);
        calculation.getNumbers().set(number, x, y, true);
    }
}
