package PuzzleLoading;

import Memory.MemorySet;

import java.util.ArrayList;
import java.util.List;

public class PDFtoFile {
    public static void write(String filename) {
//        only works for volume one
//        easy 7x7 Book 1 #123
        int difficultyNum = Integer.parseInt(filename.substring(4,5));
        String difficulty = switch (difficultyNum) {
            case 0 -> "easy";
            case 1 -> "intermediate";
            case 2 -> "tough";
            default -> "unknown";
        };

        int size = Integer.parseInt(filename.substring(6,8));
        int book = Integer.parseInt(filename.substring(13,16));

        List<TextData> rawPuzzles = ExtractDataToVariable.extract(filename);
        if (rawPuzzles != null) {
            ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(rawPuzzles);
            for (int i = 0; i < puzzles.size() / 2; i++) {
                ArrayList<TextData> puzzle = puzzles.get(i);
                String filePath = String.format("importedPuzzles/%s%dBy%dBook%d#%d.json", difficulty, size, size, book, i + 1);
                MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzle), filePath);
                Write.write(memory);
            }
        }
    }
}
