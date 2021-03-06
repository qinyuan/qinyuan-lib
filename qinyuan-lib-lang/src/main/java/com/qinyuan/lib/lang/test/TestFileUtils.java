package com.qinyuan.lib.lang.test;

import com.qinyuan.lib.lang.file.ClasspathFileUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Utils class about test file
 * Created by qinyuan on 15-1-2.
 */
public class TestFileUtils {
    public final static String tempDir = System.getProperty("java.io.tmpdir");

    private TestFileUtils() {
    }

    public static File getTempFile(String relativeFileName) {
        return new File(tempDir + "/" + relativeFileName);
    }

    public static String getAbsolutePath(String fileName) {
        File file = getFile(fileName);
        return file == null ? null : file.getAbsolutePath();
    }

    public static File getFile(String relativeFileName) {
        File file = new File("src/test/resources/" + relativeFileName);
        if (file.exists()) {
            return file;
        } else {
            return ClasspathFileUtils.getFile(relativeFileName);
        }
    }

    public static String read(String fileName) throws IOException {
        File file = getFile(fileName);
        return FileUtils.readFileToString(file, "utf8");
    }
}
