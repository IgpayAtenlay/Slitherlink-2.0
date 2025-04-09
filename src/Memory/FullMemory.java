package Memory;

import Enums.CardinalDirection;
import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Util.Indexes;

import java.util.ArrayList;
import java.util.Arrays;

public class FullMemory {
    private final Dimentions dimentions;
    private final Line[] lines;
    private final NumberMemory numbers;
    private final HighlightMemory highlights;
    private final Diagonal[] memory;
    private final LoopMemory loops;
    private final ArrayList<Changes> changes;

    public FullMemory(Dimentions dimentions, Line[] lines, NumberMemory numbers, HighlightMemory highlights, Diagonal[] diagonals, LoopMemory loops, ArrayList<Changes> changes) {
        this.dimentions = dimentions;
        this.lines = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.memory = diagonals;
        this.loops = loops;
        this.changes = changes;
    }
    private FullMemory(Dimentions dimentions, NumberMemory numbers) {
        this(
                dimentions,
                new Line[dimentions.xSize * (dimentions.ySize + 1) + dimentions.ySize * (dimentions.xSize + 1)],
                numbers,
                new HighlightMemory(dimentions),
                new Diagonal[(dimentions.xSize + 1) * (dimentions.ySize + 1) * 4],
                new LoopMemory(dimentions),
                new ArrayList<>()
        );
        Arrays.fill(lines, Line.EMPTY);
        Arrays.fill(memory, Diagonal.EMPTY);
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
                lines.clone(),
                numbers.copy(),
                highlights.copy(),
                memory.clone(),
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
        if (i < 0 || i > lines.length) {
            return null;
        }
        if (lines[i] != line && (lines[i] == Line.EMPTY || override)) {
//            System.out.println("changing " + coords + " " + direction + " to " + line);
            lines[i] = line;
            if (line == Line.LINE) {
                loops.setLoop(square, coords, direction);
            }
            return new Changes(line, i);
        }
        return null;
    }
    public Line getLine(boolean square, Coords coords, CardinalDirection direction) {
        int index = Indexes.line(square, coords, direction, dimentions);
        if (index < 0 || index >= lines.length) {
            return Line.X;
        } else {
            return lines[index];
        }
    }
    public int getNumLines() {
        int totalLines = 0;
        for (Line line : lines) {
            if (line == Line.LINE) {
                totalLines++;
            }
        }

        return totalLines;
    }
    public Diagonal getDiagonal(boolean square, Coords coords, DiagonalDirection direction) {
        int i = Indexes.diagonal(square, coords, direction, dimentions);
        if (i < 0 || i >= memory.length) {
            return Diagonal.BOTH_OR_NEITHER;
        } else {
            return memory[i];
        }
    }
    public Changes setDiagonal(boolean square, Diagonal diagonal, Coords coords, DiagonalDirection direction, boolean override) {
        int i = Indexes.diagonal(square, coords, direction, dimentions);
        if (i < 0 || i > memory.length) {
            return null;
        }
        if (memory[i] != diagonal &&
                (memory[i] == Diagonal.EMPTY ||
                        override ||
                        ((memory[i] == Diagonal.AT_LEAST_ONE || memory[i] == Diagonal.AT_MOST_ONE) && (diagonal == Diagonal.EXACTLY_ONE || diagonal == Diagonal.BOTH_OR_NEITHER)))
        ) {
//            System.out.println("changing diagonal " + i + " to " + diagonal);
            memory[i] = diagonal;
            return new Changes(diagonal, i);
        }

        return null;
    }
}
