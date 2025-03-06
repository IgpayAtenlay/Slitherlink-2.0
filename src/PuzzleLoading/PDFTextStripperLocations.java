package PuzzleLoading;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFTextStripperLocations extends PDFTextStripper {
    private final List<TextData> extractedText = new ArrayList<>();
    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        for (TextPosition textPos : textPositions) {
            extractedText.add(new TextData(
                    textPos.getUnicode(),
                    textPos.getXDirAdj(),
                    textPos.getYDirAdj(),
                    textPos.getWidthDirAdj(),
                    textPos.getHeightDir()
            ));
        }
    }
    public List<TextData> getExtractedText() {
        return extractedText;
    }
}
