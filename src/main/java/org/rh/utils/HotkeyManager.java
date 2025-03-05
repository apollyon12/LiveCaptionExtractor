package org.rh.utils;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HotkeyManager implements NativeKeyListener {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isRunning = false;

    public void registerHotkeys() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int key = e.getKeyCode();
        boolean isCtrlPressed = (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0;

        if (key == NativeKeyEvent.VC_1 && isCtrlPressed) {
            System.out.println("Starting caption extraction...");
            isRunning = true;
            executor.submit(this::captureLiveCaptions);
        } else if (key == NativeKeyEvent.VC_2 && isCtrlPressed) {
            System.out.println("Copying cached text and clearing...");
            ClipboardUtil.copyToClipboard(CacheManager.getCachedText());
            CacheManager.clearCache();
        } else if (key == NativeKeyEvent.VC_3 && isCtrlPressed) {
            System.out.println("Stopping application...");
            isRunning = false;
            executor.shutdown();
//            System.exit(0);
        }
    }

    private void captureLiveCaptions() {
        try {
            Robot robot = new Robot();
            while (isRunning) {
                Rectangle screenRect = LiveCaptionDetector.detectLiveCaptionWindow();
                if (screenRect == null) continue;

                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                String extractedText = OCRProcessor.extractText(screenshot);
                CacheManager.addText(extractedText);

                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {}
}
