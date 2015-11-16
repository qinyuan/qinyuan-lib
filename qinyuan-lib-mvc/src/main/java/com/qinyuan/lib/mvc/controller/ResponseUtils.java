package com.qinyuan.lib.mvc.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class ResponseUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(ResponseUtils.class);

    private ResponseUtils() {
    }

    /**
     * read local image file, then output it to client
     * @param response http servlet response object
     * @param imagePath local image path
     */
    public static void outputImage(HttpServletResponse response, String imagePath) {
        try {
            File file = new File(imagePath);
            if (!file.isFile()) {
                return;
            }

            response.setContentType("image/jpeg;charset=UTF8");

            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(output);

            byte data[] = new byte[4096];
            int size;
            while ((size = bis.read(data)) != -1) {
                bos.write(data, 0, size);
            }

            bis.close();
            is.close();
            bos.flush();
            bos.close();
            output.close();
        } catch (Exception e) {
            LOGGER.error("fail to print image,image path: {}, info: {}", imagePath, e);
        }
    }
}
