package Memory;

import Enums.CardinalDirection;
import Enums.Number;

public class FullMemory {
    private final int xSize;
    private final int ySize;
    private final LineMemory lines;
    private final NumberMemory logicNumbers;
    private final NumberMemory realNumbers;
    private final HighlightMemory highlights;
    private final DiagonalMemory diagonals;

    public FullMemory(int xSize, int ySize, LineMemory lines, NumberMemory realNumbers, NumberMemory logicNumbers, HighlightMemory highlights, DiagonalMemory diagonals) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.lines = lines;
        this.realNumbers = realNumbers;
        this.logicNumbers = logicNumbers;
        this.highlights = highlights;
        this.diagonals = diagonals;
    }
    public FullMemory(NumberMemory realNumbers) {
        this(realNumbers.getXSize(), realNumbers.getYSize(),
                new LineMemory(realNumbers.getXSize(), realNumbers.getYSize()),
                realNumbers,
                realNumbers.copy(false),
                new HighlightMemory(realNumbers.getXSize(), realNumbers.getYSize()),
                new DiagonalMemory(realNumbers.getXSize(), realNumbers.getYSize()));
    }
    public FullMemory(int xSize, int ySize) {
        this(xSize,
                ySize,
                new LineMemory(xSize, ySize),
                new NumberMemory(xSize, ySize),
                new NumberMemory(xSize, ySize),
                new HighlightMemory(xSize, ySize),
                new DiagonalMemory(xSize, ySize));
    }
    public FullMemory() {
        this(20,20);
    }
    public FullMemory copy() {
        return new FullMemory(
                xSize,
                ySize,
                lines.copy(),
                realNumbers.copy(),
                logicNumbers.copy(),
                highlights.copy(),
                diagonals.copy()
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
    public Number getNumber(int x, int y) {
        return realNumbers.get(x, y);
    }
    public Number getLogicNumber(int x, int y) {
        return logicNumbers.get(x, y);
    }
    public boolean setNumber(Number number, int x, int y, boolean override) {
        boolean realNumberChanged = realNumbers.set(number, x, y, override);
        boolean logicNumberChanged = logicNumbers.set(number, x, y, override);
        return realNumberChanged || logicNumberChanged;
    }
    public boolean setLogicNumber(Number number, int x, int y, boolean override) {
        return logicNumbers.set(number, x, y, override);
    }
    public HighlightMemory getHighlights() {
        return highlights;
    }
    public DiagonalMemory getDiagonals() {
        return diagonals;
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
                System.out.print(getNumber(x, y).toString(true) + " ");
            }

            System.out.print(getLines().getSquare(xSize - 1, y, CardinalDirection.EAST).toString(false));
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
                System.out.print(realNumbers.get(x, y) + " ");
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
