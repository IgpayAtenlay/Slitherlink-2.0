package Enums;

public enum Line {
    EMPTY,
    LINE,
    X;
    Line() {
    }

    public Line getOpposite() {
        return switch (this) {
            case EMPTY -> EMPTY;
            case LINE -> X;
            case X -> LINE;
        };
    }

    public String toString(boolean vertical) {
        if (vertical) {
            return switch (this) {
                case EMPTY -> " ";
                case LINE -> "|";
                case X -> "x";
            };
        } else {
            return switch (this) {
                case EMPTY -> " ";
                case LINE -> "―";
                case X -> "x";
            };
        }
    }
    public Line combine(Line line) {
        if (this == EMPTY) return line;
        if (line == EMPTY) return this;
        if (this == line) return this;
        throw new RuntimeException("Can't combine lines");
    }
}
