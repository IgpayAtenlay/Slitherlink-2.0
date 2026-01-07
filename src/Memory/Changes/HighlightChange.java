package Memory.Changes;

import Enums.Highlight;

public class HighlightChange extends Changes {
    public final Highlight current;
    public final Highlight previous;

    public HighlightChange(Highlight current, Highlight previous, int index) {
        super(index);
        this.current = current;
        this.previous = previous;
    }
    @Override
    public HighlightChange copy() {
        return new HighlightChange(current, previous, index);
    }
}
