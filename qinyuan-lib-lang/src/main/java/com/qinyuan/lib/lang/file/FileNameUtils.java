package com.qinyuan.lib.lang.file;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isBlank(fileName)) {
            return fileName;
        }

        fileName = fileName.replace("\\", "/");

        if (fileName.contains("/")) {
            String[] fileNameParts = fileName.split("[/|\\\\]");
            for (int i = 0; i < fileNameParts.length; i++) {
                fileNameParts[i] = getAsciiFileName(fileNameParts[i]);
            }
            return Joiner.on("/").join(fileNameParts);
        } else {
            try {
                fileName = URLEncoder.encode(fileName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            fileName = fileName.replace("+", "_ps_").replace("%", "_pc_");
            return fileName;
        }
    }

    public static String prune(String fileName, int maxLength) {
        if (StringUtils.isEmpty(fileName)) {
            return fileName;
        }

        if (fileName.length() > maxLength) {
            fileName = fileName.substring(fileName.length() - maxLength, fileName.length());
        }
        return fileName;
    }
}
