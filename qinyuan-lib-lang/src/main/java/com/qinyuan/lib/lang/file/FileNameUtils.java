package com.qinyuan.lib.lang.file;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileNameUtils {
    private FileNameUtils() {
    }

    /**
     * convert file name to a new name containing only ascii characters
     *
     * @return a new name containing only ascii characters
     */
    public static String getAsciiFileName(String fileName) {
        try {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        fileName = fileName.replace("+", "_plus_").replace("%", "percent");
        return fileName;
    }
}
