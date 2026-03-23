package Enums;

public enum Number {
    EMPTY(-1),
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3);

    public final int value;
    Number(int value) {
        this.value = value;
    }
    public String toString(boolean pretty) {
        if (pretty) {
            if (this == EMPTY) {
                return " ";
            } else {
                return Integer.toString(value);
            }
        } else {
            return toString();
        }
    }
    public static Number getNumber(int value) {
        return switch (value) {
            case 0 -> ZERO;
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            default -> EMPTY;
        };
    }
}
