package Memory;

import Enums.CardinalDirection;

import java.util.ArrayList;

public class FullMemory {
    private final int xSize;
    private final int ySize;
    private final LineMemory lines;
    private final NumberMemory numbers;
    private final HighlightMemory highlights;
    private final DiagonalMemory diagonals;
    private final ArrayList<Changes> changes;

    public FullMemory(int xSize, int ySize, LineMemory lines, NumberMemory numbers, HighlightMemory highlights, DiagonalMemory diagonals, ArrayList<Changes> changes) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.lines = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.diagonals = diagonals;
        this.changes = changes;
    }
    public FullMemory(NumberMemory numbers) {
        this(numbers.getXSize(), numbers.getYSize(),
                new LineMemory(numbers.getXSize(), numbers.getYSize()),
                numbers.copy(),
                new HighlightMemory(numbers.getXSize(), numbers.getYSize()),
                new DiagonalMemory(numbers.getXSize(), numbers.getYSize()),
                new ArrayList<>());
    }
    public FullMemory(int xSize, int ySize) {
        this(xSize,
                ySize,
                new LineMemory(xSize, ySize),
                new NumberMemory(xSize, ySize),
                new HighlightMemory(xSize, ySize),
                new DiagonalMemory(xSize, ySize),
                new ArrayList<>());
    }
    public FullMemory() {
        this(20,20);
    }
    public FullMemory copy() {
        ArrayList<Changes> changes = new ArrayList<>();
        for (Changes change : this.changes) {
            changes.add(change.copy());
        }
        return new FullMemory(
                xSize,
                ySize,
                lines.copy(),
                numbers.copy(),
                highlights.copy(),
                diagonals.copy(),
                changes
        );
    }

    public int getXSize() {
        return xSize;
    }
    public int getYSize() {
        return ySize;
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

    public void change(Changes change) {
        if (change != null) {
            changes.add(change);
        }
    }
    public ArrayList<Changes> getChanges() {
        return changes;
    }

    public void print() {
        for (int y = 0; y < ySize; y++) {
            System.out.print(". ");
            for (int x = 0; x < xSize; x++) {
                System.out.print(getLines().getSquare(x, y, CardinalDirection.NORTH).toString(false));
                System.out.print(" . ");
            }
            System.out.println();

            for (int x = 0; x < xSize; x++) {
                System.out.print(getLines().getSquare(x, y, CardinalDirection.WEST).toString(true) + " ");
                System.out.print(getNumbers().get(x, y).toString(true) + " ");
            }

            System.out.print(getLines().getSquare(xSize - 1, y, CardinalDirection.EAST).toString(true));
            System.out.println();

        }
        System.out.print(". ");
        for (int x = 0; x < xSize; x++) {
            System.out.print(getLines().getSquare(x, ySize - 1, CardinalDirection.SOUTH).toString(false));
            System.out.print(" . ");
        }
        System.out.println();
    }
    public void printNumbers() {
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                System.out.print(numbers.get(x, y) + " ");
            }
            System.out.println();
        }
    }
    public void printHighlight() {
        for (int y = 0; y < ySize; y++) {
            System.out.print("     ");
            for (int x = 0; x < xSize; x++) {
                System.out.print(getLines().getSquare(x, y, CardinalDirection.NORTH));
                System.out.print("      ");
            }
            System.out.println();

            for (int x = 0; x < xSize; x++) {
                System.out.print(getLines().getSquare(x, y, CardinalDirection.WEST));
                System.out.print(getHighlights().get(x, y));
                System.out.print(" ");
            }

            System.out.print(getLines().getSquare(xSize - 1, y, CardinalDirection.EAST));
            System.out.println();

        }
        System.out.print("     ");
        for (int x = 0; x < xSize; x++) {
            System.out.print(getLines().getSquare(x, ySize - 1, CardinalDirection.SOUTH));
            System.out.print("      ");
        }
        System.out.println();
    }
}
