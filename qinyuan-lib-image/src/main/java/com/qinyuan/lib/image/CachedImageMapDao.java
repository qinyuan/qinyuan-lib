package com.qinyuan.lib.image;

/**
 * ImageMapDao with cache
 * Created by qinyuan on 16-3-23.
 */
public class CachedImageMapDao extends ImageMapDao {
    public CachedImageMapDao(String relateType) {
        super(relateType);
    }
}
