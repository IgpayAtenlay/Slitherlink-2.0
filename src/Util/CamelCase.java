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
}
