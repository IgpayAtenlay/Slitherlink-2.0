package Visuals.Interactions;

import Memory.MemorySet;
import PuzzleLoading.Read;
import Visuals.Frame;
import Visuals.Panel.Puzzle;

public class MainMenu {
    private final Frame frame;
    public MainMenu(Frame frame) {
        this.frame = frame;
    }
    public void load() {
        MemorySet memorySet = Read.read("example");
        if (memorySet != null) {
            frame.switchPanel(new Puzzle(memorySet, frame));
        }
    }
}
