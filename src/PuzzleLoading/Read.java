package PuzzleLoading;

import Memory.MemorySet;
import Util.JsonConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read {
    public static MemorySet read(String filePath) {
        try {
            File myObj = new File("public/puzzles/" + filePath);
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> json = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                json.add(data);
                System.out.println(data);
            }
            myReader.close();
            return JsonConverter.jsonToMemorySet(json, filePath);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
