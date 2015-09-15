package com.qinyuan.lib.image;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Class about image thumbnail
 * Created by qinyuan on 15-3-10.
 */
public class ThumbnailBuilder {
    private final static Logger LOGGER = LoggerFactory.getLogger(ThumbnailBuilder.class);

    public String buildSmall(String imagePath) {
        return build(imagePath, ThumbnailSuffix.SMALL, ImageSize.SMALL);
    }

    public String buildMiddle(String imagePath) {
        return build(imagePath, ThumbnailSuffix.MIDDLE, ImageSize.MIDDLE);
    }

    public String build(String imagePath, String suffix, ImageSize size) {
        if (StringUtils.isBlank(suffix)) {
            throw new IllegalArgumentException("suffix can't be blank");
        }
        String path = getPath(imagePath, suffix);
        return buildThumbnail(imagePath, path, size);
    }

    private String buildThumbnail(String imagePath, String targetPath, ImageSize size) {
        if (new File(imagePath).isFile()) {
            try {
                ImageCompressor compressor = new ImageCompressor(imagePath);
                compressor.compress(targetPath, size);
            } catch (Exception e) {
                LOGGER.error("fail to compress image {}: {}", imagePath, e);
            }
        }
        return targetPath;
    }

    public String getSmall(String imagePath) {
        String path = getPath(imagePath, ThumbnailSuffix.SMALL);
        if (new File(path).isFile()) {
            return path;
        } else {
            return buildSmall(imagePath);
        }
    }

    public String getMiddle(String imagePath) {
        String path = getPath(imagePath, ThumbnailSuffix.MIDDLE);
        if (new File(path).isFile()) {
            return path;
        } else {
            return buildMiddle(imagePath);
        }
    }

    private String getPath(String imagePath, String suffix) {
        String imageName = new File(imagePath).getName();
        if (!imageName.contains(".")) {
            return imagePath + suffix;
        }

        int extendNameIndex = imagePath.lastIndexOf('.');
        if (extendNameIndex >= 0) {
            return imagePath.substring(0, extendNameIndex) + suffix +
                    imagePath.substring(extendNameIndex);
        } else {
            return imagePath + suffix;
        }
    }
}
