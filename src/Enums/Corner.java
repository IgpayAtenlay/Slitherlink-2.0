package Enums;

public enum Corner {
    ANY(true, true, true),
    ZERO(true, false, false),
    ONE(false, true, false),
    TWO(false, false, true),
    NOT_ZERO(false, true, true),
    NOT_ONE(true, false, true),
    NOT_TWO(true, true, false);
    public final boolean zero, one, two;
    Corner(boolean zero, boolean one, boolean two) {
        this.zero = zero;
        this.one = one;
        this.two = two;
    }
    public boolean atLeastOne() {
        return !this.zero;
    }
    public Corner addTo(int value) {
        return switch (value) {
            case 0 -> this == ZERO ? ZERO : null;
            case 1 -> switch (this) {
                case ZERO, NOT_ONE -> ONE;
                case ONE, NOT_ZERO -> ZERO;
                case NOT_TWO, ANY -> NOT_TWO;
                case TWO -> null;
            };
            case 2 -> switch (this) {
                case ZERO -> TWO;
                case ONE -> ONE;
                case TWO -> ZERO;
                case NOT_ZERO -> NOT_TWO;
                case NOT_ONE -> NOT_ONE;
                case NOT_TWO -> NOT_ZERO;
                case ANY -> ANY;
            };
            case 3 -> switch (this) {
                case ZERO -> null;
                case ONE, NOT_TWO -> TWO;
                case TWO, NOT_ONE -> ONE;
                case NOT_ZERO, ANY -> NOT_ZERO;
            };
            case 4 -> this == TWO ? TWO : null;
            default -> null;
        };
    }
    public Corner combine(Corner corner) {
        boolean zero = this.zero && corner.zero;
        boolean one = this.one && corner.one;
        boolean two = this.two && corner.two;
        return getCorner(zero, one, two);
    }
    public static Corner getCorner(boolean zero, boolean one, boolean two) {
        if (zero) {
            if (one) {
                if (two) {
                    return ANY;
                } else {
                    return NOT_TWO;
                }
            } else {
                if (two) {
                    return NOT_ONE;
                } else {
                    return ZERO;
                }
            }
        } else {
            if (one) {
                if (two) {
                    return NOT_ZERO;
                } else {
                    return ONE;
                }
            } else {
                if (two) {
                    return TWO;
                } else {
                    return null;
                }
            }
        }
    }
    public boolean even() {
        return (zero || two) && !one;
    }
}
