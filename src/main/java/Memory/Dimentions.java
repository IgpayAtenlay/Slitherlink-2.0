package Memory;

import java.util.ArrayList;

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
    public String toString() {
        return "(" + xSize + ", " + ySize + ")";
    }
    public ArrayList<Coords> allSquareCoords() {
        ArrayList<Coords> coordsList = new ArrayList<>();
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                coordsList.add(new Coords(x, y));
            }
        }
        return coordsList;
    }
    public ArrayList<Coords> allPointCoords() {
        ArrayList<Coords> coordsList = new ArrayList<>();
        for (int y = 0; y < ySize + 1; y++) {
            for (int x = 0; x < xSize + 1; x++) {
                coordsList.add(new Coords(x, y));
            }
        }
        return coordsList;
    }
}
