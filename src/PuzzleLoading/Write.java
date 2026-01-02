package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.FileWriter;
import java.io.IOException;

public class Write {
    public static void write(MemorySet memorySet) {
        try {
            FileWriter myWriter = new FileWriter("public/puzzles/" + memorySet.getFilePath());
            myWriter.write(JsonConverter.javaToJson(memorySet));
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
