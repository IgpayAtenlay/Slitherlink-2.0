package Memory;

public class Loop {
    public final Coords endPoint;
    public final int length;
    public Loop(Coords endPoint, int length) {
        this.endPoint = endPoint;
        this.length = length;
    }
    public Loop copy() {
        return new Loop(endPoint.copy(), length);
    }

    @Override
    public String toString() {
        return endPoint.toString() + " length: " + length;
    }
}
