package SolvingActions;

import Enums.Number;
import Enums.*;
import Memory.Coords;
import Memory.Memory;

public class SingleBlock {
    
    public static void run(Memory memory) {
//        System.out.println("starting " + SingleBlock.class.getSimpleName());
        
        int startingChanges = memory.getNumChanges();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                fillSidesUsingNumbers(memory, coords);
                fillCorners(memory, coords);
                switch (memory.getNumber(coords)) {
                    case EMPTY -> {
                        addLogicZero(memory, coords);
                        addLogicOne(memory, coords);
                        addLogicTwo(memory, coords);
                        addLogicThree(memory, coords);
                    }
                    case ONE -> {
                        populateCornersOne(memory, coords);
                        useCornerOne(memory, coords);
                    }
                    case TWO -> {
                        populateCornersTwo(memory, coords);
                        useCornerTwo(memory, coords);
                    }
                    case THREE -> {
                        populateCornerThree(memory, coords);
                        useCornerThree(memory, coords);
                    }
                }
            }
        }

//        System.out.println(SingleBlock.class.getSimpleName() + " finished");
//        System.out.println("changes: " + (memory.getNumChanges() - startingChanges));
    }

    public static void populateCornersOne(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            memory.setCorner(true, Corner.AT_MOST_ONE, coords, direction, false);
        }
    }
    public static void populateCornersTwo(Memory memory, Coords coords) {
        for (CardinalDirection direction : CardinalDirection.values()) {
            switch (memory.getLine(true, coords, direction)) {
                case X -> {
                    memory.setCorner(true, Corner.AT_LEAST_ONE, coords, direction.getOpposite().getDiagonalDirections()[0], false);
                    memory.setCorner(true, Corner.AT_LEAST_ONE, coords, direction.getOpposite().getDiagonalDirections()[1], false);
                }
                case LINE -> {
                    memory.setCorner(true, Corner.AT_MOST_ONE, coords, direction.getOpposite().getDiagonalDirections()[0], false);
                    memory.setCorner(true, Corner.AT_MOST_ONE, coords, direction.getOpposite().getDiagonalDirections()[1], false);
                }
            }
        }
    }
    public static void populateCornerThree(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            memory.setCorner(true, Corner.AT_LEAST_ONE, coords, direction, false);
        }
    }
    public static void fillSidesUsingNumbers(Memory memory, Coords coords) {
        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, coords, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLine(true, coords, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == memory.getNumber(coords).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.setLine(true, Line.X, coords, direction, false);
                memory.setLine(true, Line.X, coords, direction, false);
            }
        } else if (xs == 4 - memory.getNumber(coords).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.setLine(true, Line.LINE, coords, direction, false);
            }
        }
    }
    public static void fillCorners(Memory memory, Coords coords) {
        int numExactOne = 0;
        int numBoth = 0;
        int numAtLeast = 0;
        int numAtMost = 0;
        int numEmpty = 0;
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Corner corner = memory.getCorner(true, coords, direction);
            switch (corner) {
                case EXACTLY_ONE -> numExactOne++;
                case BOTH_OR_NEITHER -> numBoth++;
                case AT_LEAST_ONE -> numAtLeast++;
                case AT_MOST_ONE -> numAtMost++;
                case EMPTY -> numEmpty++;
            }
        }

        if (numBoth + numExactOne == 3) {
            if (numBoth % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    memory.setCorner(true, Corner.BOTH_OR_NEITHER, coords, direction, false);
                }
            } else if (numExactOne % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction, false);
                }
            }
        } else if (numBoth == 2 && numAtLeast >= 1) {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction, false);
            }
        }
    }
    public static void useCornerOne(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getCorner(true, coords, direction)) {
                case EXACTLY_ONE, AT_LEAST_ONE -> {
                    memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0].getOpposite(), false);
                    memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1].getOpposite(), false);
                    memory.setCorner(true, Corner.BOTH_OR_NEITHER, coords, direction.getOpposite(), false);
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction, false);
                }
                case BOTH_OR_NEITHER -> {
                    memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0], false);
                    memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1], false);
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction.getOpposite(), false);
                }
            }
        }
    }
    public static void useCornerTwo(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getCorner(true, coords, direction)) {
                case EXACTLY_ONE -> {
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction.getOpposite(), false);
                }
                case BOTH_OR_NEITHER -> {
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction.getClockwise(), false);
                    memory.setCorner(true, Corner.BOTH_OR_NEITHER, coords, direction.getOpposite(), false);
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction.getCounterClockwise(), false);
                }
                case AT_LEAST_ONE -> {
                    memory.setCorner(true, Corner.AT_MOST_ONE, coords, direction.getOpposite(), false);
                }
                case AT_MOST_ONE -> {
                    memory.setCorner(true, Corner.AT_LEAST_ONE, coords, direction.getOpposite(), false);
                }
            }
        }
    }
    public static void useCornerThree(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getCorner(true, coords, direction)) {
                case EXACTLY_ONE, AT_MOST_ONE -> {
                    memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0].getOpposite(), false);
                    memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1].getOpposite(), false);
                    memory.setCorner(true, Corner.BOTH_OR_NEITHER, coords, direction.getOpposite(), false);
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction, false);
                }
                case BOTH_OR_NEITHER -> {
                    memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0], false);
                    memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1], false);
                    memory.setCorner(true, Corner.EXACTLY_ONE, coords, direction.getOpposite(), false);
                }
            }
        }
    }
    public static void addLogicZero(Memory memory, Coords coords) {
        if (memory.getCorner(true, coords, DiagonalDirection.NORTHEAST) == Corner.BOTH_OR_NEITHER &&
                memory.getCorner(true, coords, DiagonalDirection.SOUTHEAST) == Corner.BOTH_OR_NEITHER &&
                memory.getCorner(true, coords, DiagonalDirection.SOUTHWEST) == Corner.BOTH_OR_NEITHER &&
                memory.getCorner(true, coords, DiagonalDirection.NORTHWEST) == Corner.BOTH_OR_NEITHER
        ) {
            memory.setNumber(Number.ZERO, coords, false);
        }
    }
    public static void addLogicOne(Memory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getCorner(true, coords, direction) == Corner.EXACTLY_ONE &&
                    memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.X &&
                    memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.X
            ) {
                memory.setNumber(Number.ONE, coords, false);
            }
        }
    }
    public static void addLogicTwo(Memory memory, Coords coords) {
        for (DiagonalDirection direction : new DiagonalDirection[]{DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST}) {
            if (memory.getCorner(true, coords, direction) == Corner.EXACTLY_ONE &&
                    memory.getCorner(true, coords, direction.getOpposite()) == Corner.EXACTLY_ONE
            ) {
                memory.setNumber(Number.TWO, coords, false);
            }
        }
    }
    public static void addLogicThree(Memory memory, Coords coords) {
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, coords, direction) == Line.LINE) {
                lines++;
            }
        }

        if (lines == 3) {
            memory.setNumber(Number.THREE, coords, false);
        } else {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                if (memory.getCorner(true, coords, direction) == Corner.EXACTLY_ONE &&
                        memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.LINE &&
                        memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.LINE
                ) {
                    memory.setNumber(Number.THREE, coords, false);
                }
            }
        }
    }
}
