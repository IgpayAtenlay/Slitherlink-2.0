package SolvingActions.Obsolete;

import Memory.Coords;
import Memory.Memory;

public class SingleBlock {
    
    public static void run(Memory memory) {
        for (Coords coords : memory.getDimentions().allSquareCoords()) {
//            populateCornersOne(memory, coords);
//            populateCornersThree(memory, coords);

//            useCornerOne(memory, coords);
//            cornersAcrossTwo(memory, coords);
//            useCornerThree(memory, coords);

//            fillSidesUsingNumbers(memory, coords);
//            fillCornersOnSquare(memory, coords);
//            fourSameCornerToTotalXSquare(memory, coords);

//            addLogicZero(memory, coords);
//            addLogicOne(memory, coords);
//            addLogicTwo(memory, coords);
//            addLogicThree(memory, coords);
        }
    }

//    public static void populateCornersOne(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.ONE) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                memory.setCorner(true, Corner.NOT_TWO, coords, direction, false);
//            }
//        }
//    }
//    public static void populateCornersThree(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.THREE) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                memory.setCorner(true, Corner.NOT_ZERO, coords, direction, false);
//            }
//        }
//    }
//    public static void fillSidesUsingNumbers(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) != Number.EMPTY) {
//            int xs = 0;
//            int lines = 0;
//            for (CardinalDirection direction : CardinalDirection.values()) {
//                if (memory.getLine(true, coords, direction) == Line.LINE) {
//                    lines++;
//                } else if (memory.getLine(true, coords, direction) == Line.X) {
//                    xs++;
//                }
//            }
//
//            if (lines == memory.getNumber(coords).value) {
//                for (CardinalDirection direction : CardinalDirection.values()) {
//                    memory.setLine(true, Line.X, coords, direction, false);
//                }
//            } else if (xs == 4 - memory.getNumber(coords).value) {
//                for (CardinalDirection direction : CardinalDirection.values()) {
//                    memory.setLine(true, Line.LINE, coords, direction, false);
//                }
//            }
//        }
//    }
//    public static void fillCornersOnSquare(Memory memory, Coords coords) {
//        int one = 0;
//        int notOne = 0;
//        int notZero = 0;
//        for (DiagonalDirection direction : DiagonalDirection.values()) {
//            Corner corner = memory.getCorner(true, coords, direction);
//            switch (corner) {
//                case ONE -> one++;
//                case NOT_ONE -> notOne++;
//                case NOT_ZERO -> notZero++;
//            }
//        }
//
//        if (notOne + one == 3) {
//            if (notOne % 2 == 1) {
//                for (DiagonalDirection direction : DiagonalDirection.values()) {
//                    memory.(true, Corner.NOT_ONE, coords, direction, false);
//                }
//            } else if (one % 2 == 1) {
//                for (DiagonalDirection direction : DiagonalDirection.values()) {
//                    memory.setCorner(true, Corner.ONE, coords, direction, false);
//                }
//            }
//        } else if (notOne == 2 && notZero >= 1) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                memory.setCorner(true, Corner.ONE, coords, direction, false);
//            }
//        }
//    }
//    public static void useCornerOne(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.ONE) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                switch (memory.getCorner(true, coords, direction)) {
//                    case ONE, NOT_ZERO -> {
//                        memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0].getOpposite(), false);
//                        memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1].getOpposite(), false);
////                        memory.setCorner(true, Corner.ONE, coords, direction, false);
//                    }
//                    case NOT_ONE -> {
//                        memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0], false);
//                        memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1], false);
////                        memory.setCorner(true, Corner.ONE, coords, direction.getOpposite(), false);
//                    }
//                }
//            }
//        }
//    }
//    public static void cornersAcrossTwo(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.TWO) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                switch (memory.getCorner(true, coords, direction)) {
//                    case ONE -> {
//                        memory.setCorner(true, Corner.ONE, coords, direction.getOpposite(), false);
//                    }
//                    case NOT_ONE -> {
//                        memory.setCorner(true, Corner.ONE, coords, direction.getClockwise(), false);
//                        memory.setCorner(true, Corner.NOT_ONE, coords, direction.getOpposite(), false);
//                        memory.setCorner(true, Corner.ONE, coords, direction.getCounterClockwise(), false);
//                    }
//                    case NOT_ZERO -> {
//                        memory.setCorner(true, Corner.NOT_TWO, coords, direction.getOpposite(), false);
//                    }
//                    case NOT_TWO -> {
//                        memory.setCorner(true, Corner.NOT_ZERO, coords, direction.getOpposite(), false);
//                    }
//                }
//            }
//        }
//    }
//    public static void useCornerThree(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.THREE) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                switch (memory.getCorner(true, coords, direction)) {
//                    case ONE, NOT_TWO -> {
//                        memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0].getOpposite(), false);
//                        memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1].getOpposite(), false);
////                        memory.setCorner(true, Corner.NOT_ONE, coords, direction.getOpposite(), false);
////                        memory.setCorner(true, Corner.ONE, coords, direction, false);
//                    }
//                    case NOT_ONE -> {
//                        memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0], false);
//                        memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1], false);
////                        memory.setCorner(true, Corner.ONE, coords, direction.getOpposite(), false);
//                    }
//                }
//            }
//        }
//    }
//    public static void fourSameCornerToTotalXSquare(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.EMPTY) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                if (memory.getCorner(true, coords, direction) != Corner.NOT_ONE) {
//                    return;
//                }
//            }
//
//            for (CardinalDirection direction : CardinalDirection.values()) {
//                memory.setLine(true, Line.X, coords, direction, false);
//            }
//        }
//    }
//    public static void addLogicZero(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.EMPTY) {
//            for (CardinalDirection direction : CardinalDirection.values()) {
//                if (memory.getLine(true, coords, direction) != Line.X) return;
//            }
//            memory.setNumber(Number.ZERO, coords, false);
//        }
//    }
//    public static void addLogicOne(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.EMPTY) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                if (memory.getCorner(true, coords, direction) == Corner.ONE &&
//                        memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.X &&
//                        memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.X
//                ) {
//                    memory.setNumber(Number.ONE, coords, false);
//                    return;
//                }
//            }
//        }
//    }
//    public static void addLogicTwo(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.EMPTY) {
//            for (DiagonalDirection direction : new DiagonalDirection[]{DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST}) {
//                if (memory.getCorner(true, coords, direction) == Corner.ONE &&
//                        memory.getCorner(true, coords, direction.getOpposite()) == Corner.ONE
//                ) {
//                    memory.setNumber(Number.TWO, coords, false);
//                    return;
//                }
//            }
//        }
//    }
//    public static void addLogicThree(Memory memory, Coords coords) {
//        if (memory.getNumber(coords) == Number.EMPTY) {
//            for (DiagonalDirection direction : DiagonalDirection.values()) {
//                if (memory.getCorner(true, coords, direction) == Corner.ONE &&
//                        memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.LINE &&
//                        memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.LINE
//                ) {
//                    memory.setNumber(Number.THREE, coords, false);
//                    return;
//                }
//            }
//        }
//    }
}
