package Memory.Changes;

public abstract class Changes {
    public final int index;

    protected Changes(int index) {
        this.index = index;
    }
    public abstract Changes copy();
}
