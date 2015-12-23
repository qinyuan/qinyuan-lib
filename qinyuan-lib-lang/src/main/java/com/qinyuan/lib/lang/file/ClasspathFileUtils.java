package com.qinyuan.lib.lang.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Get file in classpath
 * Created by qinyuan on 15-6-11.
 */
public class ClasspathFileUtils {
    public static File getFile(String relativePath) {
        URL url = ClasspathFileUtils.class.getClassLoader().getResource(relativePath);
        return url == null ? null : new File(url.getFile());
    }

    public static InputStream getInputStream(String relativePath) {
        File file = getFile(relativePath);
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties getProperties(String relativePath) {
        Properties props = new Properties();
        try {
            props.load(getInputStream(relativePath));
            return props;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Read content of properties file, then convert to map.
     * If there are Chinese characters, this method will deal it correctly
     *
     * @param relativePath relative path of target file
     * @return map that contains content of property file
     */
    public static Map<String, String> getPropertyMap(String relativePath) {
        File file = getFile(relativePath);
        String fileContent;
        try {
            fileContent = FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> result = new HashMap<>();
        for (String line : fileContent.split("\n")) {
            if (StringUtils.isBlank(line) || !line.contains("=")) {
                continue;
            }
            int splitIndex = line.indexOf("=");
            result.put(line.substring(0, splitIndex), line.substring(splitIndex + 1));
        }
        return result;
    }

    public static boolean isFile(String relativePath) {
        File file = getFile(relativePath);
        return file != null && file.isFile();
    }
}
