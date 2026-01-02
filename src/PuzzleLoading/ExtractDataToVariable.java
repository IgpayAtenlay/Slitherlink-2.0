package PuzzleLoading;

import Enums.Difficulty;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ExtractDataToVariable {
    public static List<TextData> extract(String filePath) {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            if (!document.isEncrypted()) { // Ensure the document is not encrypted
                PDFTextStripperLocations pdfStripper = new PDFTextStripperLocations();
                pdfStripper.getText(document);
                return pdfStripper.getExtractedText();
            } else {
                System.out.println("The document is encrypted and cannot be read.");
            }
        } catch (IOException e) {
            System.err.println("Error reading PDF: " + e.getMessage());
        }

        return null;
    }
    public static List<TextData> extract(Difficulty difficulty, int size, int volume, int book) {
        return extract(difficulty.toString() + size + "Volume" + volume + "Book" + book);
    }
}

