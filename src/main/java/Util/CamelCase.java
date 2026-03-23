package Util;

public class CamelCase {
    public static String convertToCamelCase(String string) {
        char[] convert = string.toCharArray();
        if (string.length() > 0) {
            convert[0] = Character.toLowerCase(convert[0]);
        }
        for(int i = 0; i < string.length() - 1; i++) {
            if (convert[i] == ' ') {
                convert[i+1] = Character.toUpperCase(convert[i+1]);
            }
        }
        string = new String(convert);
        string = string.replace(" ", "");
        return string;
    }

    public static String camelToUpper(String input) {
        if (input == null || input.isEmpty()) return input;

        String result = input
                .replaceAll("([a-z])([A-Z])", "$1 $2")   // space before capitals
                .replaceAll("([A-Z])([A-Z][a-z])", "$1 $2") // ABCWord → ABC Word
                .replaceAll("([0-9])([A-Z, a-z])", "$1 $2")
                .replaceAll("([A-Z, a-z])([0-9])", "$1 $2")
                .replaceAll("(#)", " $1");

        return result.substring(0, 1).toUpperCase() + result.substring(1);
    }
}
