package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write {
    public static void write(MemorySet memorySet, String pathName) {
        try {
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(JsonConverter.javaToJson(memorySet));
            myWriter.close();
            System.out.println("done");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void write(MemorySet memorySet) {
        write(memorySet, "public/puzzles/" + memorySet.getFilePath());
    }
}
