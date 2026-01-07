package Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFinder {
    public static int extractPartOfPattern(String string, String regex, int start, int end) {
        Pattern difficultyPattern = Pattern.compile(regex);
        Matcher difficultyMatcher = difficultyPattern.matcher(string);
        if (!difficultyMatcher.find()) throw new RuntimeException();
        return Integer.parseInt(difficultyMatcher.group().substring(start, end + 1));
    }
}
