package org.rh.utils;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class ClipboardUtil {
    public static void copyToClipboard(String text) {
        StringSelection selection = new StringSelection(text);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
    }
}
