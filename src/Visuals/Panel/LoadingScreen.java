package Visuals.Panel;

import Util.CamelCase;
import Visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoadingScreen extends JPanel {
    public Visuals.Interactions.LoadingScreen loadingScreenInteractions;
    private static final int STARTING_X = 20;
    private static final int STARTING_Y = 20;
    private static final int BUTTON_GAP = 5;

    private final int componentX;
    private int componentY;
    public LoadingScreen(Frame frame) {
        componentX = STARTING_X;
        componentY = STARTING_Y;
        setLayout(null);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        loadingScreenInteractions = new Visuals.Interactions.LoadingScreen(frame);

        createText("Loading");

        loadingButton();
    }

    public void createText(String text) {
        JLabel heading = new JLabel(text);
        heading.setBounds(componentX, componentY, 100, 40);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        componentY += 40;
        add(heading);
    }

    public void loadingButton() {
        Path folder = Paths.get("public/puzzles/json");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, "*.json")) {
            for (Path file : stream) {
                String filename = String.valueOf(file.getFileName());
                if (filename.endsWith(".json")) {
                    filename = filename.substring(0, filename.length() - 5);
                    filename = CamelCase.camelToUpper(filename);
                    loadingButton(filename);
                }
            }
        } catch (Exception ignored) {
        }
    }
    public void loadingButton(String filename) {
        JButton button = new JButton(filename);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(filename) + 50;
        button.setBounds(componentX, componentY, buttonWidth, buttonHeight);
        button.addActionListener(e -> loadingScreenInteractions.load(filename));
        add(button);
        componentY += buttonHeight + BUTTON_GAP;
    }
}
