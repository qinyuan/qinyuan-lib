package com.qinyuan.lib.lang;

import java.util.*;

/**
 * count the frequency of certain objects appear
 * Created by qinyuan on 15-9-11.
 */
public class FrequencyCounter {
    private final Map<Object, Integer> counter = new HashMap<>();
    private final HashSet<Object> excludeValues = new HashSet<>();

    /**
     * add the value that not to count
     *
     * @param excludeValue value that not to count
     */
    public synchronized void addExclude(Object excludeValue) {
        excludeValues.add(excludeValue);
    }

    public synchronized void add(Object value) {
        if (excludeValues.contains(value)) {
            return;
        }

        if (counter.containsKey(value)) {
            counter.put(value, counter.get(value) + 1);
        } else {
            counter.put(value, 1);
        }
    }

    public List<Object> top(int n) {
        Map<Integer, List<Object>> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1; // order by key descend
            }
        });
        for (Map.Entry<Object, Integer> entry : counter.entrySet()) {
            List<Object> list = map.get(entry.getValue());
            if (list == null) {
                list = new ArrayList<>();
                map.put(entry.getValue(), list);
            }

            list.add(entry.getKey());
        }

        List<Object> result = new ArrayList<>();
        for (Map.Entry<Integer, List<Object>> entry : map.entrySet()) {
            if (result.size() + entry.getValue().size() > n) {
                return result;
            } else {
                result.addAll(entry.getValue());
            }
        }
        return result;
    }
}
