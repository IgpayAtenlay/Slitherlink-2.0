package Visuals.Interactions;

import Memory.MemorySet;
import PuzzleLoading.Read;
import Visuals.Frame;
import Visuals.Panel.Puzzle;

public class LoadingScreen {
    private final Frame frame;
    public LoadingScreen(Frame frame) {
        this.frame = frame;
    }
    public void load(String filename) {
        MemorySet memorySet = Read.read(filename);
        if (memorySet != null) {
            frame.switchPanel(new Puzzle(memorySet, frame));
        }
    }
}
