package Enums;

public enum Corner {
    EMPTY,
    MAX_ONE,
    DIFFERENT,
    MIN_ONE,
    SAME;
    Corner() {
    }
    public boolean atLeastOne() {
        return this == DIFFERENT || this == MIN_ONE;
    }
}
