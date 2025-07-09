package com.example.designpatterns.gof;

/**
 * Adapter Pattern Example
 * Allows incompatible interfaces to work together.
 * Interview Insight: Used to integrate legacy code or third-party APIs with your system.
 */
/**
 * Adapter Pattern Example
 *
 * Definition: Allows incompatible interfaces to work together.
 *
 * Real-life analogy: Viewing documents of different formats (PDF, Word) through a common viewer interface.
 *
 * Interview explanation:
 * - Adapter is used to integrate legacy code or third-party APIs with your system.
 * - "Suppose you want to display both PDF and Word documents in your app, but each has a different interface. Adapter lets you use both through a common interface."
 *
 * Real-life Example: Document viewer for PDF and Word
 */
public class AdapterExample {
    // Target interface
    public interface DocumentViewer {
        String view(String fileType, String fileName);
    }

    // Adaptee 1: PDF reader
    public static class PdfReader {
        public String openPdf(String fileName) {
            return "Opened PDF: " + fileName;
        }
    }
    // Adaptee 2: Word reader
    public static class WordReader {
        public String openWord(String fileName) {
            return "Opened Word document: " + fileName;
        }
    }

    // Adapter
    public static class DocumentAdapter implements DocumentViewer {
        private final PdfReader pdfReader = new PdfReader();
        private final WordReader wordReader = new WordReader();
        public String view(String fileType, String fileName) {
            return switch (fileType.toLowerCase()) {
                case "pdf" -> pdfReader.openPdf(fileName);
                case "word" -> wordReader.openWord(fileName);
                default -> "Unsupported file type: " + fileType;
            };
        }
    }

    // Client
    public static class UniversalViewer {
        private final DocumentViewer adapter = new DocumentAdapter();
        public String viewDocument(String fileType, String fileName) {
            return adapter.view(fileType, fileName);
        }
    }
}

