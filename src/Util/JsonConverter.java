package Util;

import Enums.Diagonal;
import Enums.Highlight;
import Enums.Line;
import Enums.Number;
import Memory.Loop;
import Memory.Memory;
import Memory.Dimentions;
import Memory.MemorySet;

import java.util.ArrayList;
import java.util.Arrays;

public class JsonConverter {
    public static String javaToJson(MemorySet memorySet) {
        return "{" +
                "\n    \"visible\": " + memorySet.getVisible().getJson().replace("\n", "\n    ") + "," +
                "\n    \"calculation\": " + memorySet.getCalculation().getJson().replace("\n", "\n    ") +
                "\n}";
    }
    public static String javaToJson(Object[] a) {
        if (a instanceof Loop[]) {
            return "[]";
        } else {
            return Arrays.toString(a).replace(", ", "\", \"").replace("[", "[\"").replace("]", "\"]");
        }
    }
    public static MemorySet jsonToMemorySet(ArrayList<String> json, String filePath) {
        // grab puzzlename
        String puzzleName = null;
        int leadingSpaces = -2;
        int startingIndex = -1;
        int endingIndex = -1;
        for (int i = 0; i < json.size(); i++) {
            String string = json.get(i);
            if (string.contains("\"visible\":")) {
                leadingSpaces = string.indexOf("\"visible\":");
                startingIndex = i;
            } else if (string.indexOf("}") == leadingSpaces) {
                endingIndex = i;
                break;
            }
        }
        Memory visible = null;
        if (startingIndex != -1 && endingIndex != -1) {
            ArrayList<String> visibleList = new ArrayList<>();
            for (int i = startingIndex + 1; i < endingIndex; i++) {
                visibleList.add(json.get(i));
            }
            visible = jsonToMemory(visibleList);
        }
        leadingSpaces = -2;
        startingIndex = -1;
        endingIndex = -1;
        for (int i = 0; i < json.size(); i++) {
            String string = json.get(i);
            if (string.contains("\"visible\":")) {
                leadingSpaces = string.indexOf("\"visible\":");
                startingIndex = i;
            } else if (string.indexOf("}") == leadingSpaces) {
                endingIndex = i;
                break;
            }
        }
        Memory calculation = null;
        if (startingIndex != -1 && endingIndex != -1) {
            ArrayList<String> calculationList = new ArrayList<>();
            for (int i = startingIndex + 1; i < endingIndex; i++) {
                calculationList.add(json.get(i));
            }
            calculation = jsonToMemory(calculationList);
        }
        return new MemorySet(visible, calculation, filePath);
    }
    public static Memory jsonToMemory(ArrayList<String> json) {
        int xSize = 0;
        int ySize = 0;
        Line[] lines = new Line[0];
        Number[] numbers = new Number[0];
        Highlight[] highlights = new Highlight[0];
        Diagonal[] diagonals = new Diagonal[0];
        Loop[] loops;
        for (String string : json) {
            String text = "\"xSize\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                xSize = Integer.parseInt(result);
            }
            text = "\"ySize\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                ySize = Integer.parseInt(result);
            }
            text = "\"lines\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                lines = (Line[]) jsonToArray("lines", result);
            }
            text = "\"numbers\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                numbers = (Number[]) jsonToArray("numbers", result);
            }
            text = "\"highlights\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                highlights = (Highlight[]) jsonToArray("highlights", result);
            }
            text = "\"diagonals\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                diagonals = (Diagonal[]) jsonToArray("diagonals", result);
            }
        }
        return new Memory(new Dimentions(xSize, ySize), lines, numbers, highlights, diagonals);
    }
    public static Object[] jsonToArray(String type, String json) {
        String[] split = json.split("\", \"");
        split[0] = split[0].replace("[\"", "");
        split[split.length - 1] = split[split.length - 1].replace("\"]", "");

        switch (type) {
            case "lines": {
                Line[] lines = new Line[split.length];
                for (int i = 0; i < split.length; i++) {
                    lines[i] = Line.valueOf(split[i]);
                }
                return lines;
            }
            case "numbers": {
                Number[] numbers = new Number[split.length];
                for (int i = 0; i < split.length; i++) {
                    numbers[i] = Number.valueOf(split[i]);
                }
                return numbers;
            }
            case "highlights": {
                Highlight[] highlights = new Highlight[split.length];
                for (int i = 0; i < split.length; i++) {
                    highlights[i] = Highlight.valueOf(split[i]);
                }
                return highlights;
            }
            case "diagonals": {
                Diagonal[] diagonals = new Diagonal[split.length];
                for (int i = 0; i < split.length; i++) {
                    diagonals[i] = Diagonal.valueOf(split[i]);
                }
                return diagonals;
            }
            case "loops": {

            }
            default: return null;
        }
    }
}
