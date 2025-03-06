package Enums;

public enum Difficulty {
    EASY ("Easy"),
    INTERMEDIATE ("Intermediate"),
    TOUGH ("Tough");
    final String string;
    Difficulty(String string) {
        this.string = string;
    }
    public String toString() {
        return string;
    }
}
