package Visuals.Interactions;

import Memory.MemorySet;
import PuzzleLoading.Read;
import Visuals.Frame;
import Visuals.Panel.Puzzle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMenu {
    private final Frame frame;
    public MainMenu(Frame frame) {
        this.frame = frame;
    }
    public void load() {
//        frame.switchPanel(new LoadingScreen(frame));
        JFileChooser fileChooser = new JFileChooser(new File("public/puzzles"));
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "JSON Files", "json"
        ));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String absolutePath = selectedFile.getAbsolutePath();

            Path base = Paths.get("public/puzzles").toAbsolutePath();
            Path target = Paths.get(absolutePath);
            String filename = String.valueOf(base.relativize(target));

            MemorySet memorySet = Read.read(filename);
            if (memorySet != null) {
                frame.switchPanel(new Puzzle(memorySet, frame));
            }
        }
    }
}
