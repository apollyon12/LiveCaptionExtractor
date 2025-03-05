package org.rh.utils;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;

public class LiveCaptionDetector {
    public static Rectangle detectLiveCaptionWindow() {
        User32 user32 = User32.INSTANCE;
        WinDef.HWND hwnd = user32.FindWindow(null, "Live Captions");

        if (hwnd == null) {
            System.out.println("Live Caption Window Not Found!");
            return null;
        }

        WinDef.RECT rect = new WinDef.RECT();
        user32.GetWindowRect(hwnd, rect);
        System.out.println("Live Caption Rect:" + rect);
        try {
            return rect.toRectangle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
