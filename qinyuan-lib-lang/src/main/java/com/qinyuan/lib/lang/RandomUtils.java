package com.qinyuan.lib.lang;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Tool class about random
 * Created by qinyuan on 15-4-4.
 */
public class RandomUtils {
    private RandomUtils() {
    }

    public static <T> List<T> subList(List<? extends T> list, int size) {
        if (size > list.size()) {
            return new ArrayList<>(list);
        }

        List<T> newList = new ArrayList<>();
        for (int i : nextIntegers(0, list.size() - 1, size)) {
            newList.add(list.get(i));
        }
        return newList;
    }

    /**
     * @param lowerBound lower bound of list element
     * @param upperBound upper bound of list element
     * @param size       size of list
     * @param repeatable whether elements can be repeatable
     * @param sorted     whether elements of list is sorted
     * @return list that contains integers between lowerBound and upperBound
     */
    public static List<Integer> nextIntegers(int lowerBound, int upperBound, int size, boolean repeatable,
                                             boolean sorted) {
        if (lowerBound > upperBound) {
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }

        Collection<Integer> collection;
        if (repeatable) {
            collection = new ArrayList<>();
        } else {
            collection = sorted ? new TreeSet<Integer>() : new HashSet<Integer>();
        }

        Random rand = new Random();
        int range = upperBound - lowerBound + 1;

        if (size > range && !repeatable) {
            throw new IllegalArgumentException(
                    "it's impossible to create unrepeatable list when bound range is less than size");
        }

        while (collection.size() < size) {
            collection.add(lowerBound + rand.nextInt(range));
        }

        if (collection instanceof List) {
            @SuppressWarnings("unchecked")
            List<Integer> list = (List) collection;
            Collections.sort(list);
            return list;
        } else {
            return Lists.newArrayList(collection);
        }
    }

    public static List<Integer> nextIntegers(int lowerBound, int upperBound, int size) {
        return nextIntegers(lowerBound, upperBound, size, false, true);
    }
}
