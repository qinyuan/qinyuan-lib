package com.qinyuan.lib.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Controller to save upload image from CKEditor
 * Created by qinyuan on 15-7-7.
 */
@Controller
public class CKEditorUploadImageController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CKEditorUploadImageController.class);
    private final static String CKEDITOR_SAVE_DIR = "ckeditor-upload";

    @RequestMapping("ckeditor-image-upload")
    public void index(HttpServletResponse response,
                      @RequestParam(value = "upload", required = true) MultipartFile upload) {
        try {
            PrintWriter out = response.getWriter();
            //out.print("<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(1,'http://www.qin-yuan.site:9080/ckeditor-upload/DuBbHCjXJptKQYfqbETO_LIhzodegAYHZpyfWEqXH_600.PNG','')</script>");
            if (upload.getSize() > 600 * 1024) {
                out.print(getResultContent("", "File must not greater than 600k"));
                return;
            }

            String saveFilePath = transferUploadFile(upload, CKEDITOR_SAVE_DIR);
            String imageUrl = pathToUrl(saveFilePath);
            out.print(getResultContent(imageUrl, ""));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("fail to handle ckeditor image upload, file: {}, info: {}", upload, e);
            throw new RuntimeException(e);
        }
    }

    private String getResultContent(String imageUrl, String prompt) {
        String callback = request.getParameter("CKEditorFuncNum");
        String content = "<script type=\"text/javascript\">";
        content += "window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageUrl + "','" + prompt + "')";
        content += "</script>";
        return content;
    }
}
