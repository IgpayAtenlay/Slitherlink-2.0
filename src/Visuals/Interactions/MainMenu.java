package Visuals.Interactions;

import Visuals.Frame;
import Visuals.Panel.LoadingScreen;

public class MainMenu {
    private final Frame frame;
    public MainMenu(Frame frame) {
        this.frame = frame;
    }
    public void load() {
        frame.switchPanel(new LoadingScreen(frame));
    }
}
