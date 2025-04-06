package Memory;

public class Dimentions {
    public final int xSize;
    public final int ySize;

    public Dimentions(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public Dimentions copy() {
        return new Dimentions(xSize, ySize);
    }
}
