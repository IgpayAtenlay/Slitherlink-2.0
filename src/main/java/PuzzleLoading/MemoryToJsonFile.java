package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MemoryToJsonFile {
    public static void write(MemorySet memorySet, String pathName) {
        try {
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write(JsonConverter.javaToJson(memorySet));
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void write(MemorySet memorySet) {
        write(memorySet, "public/puzzles/" + memorySet.getFilePath());
    }
}
