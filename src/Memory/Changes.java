package Memory;

import Enums.*;
import Enums.Number;

public class Changes {
    public final DataType type;
    public final Number number;
    public final Line line;
    public final Highlight highlight;
    public final Diagonal diagonal;
    public final int index;

    private Changes(DataType type,
            Number number, Line line, Highlight highlight, Diagonal diagonal,
            int index
    ) {
        this.type = type;
        this.number = number;
        this.line = line;
        this.highlight = highlight;
        this.diagonal = diagonal;
        this.index = index;
    }
    public Changes(Number number, int index) {
        this(DataType.NUMBER,
                number, null, null, null,
                index);
    }
    public Changes(Line line, int index) {
        this(DataType.LINE,
                null, line, null, null,
                index);
    }
    public Changes(Highlight highlight, int index) {
        this(DataType.HIGHLIGHT,
                null, null, highlight, null,
                index);
    }
    public Changes(Diagonal diagonal, int index) {
        this(DataType.DIAGONAL,
                null, null, null, diagonal,
                index);
    }
    public Changes copy() {
        return new Changes(type, number, line, highlight, diagonal, index);
    }
}
