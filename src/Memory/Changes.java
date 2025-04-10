package Memory;

public class Changes {
    public final int index;

    protected Changes(int index) {
        this.index = index;
    }
    public Changes copy() {
        return new Changes(index);
    }
}
