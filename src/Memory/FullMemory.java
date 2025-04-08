package Memory;

import Enums.CardinalDirection;

import java.util.ArrayList;

public class FullMemory {
    private final Dimentions dimentions;
    private final LineMemory lines;
    private final NumberMemory numbers;
    private final HighlightMemory highlights;
    private final DiagonalMemory diagonals;
    private final LoopMemory loops;
    private final ArrayList<Changes> changes;

    public FullMemory(Dimentions dimentions, LineMemory lines, NumberMemory numbers, HighlightMemory highlights, DiagonalMemory diagonals, LoopMemory loops, ArrayList<Changes> changes) {
        this.dimentions = dimentions;
        this.lines = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.diagonals = diagonals;
        this.loops = loops;
        this.changes = changes;
    }
    public FullMemory(NumberMemory numbers) {
        this(numbers.getDimentions(),
                new LineMemory(numbers.getDimentions()),
                numbers.copy(),
                new HighlightMemory(numbers.getDimentions()),
                new DiagonalMemory(numbers.getDimentions()),
                new LoopMemory(numbers.getDimentions()),
                new ArrayList<>());
    }
    public FullMemory(Dimentions dimentions) {
        this(dimentions,
                new LineMemory(dimentions.copy()),
                new NumberMemory(dimentions.copy()),
                new HighlightMemory(dimentions.copy()),
                new DiagonalMemory(dimentions.copy()),
                new LoopMemory(dimentions.copy()),
                new ArrayList<>());
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
                lines.copy(),
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
    public LineMemory getLines() {
        return lines;
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
                System.out.print(getLines().getSquare(new Coords(x, y), CardinalDirection.NORTH).toString(false));
                System.out.print(" . ");
            }
            System.out.println();

            for (int x = 0; x < dimentions.xSize; x++) {
                Coords coords = new Coords(x, y);
                System.out.print(getLines().getSquare(coords, CardinalDirection.WEST).toString(true) + " ");
                System.out.print(getNumbers().get(coords).toString(true) + " ");
            }

            System.out.print(getLines().getSquare(new Coords(dimentions.xSize - 1, y), CardinalDirection.EAST).toString(true));
            System.out.println();

        }
        System.out.print(". ");
        for (int x = 0; x < dimentions.xSize; x++) {
            System.out.print(getLines().getSquare(new Coords(x, dimentions.ySize - 1), CardinalDirection.SOUTH).toString(false));
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
                System.out.print(getLines().getSquare(new Coords(x, y), CardinalDirection.NORTH));
                System.out.print("      ");
            }
            System.out.println();

            for (int x = 0; x < dimentions.xSize; x++) {
                Coords coords = new Coords(x, y);
                System.out.print(getLines().getSquare(coords, CardinalDirection.WEST));
                System.out.print(getHighlights().get(coords));
                System.out.print(" ");
            }

            System.out.print(getLines().getSquare(new Coords(dimentions.xSize - 1, y), CardinalDirection.EAST));
            System.out.println();

        }
        System.out.print("     ");
        for (int x = 0; x < dimentions.xSize; x++) {
            System.out.print(getLines().getSquare(new Coords(x, dimentions.ySize - 1), CardinalDirection.SOUTH));
            System.out.print("      ");
        }
        System.out.println();
    }
}
