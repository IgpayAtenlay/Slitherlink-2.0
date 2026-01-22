# Slitherlink Solver

A comprehensive Slitherlink puzzle solver and editor with an intuitive graphical interface. Create, solve, and play Slitherlink puzzles with advanced solving algorithms and interactive features.

This project uses a constraint satisfaction techniques to automatically solve puzzles rapidly. It also generates puzzles using a breath first search to carve out the path of the puzzle.

## What is Slitherlink?

Slitherlink is a paper-and-pencil logic puzzle similar to Sodoku. The goal is to draw a single, non-intersecting loop that connects dots in a grid. Each numbered cell indicates how many of its four sides are part of the loop.

## Overview

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
  - Apache PDFBox 3.0.4

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

The solver addresses this constraint satisfaction problem using a multi-stage approach:

1. **Single Block Logic**: Analyzes individual cells based on their numbers
2. **Point Actions**: Processes intersection points and corner constraints
3. **Adjacent Blocks**: Considers relationships between neighboring cells
4. **Diagonal Blocks**: Analyzes diagonal cell relationships
5. **Line-to-Block Inference**: Deduces line states from block constraints
6. **Loop Checking**: Ensures the loop remains valid and connected
7. **Trapped Detection**: Identifies cells that must be inside/outside the loop
8. **Guess-and-Check**: Advanced technique for difficult puzzles (optional)

## Puzzle Generation

The application includes an automatic puzzle generator that creates valid, solvable Slitherlink puzzles. The generation process works in three stages:

### 1. Shape Generation
The generator starts by creating a random valid loop shape using breadth-first search:
- Begins from a random starting cell
- Uses breadth-first search to mark cells as INSIDE or OUTSIDE the loop
- Ensures the shape is connected and valid (no isolated diagonal cells)
- Validates that diagonal neighbors are properly connected through cardinal directions

### 2. Line and Number Generation
Based on the generated shape, the system creates the loop and assigns numbers:
- **Line placement**: Edges between cells with opposite highlights (INSIDE/OUTSIDE) become lines; edges between same-highlight cells are marked as X
- **Number assignment**: Each cell's number is set to match the count of lines surrounding it (0-3)

### 3. Number Trimming
To create a solvable puzzle with minimal clues, the generator removes unnecessary numbers:
- Tests each number in random order
- Temporarily removes a number and attempts to solve the puzzle using the auto-solver
- If the puzzle can still be solved without that number, it is permanently removed
- Continues until all numbers have been tested
- Results in a minimal puzzle with only the essential clues needed for a unique solution

### Usage
To generate a puzzle:
1. Select **Generate Puzzle** from the main menu
2. Specify the desired grid dimensions (e.g., 7x7, 20x20)
3. The generator will create a new puzzle automatically
4. You can then save the puzzle or start solving it immediately

**Note**: Generation time increases with puzzle size, as the trimming process requires solving the puzzle multiple times to verify each number's necessity.

## Contributing

This is a personal project, but suggestions and improvements are welcome!

## License

Released under MIT License

Copyright (c) 2026 Melissa Cron.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

## Notes

- Puzzles are stored relative to the `public/puzzles/` directory
- PDF import requires PDFBox library (already included)
- The solver can handle puzzles of various sizes, though larger puzzles may take longer to solve

---

Enjoy solving Slitherlink puzzles! 🧩
