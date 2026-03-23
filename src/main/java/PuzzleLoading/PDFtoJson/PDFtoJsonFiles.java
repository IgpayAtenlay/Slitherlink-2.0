package PuzzleLoading.PDFtoJson;

import Memory.MemorySet;
import PuzzleLoading.FileToData;
import PuzzleLoading.MemoryToJsonFile;
import Util.PatternFinder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFtoJsonFiles {
    public static void write(String filename) {
        try {
            String difficulty = switch (PatternFinder.extractPartOfPattern(filename, "_d\\d_", 2, 2)) {
                case 0 -> "easy";
                case 1 -> "intermediate";
                case 2 -> "tough";
                default -> throw new RuntimeException();
            };
            int size = PatternFinder.extractPartOfPattern(filename, "\\d\\dx\\d\\d", 0, 1);
            int book = PatternFinder.extractPartOfPattern(filename, "_b\\d\\d\\d.pdf", 2, 4);

            String basePath = String.format("importedPuzzles/%s/%dx%d/book%d/", difficulty, size, size, book);
            File file = new File(String.format("%s#%d.json", basePath, 1));

            if (!file.exists()) {
                List<TextLocation> rawPuzzles = FileToData.extract(filename);

                if (rawPuzzles != null) {
                    ArrayList<ArrayList<TextLocation>> puzzleTextLocationLists = SeparatePuzzleLists.separate(rawPuzzles);

                    for (int i = 0; i < puzzleTextLocationLists.size() / 2; i++) {
                        ArrayList<TextLocation> puzzle = puzzleTextLocationLists.get(i);
                        String filePath = String.format("%s#%d.json", basePath, i + 1);
                        MemorySet memory = new MemorySet(TextLocationsToMemory.convert(puzzle), filePath);
                        MemoryToJsonFile.write(memory);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
