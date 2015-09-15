package com.qinyuan.lib.image;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Class about Suffix of Thumbnail
 * Created by qinyuan on 15-3-18.
 */
public class ThumbnailSuffix {
    private ThumbnailSuffix() {
    }

    private static List<String> DEFAULT_SUFFIXES = Lists.newArrayList(
            "_thumbnail_small",
            "_thumbnail_middle",
            "_thumbnail_large"
    );

    public final static String SMALL = DEFAULT_SUFFIXES.get(0);
    public final static String MIDDLE = DEFAULT_SUFFIXES.get(1);
    public final static String LARGE = DEFAULT_SUFFIXES.get(2);

    public static List<String> getDefaultSuffixes() {
        return new ArrayList<>(DEFAULT_SUFFIXES);
    }
}
