package Memory;

import Enums.CardinalDirection;
import Enums.Line;
import Util.Indexes;

import java.util.ArrayList;
import java.util.Arrays;

public class FullMemory {
    private final Dimentions dimentions;
    private final Line[] memory;
    private final NumberMemory numbers;
    private final HighlightMemory highlights;
    private final DiagonalMemory diagonals;
    private final LoopMemory loops;
    private final ArrayList<Changes> changes;

    public FullMemory(Dimentions dimentions, Line[] lines, NumberMemory numbers, HighlightMemory highlights, DiagonalMemory diagonals, LoopMemory loops, ArrayList<Changes> changes) {
        this.dimentions = dimentions;
        this.memory = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.diagonals = diagonals;
        this.loops = loops;
        this.changes = changes;
    }
    private FullMemory(Dimentions dimentions, NumberMemory numbers) {
        this(
                dimentions,
                new Line[dimentions.xSize * (dimentions.ySize + 1) + dimentions.ySize * (dimentions.xSize + 1)],
                numbers,
                new HighlightMemory(dimentions),
                new DiagonalMemory(dimentions),
                new LoopMemory(dimentions),
                new ArrayList<>()
        );
        Arrays.fill(memory, Line.EMPTY);
    }
    public FullMemory(NumberMemory numbers) {
        this(numbers.getDimentions(), numbers.copy());
    }
    public FullMemory(Dimentions dimentions) {
        this(dimentions, new NumberMemory(dimentions.copy()));
    }
    public FullMemory() {
        this(new Dimentions(20, 20));
    }
    public FullMemory copy() {
        ArrayList<Changes> changes = new ArrayList<>();
        for (Changes change : this.changes) {
            changes.add(change.copy());
        }
        return new FullMemory(
                dimentions.copy(),
                memory.clone(),
                numbers.copy(),
                highlights.copy(),
                diagonals.copy(),
                loops.copy(),
                changes
        );
    }

    public Dimentions getDimentions() {
        return dimentions;
    }
    public NumberMemory getNumbers() {
        return numbers;
    }
    public HighlightMemory getHighlights() {
        return highlights;
    }
    public DiagonalMemory getDiagonals() {
        return diagonals;
    }
    public LoopMemory getLoops() {
        return loops;
    }

    public void change(Changes change) {
        if (change != null) {
            changes.add(change);
        }
    }
    public ArrayList<Changes> getChanges() {
        return changes;
    }

    public void print() {
        for (int y = 0; y < dimentions.ySize; y++) {
            System.out.print(". ");
            for (int x = 0; x < dimentions.xSize; x++) {
                System.out.print(getLine(true, new Coords(x, y), CardinalDirection.NORTH).toString(false));
                System.out.print(" . ");
            }
            System.out.println();

            for (int x = 0; x < dimentions.xSize; x++) {
                Coords coords = new Coords(x, y);
                System.out.print(getLine(true, coords, CardinalDirection.WEST).toString(true) + " ");
                System.out.print(getNumbers().get(coords).toString(true) + " ");
            }

            System.out.print(getLine(true, new Coords(dimentions.xSize - 1, y), CardinalDirection.EAST).toString(true));
            System.out.println();

        }
        System.out.print(". ");
        for (int x = 0; x < dimentions.xSize; x++) {
            System.out.print(getLine(true, new Coords(x, dimentions.ySize - 1), CardinalDirection.SOUTH).toString(false));
            System.out.print(" . ");
        }
        System.out.println();
    }
    public void printNumbers() {
        for (int y = 0; y < dimentions.ySize; y++) {
            for (int x = 0; x < dimentions.xSize; x++) {
                System.out.print(numbers.get(new Coords(x, y)) + " ");
            }
            System.out.println();
        }
    }
    public void printHighlight() {
        for (int y = 0; y < dimentions.ySize; y++) {
            System.out.print("     ");
            for (int x = 0; x < dimentions.xSize; x++) {
                System.out.print(getLine(true, new Coords(x, y), CardinalDirection.NORTH));
                System.out.print("      ");
            }
            System.out.println();

            for (int x = 0; x < dimentions.xSize; x++) {
                Coords coords = new Coords(x, y);
                System.out.print(getLine(true, coords, CardinalDirection.WEST));
                System.out.print(getHighlights().get(coords));
                System.out.print(" ");
            }

            System.out.print(getLine(true, new Coords(dimentions.xSize - 1, y), CardinalDirection.EAST));
            System.out.println();

        }
        System.out.print("     ");
        for (int x = 0; x < dimentions.xSize; x++) {
            System.out.print(getLine(true, new Coords(x, dimentions.ySize - 1), CardinalDirection.SOUTH));
            System.out.print("      ");
        }
        System.out.println();
    }
    
    // setters and getters
    public Changes setLine(boolean square, Line line, Coords coords, CardinalDirection direction, boolean override) {
        int i = Indexes.line(square, coords, direction, new Dimentions(dimentions.xSize, dimentions.ySize));
        if (i < 0 || i > memory.length) {
            return null;
        }
        if (memory[i] != line && (memory[i] == Line.EMPTY || override)) {
//            System.out.println("changing " + coords + " " + direction + " to " + line);
            memory[i] = line;
            if (line == Line.LINE) {
                loops.setLoop(square, coords, direction);
            }
            return new Changes(line, i);
        }
        return null;
    }
    public Line getLine(boolean square, Coords coords, CardinalDirection direction) {
        int index = Indexes.line(square, coords, direction, dimentions);
        if (index < 0 || index >= memory.length) {
            return Line.X;
        } else {
            return memory[index];
        }
    }
    public int getNumLines() {
        int totalLines = 0;
        for (Line line : memory) {
            if (line == Line.LINE) {
                totalLines++;
            }
        }

        return totalLines;
    }
}
