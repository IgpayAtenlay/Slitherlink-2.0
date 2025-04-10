package SolvingActions;

import Enums.CardinalDirection;
import Enums.Highlight;
import Memory.Coords;
import Memory.Memory;

import java.util.ArrayDeque;

public class Trapped {
    static public void run(Memory memory) {
        System.out.println("starting " + Trapped.class.getSimpleName());
        int startingChanges = memory.getChanges().size();

        for (int x = 0; x < memory.getDimentions().xSize; x++) {
            for (int y = 0; y < memory.getDimentions().ySize; y++) {
                Coords coords = new Coords(x, y);
                if (memory.getHighlight(coords) == Highlight.EMPTY) {
                    checkTrapped(memory, coords);
                }
            }
        }

        System.out.println(Trapped.class.getSimpleName() + " finished");
        System.out.println("changes: " + (memory.getChanges().size() - startingChanges));
    }

    public static void checkTrapped(Memory memory, Coords emptyCoord) {
        for (CardinalDirection cardinalDirection : CardinalDirection.values()) {
            // two of same highlight with empty inbetween
            Coords startLocation = emptyCoord.addDirection(cardinalDirection);
            Coords endLocation = emptyCoord.addDirection(cardinalDirection.getOpposite());
            if (memory.getHighlight(startLocation) == memory.getHighlight(endLocation)) {
                System.out.println(emptyCoord + " surrounded by highlights");
                Highlight highlight = memory.getHighlight(startLocation);
                // see if they are part of the same group
                boolean[][] visitied = new boolean[memory.getDimentions().xSize + 2][memory.getDimentions().ySize + 2];
                ArrayDeque<Coords> queue = new ArrayDeque<>();
                queue.add(startLocation);
                boolean sameGroup = false;
                while (queue.size() > 0 && !sameGroup) {
                    Coords currentLocation = queue.remove();
                    for (CardinalDirection direction : CardinalDirection.values()) {
                        Coords newLocation = currentLocation.addDirection(direction);
                        if (
                                newLocation.x >= -1 && newLocation.x <= memory.getDimentions().xSize &&
                                newLocation.y >= -1 && newLocation.y <= memory.getDimentions().ySize &&
                                !visitied[newLocation.x + 1][newLocation.y + 1]
                        ) {
                            if (newLocation.equals(endLocation)) {
                                sameGroup = true;
                                break;
                            }
                            if (highlight == memory.getHighlight(newLocation)) {
                                queue.add(newLocation);
                                visitied[newLocation.x + 1][newLocation.y + 1] = true;
                            }
                        }
                    }
                }
                System.out.println(sameGroup);
                // see if there is an oposite highlight
                if (sameGroup) {
                    boolean highlightOneFound = false;
                    boolean highlightTwoFound = false;

                    queue = new ArrayDeque<>();
                    queue.add(emptyCoord.addDirection(cardinalDirection.getClockwise()));
                    while (queue.size() > 0 && !highlightOneFound) {
                        Coords currentLocation = queue.remove();
                        for (CardinalDirection direction : CardinalDirection.values()) {
                            Coords newLocation = currentLocation.addDirection(direction);
                            if (
                                    newLocation.x >= -1 && newLocation.x <= memory.getDimentions().xSize &&
                                    newLocation.y >= -1 && newLocation.y <= memory.getDimentions().ySize &&
                                    !visitied[newLocation.x + 1][newLocation.y + 1]
                            ) {
                                if (highlight.getOpposite() == memory.getHighlight(newLocation)) {
                                    highlightOneFound = true;
                                    break;
                                }
                                if (Highlight.EMPTY == memory.getHighlight(newLocation)) {
                                    queue.add(newLocation);
                                    visitied[newLocation.x + 1][newLocation.y + 1] = true;
                                }
                            }
                        }
                    }
                    queue = new ArrayDeque<>();
                    queue.add(emptyCoord.addDirection(cardinalDirection.getCounterClockwise()));
                    while (queue.size() > 0 && !highlightTwoFound) {
                        Coords currentLocation = queue.remove();
                        for (CardinalDirection direction : CardinalDirection.values()) {
                            Coords newLocation = currentLocation.addDirection(direction);
                            if (
                                    newLocation.x >= -1 && newLocation.x <= memory.getDimentions().xSize &&
                                            newLocation.y >= -1 && newLocation.y <= memory.getDimentions().ySize &&
                                            !visitied[newLocation.x + 1][newLocation.y + 1]
                            ) {
                                if (highlight.getOpposite() == memory.getHighlight(newLocation)) {
                                    highlightTwoFound = true;
                                    break;
                                }
                                if (Highlight.EMPTY == memory.getHighlight(newLocation)) {
                                    queue.add(newLocation);
                                    visitied[newLocation.x + 1][newLocation.y + 1] = true;
                                }
                            }
                        }
                    }

                    if (highlightOneFound && highlightTwoFound) {
                        memory.change(memory.setHighlight(highlight.getOpposite(), emptyCoord, false));
                    }
                }
            }
        }
    }
}
