package PuzzleLoading;

import PuzzleLoading.PDFtoJson.TextLocation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.util.ArrayList;
import java.util.List;

public class PDFTextStripperWithLocations extends PDFTextStripper {
    private final List<TextLocation> extractedText = new ArrayList<>();
    @Override
    protected void writeString(String text, List<TextPosition> textPositions) {
        for (TextPosition textPos : textPositions) {
            extractedText.add(new TextLocation(
                    textPos.getUnicode(),
                    textPos.getXDirAdj(),
                    textPos.getYDirAdj()
            ));
        }
    }
    public List<TextLocation> getExtractedText() {
        return extractedText;
    }
}
