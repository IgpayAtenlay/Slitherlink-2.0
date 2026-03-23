package Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EditAllPuzzles {
    public static void convert() {
        Path start = Paths.get("public/puzzles");
        try {
            Files.walk(start)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".json"))
                    .forEach(path -> convert(path));
        } catch (IOException ignored) {
        }
    }
    public static void convert(Path path) {
        try {
            String content = Files.readString(path);

            content = content.replace("diagonals", "corners");

            Files.writeString(path, content);
        } catch (Exception ignored) {
        }
    }
}
