package com.chatbot.commonlibrary.util;

import java.io.File;

public class FileUtil {
    public static boolean fileExists(String path) {
        return new File(path).exists();
    }

    public static String getExtension(String filename) {
        return filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : "";
    }
}
