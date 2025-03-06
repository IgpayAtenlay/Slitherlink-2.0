package Memory;

public class MemorySet {
    FullMemory visible;
    FullMemory calculation;

    public MemorySet(FullMemory visible, FullMemory calculation) {
        this.visible = visible;
        this.calculation = calculation;
    }
    public MemorySet(NumberMemory realNumbers) {
        this(new FullMemory(realNumbers), new FullMemory(realNumbers.copy()));
    }
    public MemorySet(int xSize, int ySize) {
        this(new FullMemory(xSize, ySize), new FullMemory(xSize, ySize));
    }


}
