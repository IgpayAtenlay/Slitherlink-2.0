package Actions;

import Enums.Number;
import Enums.*;
import Memory.FullMemory;

public class SingleBlock {
    
    public static void run(FullMemory memory) {
        System.out.println("starting " + SingleBlock.class.getSimpleName());
        
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getXSize(); x++) {
            for (int y = 0; y < memory.getYSize(); y++) {
                fillSidesUsingNumbers(memory, x, y);
                fillDiagonals(memory, x, y);
                switch (memory.getNumbers().get(x, y)) {
                    case EMPTY -> {
                        addLogicZero(memory, x, y);
                        addLogicOne(memory, x, y);
                        addLogicTwo(memory, x, y);
                        addLogicThree(memory, x, y);
                    }
                    case ONE -> useDiagonalOne(memory, x, y);
                    case TWO -> useDiagonalTwo(memory, x, y);
                    case THREE -> useDiagonalThree(memory, x, y);
                }
            }
        }

        System.out.println(SingleBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void fillSidesUsingNumbers(FullMemory memory, int x, int y) {
        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLines().getSquare(x, y, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == memory.getNumbers().get(x, y).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction, false));
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction, false));
            }
        } else if (xs == 4 - memory.getNumbers().get(x, y).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction, false));
            }
        }
    }
    public static void fillDiagonals(FullMemory memory, int x, int y) {
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
                    memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction, false));
                }
            } else if (numEither % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction, false));
                }
            }
        }
    }
    public static void useDiagonalOne(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[0].getOpposite(), false));
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[1].getOpposite(), false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false));
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[0], false));
                memory.change(memory.getLines().setSquare(Line.X, x, y, direction.getCardinalDirections()[1], false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false));
            }
        }
    }
    public static void useDiagonalTwo(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false));
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getAdjacent(), false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getAdjacent().getOpposite(), false));
            }
        }
    }
    public static void useDiagonalThree(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR) {
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[0].getOpposite(), false));
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[1].getOpposite(), false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, x, y, direction.getOpposite(), false));
            } else if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.BOTH_OR_NEITHER) {
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[0], false));
                memory.change(memory.getLines().setSquare(Line.LINE, x, y, direction.getCardinalDirections()[1], false));
                memory.change(memory.getDiagonals().setSquare(Diagonal.EITHER_OR, x, y, direction.getOpposite(), false));
            }
        }
    }
    public static void addLogicZero(FullMemory memory, int x, int y) {
        if (memory.getDiagonals().getSquare(x, y, DiagonalDirection.NORTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.SOUTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.SOUTHWEST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(x, y, DiagonalDirection.NORTHWEST) == Diagonal.BOTH_OR_NEITHER
        ) {
            memory.change(memory.getNumbers().set(Number.ZERO, x, y, false)) ;
        }
    }
    public static void addLogicOne(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                    memory.getLines().getSquare(x, y, direction.getCardinalDirections()[0].getOpposite()) == Line.X &&
                    memory.getLines().getSquare(x, y, direction.getCardinalDirections()[1].getOpposite()) == Line.X
            ) {
                memory.change(memory.getNumbers().set(Number.ONE, x, y, false));
            }
        }
    }
    public static void addLogicTwo(FullMemory memory, int x, int y) {
        for (DiagonalDirection direction : new DiagonalDirection[]{DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST}) {
            if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                    memory.getDiagonals().getSquare(x, y, direction.getOpposite()) == Diagonal.EITHER_OR
            ) {
                memory.change(memory.getNumbers().set(Number.TWO, x, y, false));
            }
        }
    }
    public static void addLogicThree(FullMemory memory, int x, int y) {
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLines().getSquare(x, y, direction) == Line.LINE) {
                lines++;
            }
        }

        if (lines == 3) {
            memory.change(memory.getNumbers().set(Number.THREE, x, y, false));
        } else {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                if (memory.getDiagonals().getSquare(x, y, direction) == Diagonal.EITHER_OR &&
                        memory.getLines().getSquare(x, y, direction.getCardinalDirections()[0].getOpposite()) == Line.LINE &&
                        memory.getLines().getSquare(x, y, direction.getCardinalDirections()[1].getOpposite()) == Line.LINE
                ) {
                    memory.change(memory.getNumbers().set(Number.THREE, x, y, false));
                }
            }
        }
    }
}
