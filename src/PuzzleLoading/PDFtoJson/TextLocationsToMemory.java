package PuzzleLoading.PDFtoJson;

import Enums.Number;
import Memory.Coords;
import Memory.Dimentions;
import Memory.Memory;

import java.util.*;

public class TextLocationsToMemory {
    public static Memory convert(ArrayList<TextLocation> textLocations) {
        PriorityQueue<Float> xValues = new PriorityQueue<>();
        PriorityQueue<Float> yValues = new PriorityQueue<>();

        for(TextLocation data : textLocations) {
            xValues.add(data.x());
            yValues.add(data.y());
        }

        double smallestX = xValues.remove();
        double smallestY = yValues.remove();

        double inbetweenSpace = Double.MAX_VALUE;
        double previousValue = smallestX;
        double currentValue;

        while (xValues.size() > 0) {
            currentValue = xValues.remove();
            inbetweenSpace = Math.min(inbetweenSpace, currentValue - previousValue);
            previousValue = currentValue;
        }

        double biggestX = previousValue;

        previousValue = smallestY;

        while (yValues.size() > 0) {
            currentValue = yValues.remove();
            inbetweenSpace = Math.min(inbetweenSpace, currentValue - previousValue);
            previousValue = currentValue;
        }

        double biggestY = previousValue;

        Dimentions dimentions = new Dimentions(
                (int) ((biggestX - smallestX) / inbetweenSpace) + 1,
                (int) ((biggestY - smallestY) / inbetweenSpace) + 1
        );

        Memory memory = new Memory(dimentions);
        for(TextLocation data : textLocations) {
            Number number = Number.getNumber(Integer.parseInt(data.text()));
            Coords coords = new Coords(
                    (int) (data.x() / inbetweenSpace - smallestX / inbetweenSpace),
                    (int) (data.y() / inbetweenSpace - smallestY / inbetweenSpace)
            );
            memory.setNumber(number, coords, true);
        }

        return memory;
    }
}
