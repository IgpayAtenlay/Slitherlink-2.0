package Memory;

import Enums.Line;

public class LineChange extends Changes {

    public final Line current;
    public final Line previous;

    public LineChange(Line current, Line previous, int index) {
        super(index);
        this.current = current;
        this.previous = previous;
    }
    @Override
    public LineChange copy() {
        return new LineChange(current, previous, index);
    }
}
