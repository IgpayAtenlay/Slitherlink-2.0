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
    public Corner addTo(int value)
        throws RuntimeException
    {
        return switch (value) {
            case 0 -> {
                if (this == ZERO) {
                    yield ZERO;
                } else {
                    throw new RuntimeException("Can't add corners to " + value);
                }
            }
            case 1 -> switch (this) {
                case ZERO, NOT_ONE -> ONE;
                case ONE, NOT_ZERO -> ZERO;
                case NOT_TWO, ANY -> NOT_TWO;
                case TWO -> throw new RuntimeException("Can't add corners to " + value);
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
                case ZERO -> throw new RuntimeException("Can't add corners to " + value);
                case ONE, NOT_TWO -> TWO;
                case TWO, NOT_ONE -> ONE;
                case NOT_ZERO, ANY -> NOT_ZERO;
            };
            case 4 -> {
                if (this == TWO) {
                    yield TWO;
                } else {
                    throw new RuntimeException("Can't add corners to " + value);
                }
            }
            default -> throw new RuntimeException("Can't add corners to " + value);
        };
    }
    public Corner combine(Corner corner)
        throws RuntimeException
    {
        boolean zero = this.zero && corner.zero;
        boolean one = this.one && corner.one;
        boolean two = this.two && corner.two;
        try {
            return getCorner(zero, one, two);
        } catch (Exception e) {
            throw new RuntimeException("Corner with no options");
        }

    }
    public static Corner getCorner(boolean zero, boolean one, boolean two)
        throws RuntimeException
    {
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
                    throw new RuntimeException("Corner with no options");
                }
            }
        }
    }
    public boolean even() {
        return (zero || two) && !one;
    }
    public boolean oneOption() {
        return switch (this) {
            case ZERO, ONE, TWO -> true;
            default -> false;
        };
    }
}
