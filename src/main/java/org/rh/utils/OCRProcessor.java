package org.rh.utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OCRProcessor {
    private static final ITesseract tesseract = new Tesseract();

    static {
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        tesseract.setLanguage("eng");
        tesseract.setPageSegMode(6);
    }

    public static String extractText(BufferedImage image) {
        try {
            BufferedImage processedImage = setDPI(image, 300);
            return tesseract.doOCR(processedImage).trim();
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        return "";
    }
    private static BufferedImage setDPI(BufferedImage image, int dpi) {
        try {
            File tempFile = new File("tempImage.png");
            ImageIO.write(image, "png", tempFile);

            BufferedImage newImage = ImageIO.read(tempFile);
            tempFile.delete();

            return newImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
