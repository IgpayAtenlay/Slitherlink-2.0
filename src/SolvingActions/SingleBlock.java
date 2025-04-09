package SolvingActions;

import Enums.Number;
import Enums.*;
import Memory.Coords;
import Memory.FullMemory;

public class SingleBlock {
    
    public static void run(FullMemory memory) {
        System.out.println("starting " + SingleBlock.class.getSimpleName());
        
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                fillSidesUsingNumbers(memory, coords);
                fillDiagonals(memory, coords);
                switch (memory.getNumbers().get(coords)) {
                    case EMPTY -> {
                        addLogicZero(memory, coords);
                        addLogicOne(memory, coords);
                        addLogicTwo(memory, coords);
                        addLogicThree(memory, coords);
                    }
                    case ONE -> {
                        populateDiagonalsOne(memory, coords);
                        useDiagonalOne(memory, coords);
                    }
                    case TWO -> useDiagonalTwo(memory, coords);
                    case THREE -> {
                        populateDiagonalsThree(memory, coords);
                        useDiagonalThree(memory, coords);
                    }
                }
            }
        }

        System.out.println(SingleBlock.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void populateDiagonalsOne(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            memory.change(memory.getDiagonals().setSquare(Diagonal.AT_MOST_ONE, coords, direction, false));
        }
    }
    public static void populateDiagonalsThree(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            memory.change(memory.getDiagonals().setSquare(Diagonal.AT_LEAST_ONE, coords, direction, false));
        }
    }
    public static void fillSidesUsingNumbers(FullMemory memory, Coords coords) {
        int xs = 0;
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, coords, direction) == Line.LINE) {
                lines += 1;
            } else if (memory.getLine(true, coords, direction) == Line.X) {
                xs += 1;
            }
        }

        if (lines == memory.getNumbers().get(coords).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.setLine(true, Line.X, coords, direction, false));
                memory.change(memory.setLine(true, Line.X, coords, direction, false));
            }
        } else if (xs == 4 - memory.getNumbers().get(coords).value) {
            for (CardinalDirection direction : CardinalDirection.values()) {
                memory.change(memory.setLine(true, Line.LINE, coords, direction, false));
            }
        }
    }
    public static void fillDiagonals(FullMemory memory, Coords coords) {
        int numExactOne = 0;
        int numBoth = 0;
        int numAtLeast = 0;
        int numAtMost = 0;
        int numEmpty = 0;
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            Diagonal diagonal = memory.getDiagonals().getSquare(coords, direction);
            switch (diagonal) {
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
                    memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, coords, direction, false));
                }
            } else if (numExactOne % 2 == 1) {
                for (DiagonalDirection direction : DiagonalDirection.values()) {
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction, false));
                }
            }
        } else if (numBoth == 2 && numAtLeast >= 1) {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction, false));
            }
        }
    }
    public static void useDiagonalOne(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getDiagonals().getSquare(coords, direction)) {
                case EXACTLY_ONE, AT_LEAST_ONE -> {
                    memory.change(memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0].getOpposite(), false));
                    memory.change(memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1].getOpposite(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, coords, direction.getOpposite(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction, false));
                }
                case BOTH_OR_NEITHER -> {
                    memory.change(memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[0], false));
                    memory.change(memory.setLine(true, Line.X, coords, direction.getCardinalDirections()[1], false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction.getOpposite(), false));
                }
            }
        }
    }
    public static void useDiagonalTwo(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getDiagonals().getSquare(coords, direction)) {
                case EXACTLY_ONE -> {
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction.getOpposite(), false));
                }
                case BOTH_OR_NEITHER -> {
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction.getAdjacent(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, coords, direction.getOpposite(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction.getAdjacent().getOpposite(), false));
                }
            }
        }
    }
    public static void useDiagonalThree(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            switch (memory.getDiagonals().getSquare(coords, direction)) {
                case EXACTLY_ONE, AT_MOST_ONE -> {
                    memory.change(memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0].getOpposite(), false));
                    memory.change(memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1].getOpposite(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.BOTH_OR_NEITHER, coords, direction.getOpposite(), false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction, false));
                }
                case BOTH_OR_NEITHER -> {
                    memory.change(memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[0], false));
                    memory.change(memory.setLine(true, Line.LINE, coords, direction.getCardinalDirections()[1], false));
                    memory.change(memory.getDiagonals().setSquare(Diagonal.EXACTLY_ONE, coords, direction.getOpposite(), false));
                }
            }
        }
    }
    public static void addLogicZero(FullMemory memory, Coords coords) {
        if (memory.getDiagonals().getSquare(coords, DiagonalDirection.NORTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(coords, DiagonalDirection.SOUTHEAST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(coords, DiagonalDirection.SOUTHWEST) == Diagonal.BOTH_OR_NEITHER &&
                memory.getDiagonals().getSquare(coords, DiagonalDirection.NORTHWEST) == Diagonal.BOTH_OR_NEITHER
        ) {
            memory.change(memory.getNumbers().set(Number.ZERO, coords, false)) ;
        }
    }
    public static void addLogicOne(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : DiagonalDirection.values()) {
            if (memory.getDiagonals().getSquare(coords, direction) == Diagonal.EXACTLY_ONE &&
                    memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.X &&
                    memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.X
            ) {
                memory.change(memory.getNumbers().set(Number.ONE, coords, false));
            }
        }
    }
    public static void addLogicTwo(FullMemory memory, Coords coords) {
        for (DiagonalDirection direction : new DiagonalDirection[]{DiagonalDirection.NORTHEAST, DiagonalDirection.SOUTHEAST}) {
            if (memory.getDiagonals().getSquare(coords, direction) == Diagonal.EXACTLY_ONE &&
                    memory.getDiagonals().getSquare(coords, direction.getOpposite()) == Diagonal.EXACTLY_ONE
            ) {
                memory.change(memory.getNumbers().set(Number.TWO, coords, false));
            }
        }
    }
    public static void addLogicThree(FullMemory memory, Coords coords) {
        int lines = 0;
        for (CardinalDirection direction : CardinalDirection.values()) {
            if (memory.getLine(true, coords, direction) == Line.LINE) {
                lines++;
            }
        }

        if (lines == 3) {
            memory.change(memory.getNumbers().set(Number.THREE, coords, false));
        } else {
            for (DiagonalDirection direction : DiagonalDirection.values()) {
                if (memory.getDiagonals().getSquare(coords, direction) == Diagonal.EXACTLY_ONE &&
                        memory.getLine(true, coords, direction.getCardinalDirections()[0].getOpposite()) == Line.LINE &&
                        memory.getLine(true, coords, direction.getCardinalDirections()[1].getOpposite()) == Line.LINE
                ) {
                    memory.change(memory.getNumbers().set(Number.THREE, coords, false));
                }
            }
        }
    }
}
