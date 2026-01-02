package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write {
    public static void write(MemorySet memorySet) {
        try {
            File file = new File("public/puzzles/" + memorySet.getFilePath());
            file.getParentFile().mkdirs();
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(JsonConverter.javaToJson(memorySet));
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
