package Actions;

import Enums.CardinalDirection;
import Enums.Diagonal;
import Enums.DiagonalDirection;
import Enums.Line;
import Enums.Number;
import Memory.FullMemory;

public class SingleBlock {
    static public int run(FullMemory memory) {
        System.out.println("starting " + SingleBlock.class.getSimpleName());

        int changes = 0;

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                changes += fillSidesUsingNumbers(memory, x, y);
                changes += fillDiagonals(memory, x, y);
                switch (memory.getLogicNumber(x, y)) {
                    case EMPTY -> {
                        changes += addLogicZero(memory, x, y);
                        changes += addLogicOne(memory, x, y);
                        changes += addLogicTwo(memory, x, y);
                        changes += addLogicThree(memory, x, y);
                    }
                    case ONE -> changes += useDiagonalOne(memory, x, y);
                    case TWO -> changes += useDiagonalTwo(memory, x, y);
                    case THREE -> changes += useDiagonalThree(memory, x, y);
                }
            }
        }

        System.out.println(SingleBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + changes);
        return changes;
    }

    static public int fillSidesUsingNumbers(FullMemory memory, int x, int y) {
        int changes = 0;

        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLines().getSquare(x, y, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == memory.getLogicNumber(x, y).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                changes += memory.getLines().setSquare(Line.X, x, y, direction, false) ? 1 : 0;
            }
        } else if (xs == 4 - memory.getLogicNumber(x, y).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction, false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int fillDiagonals(FullMemory memory, int x, int y) {
        int changes = 0;

        int numEither = 0;
        int numBoth = 0;
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getSquare(x, y, direction);
            if (diagonal == Diagonal.EITHER_OR) {
                numEither++;
            } else if (diagonal == Diagonal.BOTH_OR_NEITHER) {
                numBoth++;
            }
        }

        if (numBoth + numEither == 3) {
            if (numBoth % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    changes += memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction, false) ? 1 : 0;
                }
            } else if (numEither % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction, false) ? 1 : 0;
                }
            }
        }

        return changes;
    }
    public static int useDiagonalOne(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                changes += memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[0].getOpposite(), false) ? 1 : 0;
                changes += memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[1].getOpposite(), false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false) ? 1 : 0;
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                changes += memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[0], false) ? 1 : 0;
                changes += memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[1], false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int useDiagonalTwo(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false) ? 1 : 0;
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getAdjacent(), false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getAdjacent().getOpposite(), false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int useDiagonalThree(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[0].getOpposite(), false) ? 1 : 0;
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[1].getOpposite(), false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false) ? 1 : 0;
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[0], false) ? 1 : 0;
                changes += memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[1], false) ? 1 : 0;
                changes += memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int addLogicZero(FullMemory memory, int x, int y) {
        int changes = 0;

        if (memory.getDiagonals().getSquare(x, y, DiagonalDirection.NORTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.SOUTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.SOUTHWEST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.NORTHWEST) == Diagonal.BOTH_OR_NEITHER
        ) {
            changes += memory.setLogicNumber(Number.ZERO, x, y, false) ? 1 : 0 ;
        }

        return changes;
    }
    public static int addLogicOne(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                    memory.getLines().getSquare(x, y, direction.getCardinalDirections()[0].getOpposite()) == Line.X &&
                    memory.getLines().getSquare(x, y, direction.getCardinalDirections()[1].getOpposite()) == Line.X
            ) {
                changes += memory.setLogicNumber(Number.ONE, x, y, false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int addLogicTwo(FullMemory memory, int x, int y) {
        int changes = 0;

        for (DiagonalDirection direction : new DiagonalDirection[]{DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST}) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                    memory.getDiagonals().getSquare(x, y, direction.getOpposite()) == Diagonal.EITHER_OR
            ) {
                changes += memory.setLogicNumber(Number.TWO, x, y, false) ? 1 : 0;
            }
        }

        return changes;
    }
    public static int addLogicThree(FullMemory memory, int x, int y) {
        int changes = 0;

        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                lines++;
            }
        }

        if (lines == 3) {
            changes += memory.setLogicNumber(Number.THREE, x, y, false) ? 1 : 0;
        } else {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                        memory.getLines().getSquare(x, y, direction.getCardinalDirections()[0].getOpposite()) == Line.LINE &&
                        memory.getLines().getSquare(x, y, direction.getCardinalDirections()[1].getOpposite()) == Line.LINE
                ) {
                    changes += memory.setLogicNumber(Number.THREE, x, y, false) ? 1 : 0;
                }
            }
        }

        return changes;
    }
}
