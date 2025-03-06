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

    public String toString() {
        return switch (this) {
            case EMPTY -> "E";
            case LINE -> "_";
            case X -> "X";
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
}
