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
    public boolean isOpposite(Highlight comparisionHighlight) {
        if (this == EMPTY || comparisionHighlight == EMPTY) return false;
        return getOpposite() == comparisionHighlight;
    }
}
