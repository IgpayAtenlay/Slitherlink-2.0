package PuzzleLoading;

import PuzzleLoading.PDFtoJson.TextLocation;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileToData {
    public static List<TextLocation> extract(String filePath) {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            if (!document.isEncrypted()) {
                PDFTextStripperWithLocations pdfStripper = new PDFTextStripperWithLocations();
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
}

