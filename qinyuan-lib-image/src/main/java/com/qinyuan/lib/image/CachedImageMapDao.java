package com.qinyuan.lib.image;

import com.qinyuan.lib.lang.Cache;

import java.util.ArrayList;
import java.util.List;

/**
 * ImageMapDao with cache
 * Created by qinyuan on 16-3-23.
 */
public class CachedImageMapDao extends ImageMapDao {
    private final static Cache CACHE = new Cache();

    public CachedImageMapDao(String relateType) {
        super(relateType);
    }

    public void clearCache() {
        CACHE.deleteValue(relateType);
    }

    @Override
    public Integer add(Integer relateId, Integer xStart, Integer yStart, Integer xEnd, Integer yEnd,
                       String href, String comment) {
        clearCache();
        return super.add(relateId, xStart, yStart, xEnd, yEnd, href, comment);
    }

    @Override
    public ImageMap getInstance(Integer id) {
        if (id == null) {
            return null;
        }

        for (ImageMap map : getInstances()) {
            if (id.equals(map.getId())) {
                return map;
            }
        }

        return null;
    }

    @Override
    public void update(Integer id, String href, String comment) {
        clearCache();
        super.update(id, href, comment);
    }

    @Override
    public void delete(Integer id) {
        clearCache();
        super.delete(id);
    }

    @Override
    public void deleteByRelateId(Integer relateId) {
        clearCache();
        super.deleteByRelateId(relateId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ImageMap> getInstances() {
        return (List) CACHE.getValue(relateType, new Cache.Source() {
            @Override
            public Object getValue() {
                return oldGetInstances();
            }
        });
    }

    @Override
    public List<ImageMap> getInstancesByRelateId(Integer relateId) {
        List<ImageMap> result = new ArrayList<>();
        if (relateId == null) {
            return result;
        }

        for (ImageMap imageMap : getInstances()) {
            if (relateId.equals(imageMap.getRelateId())) {
                result.add(imageMap);
            }
        }
        return result;
    }

    private List<ImageMap> oldGetInstances() {
        return super.getInstances();
    }

}
