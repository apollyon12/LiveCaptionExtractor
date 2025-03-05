package org.rh.utils;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
    private static final Set<String> textCache = new HashSet<>();

    public static synchronized void addText(String text) {
        if (!textCache.contains(text) && !text.isEmpty()) {
            textCache.add(text);
        }
    }

    public static synchronized String getCachedText() {
        return String.join("\n", textCache);
    }

    public static synchronized void clearCache() {
        textCache.clear();
    }
}
