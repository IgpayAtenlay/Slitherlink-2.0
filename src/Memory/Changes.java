package Memory;

import Enums.*;
import Enums.Number;

public class Changes {
    public final DataType type;
    public final Number number;
    public final Line line;
    public final Highlight highlight;
    public final Diagonal diagonal;
    public final int x;
    public final int y;
    public final CardinalDirection cardinalDirection;
    public final DiagonalDirection diagonalDirection;

    private Changes(DataType type,
            Number number, Line line, Highlight highlight, Diagonal diagonal,
            int x, int y,
            CardinalDirection cardinalDirection, DiagonalDirection diagonalDirection
    ) {
        this.type = type;
        this.number = number;
        this.line = line;
        this.highlight = highlight;
        this.diagonal = diagonal;
        this.x = x;
        this.y = y;
        this.cardinalDirection = cardinalDirection;
        this.diagonalDirection = diagonalDirection;
    }
    public Changes(Number number, int x, int y) {
        this(DataType.NUMBER,
                number, null, null, null,
                x, y,
                null, null);
    }
    public Changes(Line line, int x, int y, CardinalDirection direction) {
        this(DataType.LINE,
                null, line, null, null,
                x, y,
                direction, null);
    }
    public Changes(Highlight highlight, int x, int y) {
        this(DataType.HIGHLIGHT,
                null, null, highlight, null,
                x, y,
                null, null);
    }
    public Changes(Diagonal diagonal, int x, int y, DiagonalDirection direction) {
        this(DataType.DIAGONAL,
                null, null, null, diagonal,
                x, y,
                null, direction);
    }
    public Changes copy() {
        return new Changes(type, number, line, highlight, diagonal, x, y, cardinalDirection, diagonalDirection);
    }
}
