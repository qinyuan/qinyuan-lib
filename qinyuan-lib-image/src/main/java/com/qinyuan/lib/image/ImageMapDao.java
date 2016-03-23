package com.qinyuan.lib.image;

import com.qinyuan.lib.database.hibernate.HibernateDeleter;
import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.database.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao of ImageMap
 * Created by qinyuan on 15-6-27.
 */
public class ImageMapDao {
    private final String relateType;

    public ImageMapDao(String relateType) {
        this.relateType = relateType;
    }

    public Integer add(Integer relateId, Integer xStart, Integer yStart, Integer xEnd, Integer yEnd,
                       String href, String comment) {
        ImageMap map = new ImageMap();
        map.setRelateType(this.relateType);
        map.setRelateId(relateId);
        map.setxStart(xStart);
        map.setyStart(yStart);
        map.setxEnd(xEnd);
        map.setyEnd(yEnd);
        map.setHref(href);
        map.setComment(comment);
        return HibernateUtils.save(map);
    }

    public ImageMap getInstance(Integer id) {
        ImageMap imageMap = HibernateUtils.get(ImageMap.class, id);
        return isTypeCorrect(imageMap) ? imageMap : null;
    }

    private boolean isTypeCorrect(ImageMap map) {
        return map != null && map.getRelateType().equals(relateType);
    }

    public void update(Integer id, String href, String comment) {
        ImageMap map = getInstance(id);
        if (isTypeCorrect(map)) {
            map.setHref(href);
            map.setComment(comment);
            HibernateUtils.update(map);
        }
    }

    private HibernateDeleter newHibernateDeleter() {
        return new HibernateDeleter().addEqualFilter("relateType", relateType);
    }

    public void delete(Integer id) {
        newHibernateDeleter().addEqualFilter("id", id).delete(ImageMap.class);
    }

    public void deleteByRelateId(Integer relateId) {
        newHibernateDeleter().addEqualFilter("relateId", relateId).delete(ImageMap.class);
    }

    private HibernateListBuilder newHibernateListBuilder() {
        return new HibernateListBuilder().addEqualFilter("relateType", relateType);
    }

    public List<ImageMap> getInstancesByRelateId(Integer relateId) {
        return newHibernateListBuilder().addEqualFilter("relateId", relateId)
                .addOrder("id", true).build(ImageMap.class);
    }

    public List<ImageMap> getInstances() {
        return newHibernateListBuilder().addOrder("relateId", true).addOrder("id", true).build(ImageMap.class);
    }

    public Map<Integer, List<ImageMap>> getInstancesAndGroupByRelateId() {
        Map<Integer, List<ImageMap>> map = new HashMap<>();
        for (ImageMap imageMap : getInstances()) {
            Integer relateId = imageMap.getRelateId();
            List<ImageMap> list = map.get(relateId);

            if (list == null) {
                list = new ArrayList<>();
                map.put(relateId, list);
            }

            list.add(imageMap);
        }
        return map;
    }
}
