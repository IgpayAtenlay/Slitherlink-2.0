package Memory;

import Enums.*;
import Enums.Number;
import Util.Indexes;

import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
    private final Dimentions dimentions;
    private final Line[] lines;
    private final Number[] numbers;
    private final Highlight[] highlights;
    private final Diagonal[] diagonals;
    private final Loop[] loops;
    private final ArrayList<Changes> changes;

    public Memory(Dimentions dimentions, Line[] lines, Number[] numbers, Highlight[] highlights, Diagonal[] diagonals, Loop[] loops, ArrayList<Changes> changes) {
        this.dimentions = dimentions;
        this.lines = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.diagonals = diagonals;
        this.loops = loops;
        this.changes = changes;
    }
    public Memory(Dimentions dimentions) {
        this(
                dimentions,
                new Line[dimentions.xSize * (dimentions.ySize + 1) + dimentions.ySize * (dimentions.xSize + 1)],
                new Number[dimentions.xSize * dimentions.ySize],
                new Highlight[dimentions.xSize * dimentions.ySize],
                new Diagonal[(dimentions.xSize + 1) * (dimentions.ySize + 1) * 4],
                new Loop[(dimentions.xSize + 1) * (dimentions.ySize + 1)],
                new ArrayList<>()
        );
        Arrays.fill(lines, Line.EMPTY);
        Arrays.fill(diagonals, Diagonal.EMPTY);
        Arrays.fill(highlights, Highlight.EMPTY);
        Arrays.fill(numbers, Number.EMPTY);
    }
    public Memory() {
        this(new Dimentions(20, 20));
    }
    public Memory copy() {
        ArrayList<Changes> changes = new ArrayList<>();
        for (Changes change : this.changes) {
            changes.add(change.copy());
        }
        Loop[] loops = new Loop[this.loops.length];
        for (int i = 0; i < this.loops.length; i++) {
            if (this.loops[i] != null) {
                loops[i] = this.loops[i].copy();
            }
        }
        return new Memory(
                dimentions.copy(),
                lines.clone(),
                numbers.clone(),
                highlights.clone(),
                diagonals.clone(),
                loops,
                changes
        );
    }

    public Dimentions getDimentions() {
        return dimentions;
    }
    public ArrayList<Changes> getChanges() {
        return changes;
    }
    public int getNumChanges() {
        return changes.size();
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
                System.out.print(getNumber(coords).toString(true) + " ");
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
                System.out.print(getNumber(new Coords(x, y)) + " ");
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
                System.out.print(getHighlight(coords));
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
    public void setLine(boolean square, Line line, Coords coords, CardinalDirection direction, boolean override) {
        int i = Indexes.line(square, coords, direction, new Dimentions(dimentions.xSize, dimentions.ySize));
        if (i < 0 || i > lines.length) {
            return;
        }
        if (lines[i] != line && (lines[i] == Line.EMPTY || override)) {
//            System.out.println("changing " + coords + " " + direction + " to " + line);
            changes.add(new LineChange(line, lines[i], i));
            lines[i] = line;
            if (line == Line.LINE) {
                setLoop(square, coords, direction);
            }
        }
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
        if (i < 0 || i >= diagonals.length) {
            return Diagonal.BOTH_OR_NEITHER;
        } else {
            return diagonals[i];
        }
    }
    public void setDiagonal(boolean square, Diagonal diagonal, Coords coords, DiagonalDirection direction, boolean override) {
        int i = Indexes.diagonal(square, coords, direction, dimentions);
        if (i < 0 || i > diagonals.length) {
            return;
        }
        if (diagonals[i] != diagonal) {
            if (diagonals[i] == Diagonal.EMPTY ||
                    override ||
                    ((diagonals[i] == Diagonal.AT_LEAST_ONE || diagonals[i] == Diagonal.AT_MOST_ONE) && (diagonal == Diagonal.EXACTLY_ONE || diagonal == Diagonal.BOTH_OR_NEITHER))
            ) {
//            System.out.println("changing diagonal " + i + " to " + diagonal);
                changes.add(new DiagonalChange(diagonal, diagonals[i], i));
                diagonals[i] = diagonal;
            } else if ((diagonals[i] == Diagonal.AT_LEAST_ONE && diagonal == Diagonal.AT_MOST_ONE) || (diagonal == Diagonal.AT_LEAST_ONE && diagonals[i] == Diagonal.AT_MOST_ONE)) {
                changes.add(new DiagonalChange(Diagonal.EXACTLY_ONE, diagonals[i], i));
                diagonals[i] = Diagonal.EXACTLY_ONE;
            }
        }
    }
    public void setHighlight(Highlight highlight, Coords coords, boolean override) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0 &&
                (highlights[x + y * dimentions.xSize] != highlight && (highlights[x + y * dimentions.xSize] == Highlight.EMPTY || override))
        ) {
            int i = Indexes.box(coords, dimentions);
            changes.add(new HighlightChange(highlight, highlights[i], i));
            highlights[i] = highlight;
        }
    }
    public Highlight getHighlight(Coords coords) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
            return highlights[x + y * dimentions.xSize];
        } else {
            return Highlight.OUTSIDE;
        }
    }
    public void setNumber(Number number, Coords coords, boolean override) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0 &&
                numbers[x + y * dimentions.xSize] != number && (numbers[x + y * dimentions.xSize] == Number.EMPTY || override)) {
            int i = Indexes.box(coords, dimentions);
            changes.add(new NumberChange(number, numbers[i], i));
            numbers[i] = number;
        }
    }
    public Number getNumber(Coords coords) {
        int x = coords.x;
        int y = coords.y;
        if (x < dimentions.xSize && y < dimentions.ySize && x >= 0 && y >= 0) {
            return numbers[x + y * dimentions.xSize];
        } else {
            return Number.EMPTY;
        }
    }
    public void setLoop(boolean square, Coords coordOne, CardinalDirection direction) {
        if (square) {
            switch (direction) {
                case NORTH -> setLoop(false, coordOne, CardinalDirection.EAST);
                case EAST -> setLoop(false, coordOne.addDirection(CardinalDirection.EAST), CardinalDirection.SOUTH);
                case SOUTH -> setLoop(false, coordOne.addDirection(CardinalDirection.SOUTH), CardinalDirection.EAST);
                case WEST -> setLoop(false, coordOne, CardinalDirection.SOUTH);
            };
        } else {
            Coords coordTwo = coordOne.addDirection(direction);
            if (Indexes.point(coordOne, dimentions) < 0 || Indexes.point(coordOne, dimentions) >= loops.length || Indexes.point(coordTwo, dimentions) < 0 || Indexes.point(coordTwo, dimentions) >= loops.length) {
                return;
            }
            if (loops[Indexes.point(coordOne, dimentions)] != null && loops[Indexes.point(coordOne, dimentions)].coords.equals(coordTwo)) {
                loops[Indexes.point(coordOne, dimentions)] = null;
                loops[Indexes.point(coordTwo, dimentions)] = null;
                return;
            }

            Loop oneLoopEnd = loops[Indexes.point(coordOne, dimentions)];
            Loop twoLoopEnd = loops[Indexes.point(coordTwo, dimentions)];

            int length = 1;
            if (oneLoopEnd != null) {
                length += oneLoopEnd.length;
            }
            if (twoLoopEnd != null) {
                length += twoLoopEnd.length;
            }

            Loop newOneLoop = new Loop(twoLoopEnd == null ? coordTwo : twoLoopEnd.coords, length);
            Loop newTwoLoop = new Loop(oneLoopEnd == null ? coordOne : oneLoopEnd.coords, length);

            if (oneLoopEnd == null) {
                loops[Indexes.point(coordOne, dimentions)] = newOneLoop;
            } else {
                loops[Indexes.point(coordOne, dimentions)] = null;
                loops[Indexes.point(oneLoopEnd.coords, dimentions)] = newOneLoop;
            }
            if (twoLoopEnd == null) {
                loops[Indexes.point(coordTwo, dimentions)] = newTwoLoop;
            } else {
                loops[Indexes.point(coordTwo, dimentions)] = null;
                loops[Indexes.point(twoLoopEnd.coords, dimentions)] = newTwoLoop;
            }
        }
    }
    public Loop getLoop(Coords coords) {
        if (Indexes.point(coords, dimentions) < 0 || Indexes.point(coords, dimentions) >= loops.length) {
            return null;
        }
        return loops[Indexes.point(coords, dimentions)];
    }
}
