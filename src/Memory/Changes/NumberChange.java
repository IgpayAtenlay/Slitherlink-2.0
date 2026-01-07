package Memory.Changes;

import Enums.Number;

public class NumberChange extends Changes {
    public final Number current;
    public final Number previous;

    public NumberChange(Number current, Number previous, int index) {
        super(index);
        this.current = current;
        this.previous = previous;
    }
    @Override
    public NumberChange copy() {
        return new NumberChange(current, previous, index);
    }
}
