package Visuals.Panel;

import Enums.*;
import Memory.Coords;
import Memory.MemorySet;
import Visuals.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Puzzle extends JPanel {
    private final MemorySet memorySet;
    public Visuals.Interactions.Puzzle puzzleInteractions;
    private final Frame frame;

    private boolean checkAccuracy;
    private JLabel errorChecking;
    private JLabel completionChecking;
    private Timer keyTimer = null;
    private int keyCharacter = 0;

    private static final int DOT_DIAMETER = 6;
    private static final int STARTING_X = 20;
    private static final int STARTING_Y = 20;
    private static final int LINE_SIZE = 30;
    private static final int LINE_WIDTH = 2;
    private static final double HEIGHT_OFFSET = 2.3;
    private static final Color CORRECT_COLOR = Color.GREEN;
    private static final Color INCORRECT_COLOR = Color.RED;
    private static final int BUTTON_GAP = 5;

    private final int componentX;
    private int componentY;

    public Puzzle(MemorySet memorySet, Frame frame) {
        this.memorySet = memorySet;
        this.frame = frame;
        this.puzzleInteractions = new Visuals.Interactions.Puzzle(memorySet, this);
        componentX = STARTING_X * 2 + memorySet.getVisible().getDimentions().xSize * LINE_SIZE;
        componentY = STARTING_Y;
        setLayout(null);
        setFont(getFont().deriveFont(Font.BOLD, 14f));
        setFocusable(true);

        setupMouseControls();
        setupKeyBindings();

        createButton("Save", e -> puzzleInteractions.save());
        createButton("Check Accuracy", e -> puzzleInteractions.checkAccuracy());
        createButton("Show Solution", e -> puzzleInteractions.autoSolve());
        createButton("Autosolve - one step", e -> puzzleInteractions.autoSolveOneStep());
        createButton("Highlight", e -> puzzleInteractions.highlight());
        createButton("Undo", e -> puzzleInteractions.undo());
        createButton("50 Undo", e -> puzzleInteractions.undo(50));
        createButton("Redo", e -> puzzleInteractions.redo());
        createButton("50 Redo", e -> puzzleInteractions.redo(50));
        errorChecking = createLabeledButton("Check for Errors", e -> puzzleInteractions.checkForErrors());
        completionChecking = createLabeledButton("Check for Completetion", e -> puzzleInteractions.checkForCompletetion());
    }

    private void setupMouseControls() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                stopKeyTimer();
                puzzleInteractions.click(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                stopKeyTimer();
                puzzleInteractions.drag(e);
            }
        });
    }
    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        for (int i = 0x30; i <= 0x5A; i++) {
            final char c = (char) i;
            setupKeyBinding(c, inputMap, actionMap);
        }
        for (char c : new char[] {
                java.awt.event.KeyEvent.VK_BACK_SPACE,

        }) {
            setupKeyBinding(c, inputMap, actionMap);
        }
    }
    private void setupKeyBinding(int keyCode, InputMap inputMap, ActionMap actionMap) {
        // Key pressed - start dragging
        KeyStroke keyPressedStroke = KeyStroke.getKeyStroke(keyCode, 0);
        String pressedActionKey = "keyPressed_" + (char)(keyCode);
        
        Action keyPressedAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopKeyTimer();
                startKeyTimer(keyCode);
            }
        };
        
        // Key released - stop dragging
        KeyStroke keyReleasedStroke = KeyStroke.getKeyStroke(keyCode, 0, true);
        String releasedActionKey = "keyReleased_" + keyCode;
        
        Action keyReleasedAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (keyCharacter == keyCode) {
                    stopKeyTimer();
                }
            }
        };

        inputMap.put(keyPressedStroke, pressedActionKey);
        actionMap.put(pressedActionKey, keyPressedAction);
        inputMap.put(keyReleasedStroke, releasedActionKey);
        actionMap.put(releasedActionKey, keyReleasedAction);
    }
    private void performKeyAction(int keyCode) {
        Point mouseLocationScreen = MouseInfo.getPointerInfo().getLocation();
        Point panelLocation = getLocationOnScreen();
        Point mouseLocationPanel = new Point(mouseLocationScreen.x - panelLocation.x, mouseLocationScreen.y - panelLocation.y);
        puzzleInteractions.numbers(keyCode, mouseLocationPanel);
    }
    private void startKeyTimer(int keyCode) {
        stopKeyTimer();
        keyTimer = new Timer(50, evt -> performKeyAction(keyCode));
        keyTimer.setInitialDelay(0);
        keyCharacter = keyCode;
        keyTimer.start();
    }
    private void stopKeyTimer() {
        if (keyTimer != null) {
            keyCharacter = 0;
            keyTimer.stop();
            keyTimer = null;
        }
    }
    @Override
    public void removeNotify() {
        super.removeNotify();
        stopKeyTimer();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(LINE_WIDTH));

        drawHighlights(g2d);
        drawNumbers(g2d);
        drawLines(g2d);
        drawDiagonals(g2d);
        drawDots(g2d);

    }

    private void drawDots(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize + 1; x++) {
                Coords dotCoords = getNorthWestDotCoords(new Coords(x, y));
                g.fillOval(dotCoords.x - DOT_DIAMETER / 2,
                        dotCoords.y - DOT_DIAMETER / 2,
                        DOT_DIAMETER,
                        DOT_DIAMETER);
            }
        }
    }
    private void drawNumbers(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                String text = memorySet.getVisible().getNumber(coords).toString(true);
                int textWidth = g.getFontMetrics().stringWidth(text);
                g.drawString(text,
                        getSquareCenterCoords(coords).x - textWidth / 2,
                        getSquareCenterCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
            }
        }
    }
    private void drawLines(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize + 1; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize + 1; x++) {
                Coords coords = new Coords(x, y);
                Line eastLine = memorySet.getVisible().getLine(false, coords, CardinalDirection.EAST);
                Line eastLineAnswer = memorySet.getCalculation().getLine(false, coords, CardinalDirection.EAST);
                Line southLine = memorySet.getVisible().getLine(false, coords, CardinalDirection.SOUTH);
                Line southLineAnswer = memorySet.getCalculation().getLine(false, coords, CardinalDirection.SOUTH);

                if (checkAccuracy) {
                    if (eastLineAnswer == Line.EMPTY) {
                        g.setColor(startingColor);
                    } else if (eastLine == eastLineAnswer) {
                        g.setColor(CORRECT_COLOR);
                    } else {
                        g.setColor(INCORRECT_COLOR);
                    }
                }

                if (eastLine == Line.LINE && x != memorySet.getVisible().getDimentions().xSize) {
                    g.drawLine(getNorthWestDotCoords(coords).x,
                            getNorthWestDotCoords(coords).y,
                            getNorthWestDotCoords(coords.addDirection(CardinalDirection.EAST)).x,
                            getNorthWestDotCoords(coords.addDirection(CardinalDirection.EAST)).y);
                } else if (eastLine == Line.X && x != memorySet.getVisible().getDimentions().xSize) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getSquareCenterCoords(coords).x - textWidth / 2,
                            getNorthWestDotCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
                }

                if (checkAccuracy) {
                    if (southLineAnswer == Line.EMPTY) {
                        g.setColor(startingColor);
                    } else if (southLine == southLineAnswer) {
                        g.setColor(CORRECT_COLOR);
                    } else {
                        g.setColor(INCORRECT_COLOR);
                    }
                }

                if (southLine == Line.LINE && y != memorySet.getVisible().getDimentions().ySize) {
                    g.drawLine(getNorthWestDotCoords(coords).x,
                            getNorthWestDotCoords(coords).y,
                            getNorthWestDotCoords(coords.addDirection(CardinalDirection.SOUTH)).x,
                            getNorthWestDotCoords(coords.addDirection(CardinalDirection.SOUTH)).y);
                } else if (southLine == Line.X && y != memorySet.getVisible().getDimentions().ySize) {
                    String text = Line.X.toString();
                    int textWidth = g.getFontMetrics().stringWidth(text);
                    g.drawString(text,
                            getNorthWestDotCoords(coords).x - textWidth / 2,
                            getSquareCenterCoords(coords).y + (int) (getFont().getSize() / HEIGHT_OFFSET));
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawHighlights(Graphics g) {
        Color startingColor = g.getColor();
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                Highlight highlight = memorySet.getVisible().getHighlight(coords);
                if (highlight != Highlight.EMPTY) {
                    if (highlight == Highlight.INSIDE) {
                        g.setColor(new Color(193, 255, 176));
                    } else if (highlight == Highlight.OUTSIDE) {
                        g.setColor(new Color(198, 255, 255));
                    }
                    g.fillRect(getNorthWestDotCoords(coords).x,
                            getNorthWestDotCoords(coords).y,
                            LINE_SIZE,
                            LINE_SIZE);
                }
            }
        }
        g.setColor(startingColor);
    }
    private void drawDiagonals(Graphics g) {
        for (int y = 0; y < memorySet.getVisible().getDimentions().ySize; y++) {
            for (int x = 0; x < memorySet.getVisible().getDimentions().xSize; x++) {
                Coords coords = new Coords(x, y);
                for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
                    Diagonal diagonal = memorySet.getVisible().getDiagonal(true, coords, diagonalDirection);
                    Coords start = null;
                    Coords end = null;
                    switch (diagonalDirection) {
                        case NORTHEAST -> {
                            Coords dotCoords = getNorthWestDotCoords(coords.addDirection(CardinalDirection.EAST));
                            start = new Coords(dotCoords.x - LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y + LINE_SIZE / 4);

                        }
                        case SOUTHEAST -> {
                            Coords dotCoords = getNorthWestDotCoords(coords.addDirection(DiagonalDirection.SOUTHEAST));
                            start = new Coords(dotCoords.x - LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y - LINE_SIZE / 4);
                        }
                        case SOUTHWEST -> {
                            Coords dotCoords = getNorthWestDotCoords(coords.addDirection(CardinalDirection.SOUTH));
                            start = new Coords(dotCoords.x + LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y - LINE_SIZE / 4);
                        }
                        case NORTHWEST -> {
                            Coords dotCoords = getNorthWestDotCoords(coords);
                            start = new Coords(dotCoords.x + LINE_SIZE / 4, dotCoords.y);
                            end = new Coords(dotCoords.x, dotCoords.y + LINE_SIZE / 4);
                        }
                    }

                    switch (diagonal) {
                        case EXACTLY_ONE -> {
                            g.drawLine(start.x, start.y, end.x, end.y);
                        }
                        case BOTH_OR_NEITHER -> {
                            g.drawLine(start.x, start.y, start.x, end.y);
                            g.drawLine(start.x, end.y, end.x, end.y);
                        }
                        case AT_LEAST_ONE -> {
                            g.drawLine(start.x, end.y, end.x, start.y);
                        }
                        case AT_MOST_ONE -> {
                            g.drawOval(start.x, end.y, 2, 2);
                            g.drawOval(end.x, start.y, 2, 2);
                        }
                    }
                }
            }
        }
    }

    public Coords getNorthWestDotCoords(Coords coords) {
        return new Coords(STARTING_X + LINE_SIZE * coords.x, STARTING_Y + LINE_SIZE * coords.y);
    }
    public Coords getSquareCenterCoords(Coords coords) {
        return new Coords(STARTING_X + LINE_SIZE / 2 + coords.x * LINE_SIZE, STARTING_Y + LINE_SIZE / 2 + coords.y * LINE_SIZE);
    }
    public Coords getSquareIndex(Point coords) {
        return new Coords((coords.x - STARTING_X) / LINE_SIZE, (coords.y - STARTING_Y) / LINE_SIZE);
    }
    public CardinalDirection getLineDirection(Point clickCoords) {
        Coords squareIndex = getSquareIndex(clickCoords);
        Coords northWestDotCoords = getNorthWestDotCoords(squareIndex);
        Coords relativeCoords = new Coords(clickCoords.x - northWestDotCoords.x, clickCoords.y - northWestDotCoords.y);

        CardinalDirection direction;
        if (relativeCoords.x > relativeCoords.y) {
            if (relativeCoords.x + relativeCoords.y > getLineSize()) {
                direction = CardinalDirection.EAST;
            } else {
                direction = CardinalDirection.NORTH;
            }
        } else {
            if (relativeCoords.x + relativeCoords.y > getLineSize()) {
                direction = CardinalDirection.SOUTH;
            } else {
                direction = CardinalDirection.WEST;
            }
        }
        return direction;
    }
    public int getLineSize() {
        return LINE_SIZE;
    }

    public void toggleCheckAccuracy() {
        checkAccuracy = !checkAccuracy;
    }

    public void createButton(String text, ActionListener l) {
        JButton checkAccuracy = new JButton(text);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(text) + 50;
        checkAccuracy.setBounds(componentX, componentY, buttonWidth, buttonHeight);
        checkAccuracy.addActionListener(l);
        add(checkAccuracy);
        componentY += buttonHeight + BUTTON_GAP;
    }
    public JLabel createLabeledButton(String buttonText, ActionListener l) {
        JButton checkAccuracy = new JButton(buttonText);
        int buttonHeight = getFont().getSize() + 2;
        int buttonWidth = getFontMetrics(getFont()).stringWidth(buttonText) + 50;
        checkAccuracy.setBounds(componentX, componentY, buttonWidth, buttonHeight);
        checkAccuracy.addActionListener(l);
        add(checkAccuracy);

        JLabel label = new JLabel();
        label.setBounds(componentX + buttonWidth + 10, componentY, 100, buttonHeight);
        label.setFont(getFont());
        add(label);

        componentY += buttonHeight + BUTTON_GAP;
        return label;
    }

    public void setErrorChecking(boolean hasError) {
        this.errorChecking.setText(hasError ? "Error" : "No Error");
    }
    public void setCompletionChecking(boolean isComplete) {
        this.completionChecking.setText(isComplete ? "Complete" : "Not Complete");
    }
}