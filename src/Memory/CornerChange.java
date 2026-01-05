package Memory;

import Enums.Corner;

public class CornerChange extends Changes {
    public final Corner current;
    public final Corner previous;

    public CornerChange(Corner current, Corner previous, int index) {
        super(index);
        this.current = current;
        this.previous = previous;
    }
    @Override
    public CornerChange copy() {
        return new CornerChange(current, previous, index);
    }
}
