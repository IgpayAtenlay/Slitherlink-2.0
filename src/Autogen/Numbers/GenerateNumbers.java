package Autogen.Numbers;

import Enums.CardinalDirection;
import Enums.DiagonalDirection;
import Enums.Highlight;
import Memory.Coords;
import Memory.Memory;

import java.util.ArrayList;

public class GenerateNumbers {
    public static void generate(Memory puzzle) {
        Coords start = new Coords((int)(Math.random() * puzzle.getDimentions().xSize), (int)(Math.random() * puzzle.getDimentions().ySize));
        ArrayList<Coords> possibleDirections = new ArrayList<>();
        possibleDirections.add(start);
        while(possibleDirections.size() > 0) {
            int randomIndex = (int)(Math.random() * possibleDirections.size());
            Coords coordToTest = possibleDirections.get(randomIndex);
            possibleDirections.remove(randomIndex);

            int numOfInsides = 0;
            boolean goodSpot = true;
            for (CardinalDirection direction : CardinalDirection.values()) {
                if (puzzle.getHighlight(coordToTest.addDirection(direction)) == Highlight.INSIDE) {
                    numOfInsides++;
                }
            }
            if (numOfInsides > 1) {
                goodSpot = false;
            }
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                if (puzzle.getHighlight(coordToTest.addDirection(direction)) == Highlight.INSIDE) {
                    boolean isConnected = false;
                    for (CardinalDirection cardinalDirection : direction.getCardinalDirections()) {
                        if (puzzle.getHighlight(coordToTest.addDirection(cardinalDirection)) == Highlight.INSIDE) {
                            isConnected = true;
                        }
                    }
                    if (!isConnected) {
                        goodSpot = false;
                    }
                }
            }
            if (goodSpot) {
                puzzle.setHighlight(Highlight.INSIDE, coordToTest, true);
                for (CardinalDirection direction : CardinalDirection.values()) {
                    if (puzzle.getHighlight(coordToTest.addDirection(direction)) != Highlight.INSIDE) {
                        possibleDirections.add(start.addDirection(direction));
                    }
                }
            }
        }
    }
}
