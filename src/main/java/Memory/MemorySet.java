package Memory;

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
    public MemorySet(Memory memory, String filePath) {
        this(memory, memory.copy(), memory.copy(), filePath);
    }
    public MemorySet(Memory memory) {
        this(memory, memory.copy(), memory.copy(), "customPuzzles/newPuzzle.json");
    }
    public MemorySet(Dimentions dimentions) {
        this(new Memory(dimentions));
    }
    public MemorySet copy() {
        return new MemorySet(visible.copy(), calculation.copy(), start.copy(), filePath);
    }

    public void reset() {
        visible.copyMemory(start);
        calculation.copyMemory(start);
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
}
