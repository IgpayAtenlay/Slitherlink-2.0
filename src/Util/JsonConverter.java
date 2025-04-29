package Util;

import Enums.Line;
import Enums.Number;
import Memory.Loop;
import Memory.MemorySet;

import java.util.Arrays;

public class JsonConverter {
    public static String javaToJson(MemorySet memorySet) {
        return "{" +
                "\n    \"puzzleName\": \"" + memorySet.getPuzzleName() + "\"," +
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
    public static Object[] jsonToJava(String type, String array) {
        String[] split = array.split("\", \"");
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

            }
            case "diagonals": {

            }
            case "loops": {

            }
            default: return null;
        }
    }
}
