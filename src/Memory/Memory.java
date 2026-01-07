package Memory;

import Enums.Number;
import Enums.*;
import Memory.Changes.*;
import Util.Indexes;
import Util.JsonConverter;

import java.util.Arrays;
import java.util.Stack;

public class Memory {
    private final Dimentions dimentions;
    private final Line[] lines;
    private final Number[] numbers;
    private final Highlight[] highlights;
    private final Corner[] corners;
    private final Loop[] loops;
    private final Stack<Changes> undo;
    private final Stack<Changes> redo;

    public Memory(Dimentions dimentions, Line[] lines, Number[] numbers, Highlight[] highlights, Corner[] corners, Loop[] loops, Stack<Changes> undo, Stack<Changes> redo) {
        this.dimentions = dimentions;
        this.lines = lines;
        this.numbers = numbers;
        this.highlights = highlights;
        this.corners = corners;
        this.loops = loops;
        this.undo = undo;
        this.redo = redo;
    }
    public Memory(Dimentions dimentions, Line[] lines, Number[] numbers, Highlight[] highlights, Corner[] corners) {
        this(dimentions, lines, numbers, highlights, corners,
                new Loop[(dimentions.xSize + 1) * (dimentions.ySize + 1)],
                new Stack<>(),
                new Stack<>()
        );
    }
    public Memory(Dimentions dimentions) {
        this(
                dimentions,
                new Line[dimentions.xSize * (dimentions.ySize + 1) + dimentions.ySize * (dimentions.xSize + 1)],
                new Number[dimentions.xSize * dimentions.ySize],
                new Highlight[dimentions.xSize * dimentions.ySize],
                new Corner[(dimentions.xSize + 1) * (dimentions.ySize + 1) * 4],
                new Loop[(dimentions.xSize + 1) * (dimentions.ySize + 1)],
                new Stack<>(),
                new Stack<>()
        );
        Arrays.fill(lines, Line.EMPTY);
        Arrays.fill(corners, Corner.EMPTY);
        Arrays.fill(highlights, Highlight.EMPTY);
        Arrays.fill(numbers, Number.EMPTY);
    }
    public Memory() {
        this(new Dimentions(20, 20));
    }
    public Memory copy() {
        Stack<Changes> undo = new Stack<>();
        for (Changes change : this.undo) {
            undo.push(change.copy());
        }
        Stack<Changes> redo = new Stack<>();
        for (Changes change : this.redo) {
            redo.push(change.copy());
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
                corners.clone(),
                loops,
                undo,
                redo
        );
    }

    public Dimentions getDimentions() {
        return dimentions;
    }
    public int getNumChanges() {
        return undo.size();
    }

    public String getJson() {
        return "{" +
                "\n    \"xSize\": " + dimentions.xSize + "," +
                "\n    \"ySize\": " + dimentions.ySize + "," +
                "\n    \"lines\": " + JsonConverter.javaToJson(lines) + "," +
                "\n    \"numbers\": " + JsonConverter.javaToJson(numbers) + "," +
                "\n    \"highlights\": " + JsonConverter.javaToJson(highlights) + "," +
                "\n    \"corners\": " + JsonConverter.javaToJson(corners) + "," +
                "\n    \"loops\": " + JsonConverter.javaToJson(loops) +
                "\n}";
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
            change(new LineChange(line, lines[i], i));
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
    public int getNumLines(Coords coords) {
        int numLines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (getLine(true, coords, direction) == Line.LINE) {
                numLines++;
            }
        }
        return numLines;
    }
    public Corner getCorner(boolean square, Coords coords, DiagonalDirection direction) {
        int i = Indexes.diagonal(square, coords, direction, dimentions);
        if (i < 0 || i >= corners.length) {
            return Corner.BOTH_OR_NEITHER;
        } else {
            return corners[i];
        }
    }
    public void setCorner(boolean square, Corner corner, Coords coords, DiagonalDirection direction, boolean override) {
        int i = Indexes.diagonal(square, coords, direction, dimentions);
        if (i < 0 || i > corners.length) {
            return;
        }
        if (corners[i] != corner) {
            if (corners[i] == Corner.EMPTY ||
                    override ||
                    ((corners[i] == Corner.AT_LEAST_ONE || corners[i] == Corner.AT_MOST_ONE) && (corner == Corner.EXACTLY_ONE || corner == Corner.BOTH_OR_NEITHER))
            ) {
//            System.out.println("changing diagonal " + i + " to " + diagonal);
                change(new CornerChange(corner, corners[i], i));
                corners[i] = corner;
            } else if ((corners[i] == Corner.AT_LEAST_ONE && corner == Corner.AT_MOST_ONE) || (corner == Corner.AT_LEAST_ONE && corners[i] == Corner.AT_MOST_ONE)) {
                change(new CornerChange(Corner.EXACTLY_ONE, corners[i], i));
                corners[i] = Corner.EXACTLY_ONE;
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
            change(new HighlightChange(highlight, highlights[i], i));
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
            change(new NumberChange(number, numbers[i], i));
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

    public void undo(int reps) {
        for (int i = 0; i < reps; i++) {
            if (undo.size() > 0) {
                Changes change = undo.pop();
                redo.push(change);
                if (change instanceof LineChange) {
                    lines[change.index] = ((LineChange) change).previous;
                } else if (change instanceof NumberChange) {
                    numbers[change.index] = ((NumberChange) change).previous;
                } else if (change instanceof CornerChange) {
                    corners[change.index] = ((CornerChange) change).previous;
                } else if (change instanceof HighlightChange) {
                    highlights[change.index] = ((HighlightChange) change).previous;
                }
            }
        }
    }
    public void redo(int reps) {
        for (int i = 0; i < reps; i++) {
            if (redo.size() > 0) {
                Changes change = redo.pop();
                undo.push(change);
                if (change instanceof LineChange) {
                    lines[change.index] = ((LineChange) change).current;
                } else if (change instanceof NumberChange) {
                    numbers[change.index] = ((NumberChange) change).current;
                } else if (change instanceof CornerChange) {
                    corners[change.index] = ((CornerChange) change).current;
                } else if (change instanceof HighlightChange) {
                    highlights[change.index] = ((HighlightChange) change).current;
                }
            }
        }
    }
    public void change(Changes changes) {
        undo.push(changes);
        redo.empty();
    }
    public void clearMemory() {
        undo.clear();
        redo.clear();
    }
    public void copyMemory(Memory memory) {
        System.arraycopy(memory.lines, 0, lines, 0, lines.length);
        System.arraycopy(memory.numbers, 0, numbers, 0, numbers.length);
        System.arraycopy(memory.highlights, 0, highlights, 0, highlights.length);
        System.arraycopy(memory.corners, 0, corners, 0, corners.length);
        System.arraycopy(memory.loops, 0, loops, 0, loops.length);
        clearMemory();
    }
}
