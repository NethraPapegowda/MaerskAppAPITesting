package util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Helper class to resolve font issues with PDF reporting
 */
public class FontManager {
    private static final Logger LOGGER = Logger.getLogger(FontManager.class.getName());
    
    /**
     * Initializes the fonts needed for PDF reporting
     */
    public static void initializeFonts() {
        try {
            // Register all available system fonts
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            
            // Log all available fonts
            Font[] fonts = ge.getAllFonts();
            LOGGER.info("Available fonts: " + fonts.length);
            for (Font font : fonts) {
                LOGGER.fine("Font: " + font.getFontName());
            }
            
            // Set system property for PDF reporting
            System.setProperty("extent.reporter.pdf.start", "true");
            System.setProperty("extent.reporter.pdf.out", "target/cucumber-reports/ExtentPdf.pdf");
            
            // Use Arial as backup
            System.setProperty("extent.reporter.pdf.fontsdir", "/System/Library/Fonts");
            System.setProperty("extent.reporter.pdf.fonts", "Arial, Helvetica, Times New Roman");
        } catch (Exception e) {
            LOGGER.warning("Failed to initialize fonts: " + e.getMessage());
        }
    }
    
    /**
     * Check if a font is available in the system
     */
    public static boolean isFontAvailable(String fontName) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = ge.getAllFonts();
        for (Font font : fonts) {
            if (font.getFontName().equalsIgnoreCase(fontName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Register a custom font from a file
     */
    public static boolean registerFont(File fontFile) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            return true;
        } catch (IOException | FontFormatException e) {
            LOGGER.warning("Failed to register font: " + e.getMessage());
            return false;
        }
    }
}
