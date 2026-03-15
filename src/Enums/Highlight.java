package Enums;

public enum Highlight {
    EMPTY,
    INSIDE,
    OUTSIDE;
    Highlight() {
    }

    public Highlight getOpposite() {
        return switch (this) {
            case EMPTY -> EMPTY;
            case INSIDE -> OUTSIDE;
            case OUTSIDE -> INSIDE;
        };
    }
    public boolean equalsNotEmpty(Highlight highlight) {
        if (this == EMPTY) return false;
        return this == highlight;
    }
    public boolean isOpposite(Highlight comparisionHighlight) {
        if (this == EMPTY || comparisionHighlight == EMPTY) return false;
        return getOpposite() == comparisionHighlight;
    }
    public Highlight combine(Highlight highlight) {
        if (this == EMPTY) return highlight;
        if (highlight == EMPTY) return this;
        if (this == highlight) return this;
        throw new RuntimeException("Can't combine highlights");
    }
}
