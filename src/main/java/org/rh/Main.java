package org.rh;

import org.rh.utils.HotkeyManager;

public class Main {
    public static void main(String[] args) {
        HotkeyManager hotkeyManager = new HotkeyManager();
        hotkeyManager.registerHotkeys();
        System.out.println("Application started.");
    }
}