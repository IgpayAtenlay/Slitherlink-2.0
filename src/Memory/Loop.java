package Memory;

public class Loop {
    public final Coords coords;
    public final int length;
    public Loop(Coords coords, int length) {
        this.coords = coords;
        this.length = length;
    }
    public Loop copy() {
        return new Loop(coords.copy(), length);
    }

    @Override
    public String toString() {
        return coords.toString() + " length: " + length;
    }
}
