package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonFileToMemory {
    public static MemorySet read(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            ArrayList<String> json = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                json.add(data);
            }
            scanner.close();
            return JsonConverter.jsonToMemorySet(json, filePath);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
