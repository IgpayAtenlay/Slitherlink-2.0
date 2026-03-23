package PuzzleLoading.PDFtoJson;

import java.util.ArrayList;
import java.util.List;

public class SeparatePuzzleLists {
    public static ArrayList<ArrayList<TextLocation>> separate(List<TextLocation> data) {
        ArrayList<ArrayList<TextLocation>> puzzles = new ArrayList<>();
        int currentPuzzleStart = 0;

        boolean puzzleStarted = false;
        for (int i = 0; i < data.size(); i++) {
            if (!puzzleStarted && "0123".contains(data.get(i).text())) {
                puzzleStarted = true;
                currentPuzzleStart = i;
                puzzles.add(new ArrayList<>());
            }
            if (puzzleStarted) {
                if ("0123".contains(data.get(i).text())) {
                    puzzles.get(puzzles.size() - 1).add(data.get(i));
                } else {
                    if (i - currentPuzzleStart < 5) {
                        puzzles.remove(puzzles.size() - 1);
                    }
                    puzzleStarted = false;
                }
            }
        }

        return puzzles;
    }
}
