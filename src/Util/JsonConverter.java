package Util;

import Enums.Corner;
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
                "\n    \"start\": " + memorySet.getStart().getJson().replace("\n", "\n    ") + "," +
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
        Memory visible = jsonToMemory("visible", json);
        Memory calculation = jsonToMemory("calculation", json);
        Memory start = jsonToMemory("start", json);

        if (visible == null && start != null) {
            visible = start.copy();
        }
        if (start == null && visible != null) {
            start = visible.copy();
        }
        if (calculation == null && start != null) {
            calculation = start.copy();
        }

        return new MemorySet(visible, calculation, start, filePath);
    }
    public static Memory jsonToMemory(String target, ArrayList<String> json) {
        int leadingSpaces = -2;
        int startingIndex = -1;
        int endingIndex = -1;

        for (int i = 0; i < json.size(); i++) {
            String string = json.get(i);
            if (string.contains("\"" + target + "\":")) {
                leadingSpaces = string.indexOf("\"" + target + "\":");
                startingIndex = i;
            } else if (string.indexOf("}") == leadingSpaces) {
                endingIndex = i;
                break;
            }
        }

        if (startingIndex != -1 && endingIndex != -1) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = startingIndex + 1; i < endingIndex; i++) {
                list.add(json.get(i));
            }
            return jsonToMemory(list);
        }
        return null;
    }
    public static Memory jsonToMemory(ArrayList<String> json) {
        int xSize = 0;
        int ySize = 0;
        Line[] lines = new Line[0];
        Number[] numbers = new Number[0];
        Highlight[] highlights = new Highlight[0];
        Corner[] corners = new Corner[0];
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
            text = "\"corners\": ";
            if (string.contains(text)) {
                String result = string.substring(string.indexOf(text) + text.length(), string.length() - 1);
                corners = (Corner[]) jsonToArray("corners", result);
            }
        }
        return new Memory(new Dimentions(xSize, ySize), lines, numbers, highlights, corners);
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
            case "corners": {
                Corner[] corners = new Corner[split.length];
                for (int i = 0; i < split.length; i++) {
                    corners[i] = Corner.valueOf(split[i]);
                }
                return corners;
            }
            case "loops": {

            }
            default: return null;
        }
    }
}
