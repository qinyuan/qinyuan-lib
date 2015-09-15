package com.qinyuan.lib.mvc.controller;

import com.qinyuan.lib.config.ImageConfig;
import com.qinyuan.lib.image.ImageDownloader;
import com.qinyuan.lib.image.ThumbnailSuffix;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Controller to deal with image
 * Created by qinyuan on 15-6-16.
 */
public class ImageController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageConfig imageConfig;

    /**
     * web frontend may post a image url or file, this method validate
     * the  url and upload file and return a local path
     *
     * @param imageUrl       image url posted
     * @param imageFile      image file uploaded
     * @param savePathPrefix save path prefix
     * @return save path
     * @throws java.io.IOException
     */
    protected String getSavePath(String imageUrl, MultipartFile imageFile, String savePathPrefix) throws IOException {
        if (isUploadFileNotEmpty(imageFile)) {
            /*
             * deal with upload file if it's not empty
             */
            return replaceBuildInString(transferUploadFile(imageFile, savePathPrefix));
        } else {
            /*
             * If upload file is empty, deal with image url
             */
            if (isLocalUrl(imageUrl)) {
                return new ImageUrlAdapter(imageConfig, getLocalHost()).urlToPath(imageUrl);
            } else {
                String filePath = replaceBuildInString(getImageDownloader().save(imageUrl));
                LOGGER.info("save upload image to {}", filePath);
                return filePath;
            }
        }
    }

    private ImageDownloader getImageDownloader() {
        return new ImageDownloader(imageConfig.getDirectory());
    }

    /**
     * transfer upload file to certain path
     *
     * @param uploadFile     upload file
     * @param savePathPrefix save path prefix
     * @return target file to transfer
     * @throws IOException
     */
    protected String transferUploadFile(MultipartFile uploadFile, String savePathPrefix) throws IOException {
        if (savePathPrefix == null) {
            savePathPrefix = "";
        } else if (!savePathPrefix.isEmpty() && !savePathPrefix.endsWith("/")) {
            savePathPrefix += "/";
        }

        String filePath = getImageDownloader().getSaveDir();
        if (!filePath.endsWith("/")) {
            filePath += "/";
        }
        filePath += savePathPrefix + RandomStringUtils.randomAlphabetic(20)
                + "_" + uploadFile.getOriginalFilename();
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            LOGGER.error("fail to create directory {}", parent.getAbsolutePath());
        }
        uploadFile.transferTo(file);
        LOGGER.info("save upload image to {}", filePath);
        return filePath;
    }

    private String replaceBuildInString(String path) throws IOException {
        if (!StringUtils.hasText(path)) {
            return path;
        }

        String oldPath = path;
        for (String suffix : ThumbnailSuffix.getDefaultSuffixes()) {
            if (path.contains(suffix)) {
                path = path.replace(suffix, RandomStringUtils.random(suffix.length()));
            }
        }
        if (!path.equals(oldPath)) {
            FileUtils.moveFile(new File(oldPath), new File(path));
        }
        return path;
    }

    public String pathToUrl(String path) {
        return new ImageUrlAdapter(imageConfig, getLocalHost()).pathToUrl(path);
    }

    private boolean isLocalUrl(String url) {
        return url.contains("://" + getLocalHost()) || url.contains("://" + getLocalAddress());
    }

    protected String getSavePath(String imageUrl, MultipartFile imageFile) throws IOException {
        return getSavePath(imageUrl, imageFile, "");
    }

    protected boolean isUploadFileEmpty(MultipartFile file) {
        return file == null || !StringUtils.hasText(file.getOriginalFilename())
                || file.getSize() <= 0;
    }

    protected boolean isUploadFileNotEmpty(MultipartFile file) {
        return !isUploadFileEmpty(file);
    }
}
