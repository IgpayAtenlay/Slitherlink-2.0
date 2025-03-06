package PuzzleLoading;

import Enums.Number;
import Memory.NumberMemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParseData {
    public static ArrayList<ArrayList<TextData>> splitLists(List<TextData> data) {
        ArrayList<ArrayList<TextData>> puzzles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            puzzles.add(new ArrayList<>());
        }

        for (int i = 0; i < data.size(); i++) {
            if (Objects.equals(data.get(i).text, "#")) {
                i++;
                int puzzleNum = Integer.parseInt(data.get(i).text);
                i++;
                if (Objects.equals(data.get(i).text, "©")) {
                    i += 18;
                    // puzzle starts here
                    while(!Objects.equals(data.get(i).text, "S")) {
                        puzzles.get(puzzleNum - 1).add(data.get(i));
                        i++;
                    }
                }
            }
        }

        return puzzles;
    }
    public static NumberMemory parsePuzzle(ArrayList<TextData> puzzleList) {
        NumberMemory memory = new NumberMemory(20, 20);

        for(TextData data : puzzleList) {
            float xLoc = data.x;
            float yLoc = data.y;
            Number number = Number.getNumber(Integer.parseInt(data.text));
            int xPos = (int) (xLoc / 23.3333 - 3);
            int yPos = (int) (yLoc / 23.3333 - 5.9);
            memory.set(number, xPos, yPos, true);
        }

        memory.lock();

        return memory;
    }
}
