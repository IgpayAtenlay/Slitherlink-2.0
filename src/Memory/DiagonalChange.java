package Memory;

import Enums.Diagonal;

public class DiagonalChange extends Changes {
    public final Diagonal current;
    public final Diagonal previous;

    public DiagonalChange(Diagonal current, Diagonal previous, int index) {
        super(index);
        this.current = current;
        this.previous = previous;
    }
    @Override
    public DiagonalChange copy() {
        return new DiagonalChange(current, previous, index);
    }
}
