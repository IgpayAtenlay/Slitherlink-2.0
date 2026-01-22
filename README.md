# Slitherlink Solver 3.0

A comprehensive Java-based Slitherlink puzzle solver and editor with an intuitive graphical interface. Create, solve, and play Slitherlink puzzles with advanced solving algorithms and interactive features.

## Overview

Slitherlink is a logic puzzle where you draw a single, non-intersecting loop that connects dots in a grid. Each numbered cell indicates how many of its four sides are part of the loop.

This application provides:
- **Interactive puzzle solving** with mouse and keyboard controls
- **Automatic solving algorithms** with multiple solving strategies
- **Puzzle generation** for custom puzzle creation
- **PDF import** support for loading puzzles from PDF files
- **JSON-based puzzle storage** for easy puzzle management
- **Error checking** and completion validation
- **Undo/redo** functionality for easy puzzle editing

## Features

### Core Functionality
- **Load Puzzles**: Import puzzles from JSON files or PDF documents
- **Create Puzzles**: Generate new puzzles or create custom puzzles from scratch
- **Interactive Solving**: Click and drag to place lines, use keyboard shortcuts for numbers and annotations
- **Auto-Solve**: Automatic solving with multiple algorithms. See [Solving Algorithms](#solving-algorithms) for more details
- **Error Detection**: Real-time error checking for invalid line configurations
- **Completion Checking**: Verify when puzzles are correctly solved
- **Save/Load**: Save puzzles in JSON format for later use

### Puzzle Management
- Support for multiple puzzle sizes (7x7, 20x20, and custom sizes)
- Organized puzzle library with difficulty levels (Easy, Intermediate, Tough)
- Custom puzzle creation and editing
- PDF to JSON conversion for imported puzzles

## Requirements

- **Java**: JDK 8 or higher
- **Libraries**:
  - Apache PDFBox 3.0.4 (included in `src/PuzzleLoading/pdfbox-app-3.0.4.jar`)

## Installation

1. Clone or download this repository
2. Ensure you have Java JDK 8+ installed
3. The PDFBox library is already included in the project
4. Compile the project using your preferred Java IDE (IntelliJ IDEA, Eclipse, etc.)

## Usage

### Running the Application

Use your IDE's run configuration.

### Main Menu Options

- **Load Puzzle**: Browse and load a puzzle from the `public/puzzles/` directory
- **New Puzzle**: Create a blank puzzle with custom dimensions
- **Generate Puzzle**: Automatically generate a new puzzle
- **Import PDFs**: Convert PDF puzzle files to JSON format

### Puzzle Solving Controls

#### Mouse Controls
- **Left Click**: Place/remove a line
- **Right Click**: Mark a line as X (not part of the loop)
- **Drag**: Quickly place multiple lines

#### Keyboard Controls
- **0-3**: Place numbers (0, 1, 2, or 3) in cells
- **I**: Toggle inside highlight
- **O**: Toggle outside highlight
- **W**: Set corner to "both or neither"
- **S**: Set corner to "exactly one"
- **A**: Set corner to "at least one"
- **D**: Set corner to "at most one"

#### Menu Actions
- **Auto Solve**: Automatically solve the entire puzzle
- **One Step**: Apply one round of solving logic
- **Check Errors**: Verify puzzle for errors
- **Check Completion**: Verify if puzzle is complete
- **Highlight**: Apply visual highlighting
- **Undo/Redo**: Navigate through puzzle history
- **Reset**: Clear all lines and start over
- **Save/Save As**: Save puzzle to JSON file

## Solving Algorithms

The solver uses a multi-stage approach:

1. **Single Block Logic**: Analyzes individual cells based on their numbers
2. **Point Actions**: Processes intersection points and corner constraints
3. **Adjacent Blocks**: Considers relationships between neighboring cells
4. **Diagonal Blocks**: Analyzes diagonal cell relationships
5. **Line-to-Block Inference**: Deduces line states from block constraints
6. **Loop Checking**: Ensures the loop remains valid and connected
7. **Trapped Detection**: Identifies cells that must be inside/outside the loop
8. **Guess-and-Check**: Advanced technique for difficult puzzles (optional)

## Contributing

This is a personal project, but suggestions and improvements are welcome!

## License

This project is provided as-is for educational and personal use.

## Notes

- Puzzles are stored relative to the `public/puzzles/` directory
- PDF import requires PDFBox library (already included)
- The solver can handle puzzles of various sizes, though larger puzzles may take longer to solve

---

Enjoy solving Slitherlink puzzles! 🧩
