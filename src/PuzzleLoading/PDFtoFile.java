package PuzzleLoading;

import Memory.MemorySet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFtoFile {
    public static void write(String filename) {
        try {
            Pattern difficultyPattern = Pattern.compile("_d\\d_");
            Matcher difficultyMatcher = difficultyPattern.matcher(filename);
            if (!difficultyMatcher.find()) return;
            int difficultyNum = Integer.parseInt(difficultyMatcher.group().substring(2, 3));
            String difficulty = switch (difficultyNum) {
                case 0 -> "easy";
                case 1 -> "intermediate";
                case 2 -> "tough";
                default -> "unknown";
            };

            Pattern sizePattern = Pattern.compile("\\d\\dx\\d\\d");
            Matcher sizeMatcher = sizePattern.matcher(filename);
            if (!sizeMatcher.find()) return;
            int size = Integer.parseInt(sizeMatcher.group().substring(0, 2));

            Pattern bookPattern = Pattern.compile("_b\\d\\d\\d.pdf");
            Matcher bookMatcher = bookPattern.matcher(filename);
            if (!bookMatcher.find()) return;
            int book = Integer.parseInt(bookMatcher.group().substring(2, 5));

            String basePath = String.format("importedPuzzles/%s/%dx%d/book%d/", difficulty, size, size, book);

            File file = new File(String.format("%s#%d.json", basePath, 1));

            if (!file.exists()) {
                List<TextData> rawPuzzles = ExtractDataToVariable.extract(filename);
                if (rawPuzzles != null) {
                    ArrayList<ArrayList<TextData>> puzzles = ParseData.splitLists(rawPuzzles);
                    for (int i = 0; i < puzzles.size() / 2; i++) {
                        ArrayList<TextData> puzzle = puzzles.get(i);
                        String filePath = String.format("%s#%d.json", basePath, i + 1);
                        MemorySet memory = new MemorySet(ParseData.parsePuzzle(puzzle), filePath);
                        Write.write(memory);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
