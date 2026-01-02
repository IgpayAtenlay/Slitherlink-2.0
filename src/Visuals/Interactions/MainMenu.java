package Visuals.Interactions;

import Autogen.Generate;
import Memory.Dimentions;
import Memory.MemorySet;
import PuzzleLoading.PDFtoFile;
import PuzzleLoading.Read;
import Visuals.Frame;
import Visuals.Panel.Puzzle;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainMenu {
    private final Frame frame;
    public MainMenu(Frame frame) {
        this.frame = frame;
    }
    public void load() {
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
    public void importPDFs() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("public/puzzles/pdf"), "*.pdf")) {
            for (Path path : stream) {
                PDFtoFile.write(String.valueOf(path));
            }
        } catch (IOException ignore) {
        }
    }
    public void newPuzzle(int xSize, int ySize) {
        MemorySet memorySet = new MemorySet(new Dimentions(xSize, ySize));
        frame.switchPanel(new Puzzle(memorySet, frame));
    }
    public void generatePuzzle(int xSize, int ySize) {
        MemorySet memorySet = new MemorySet(new Dimentions(xSize, ySize));
        Generate.generate(memorySet.getVisible());
        frame.switchPanel(new Puzzle(memorySet, frame));
    }
}
