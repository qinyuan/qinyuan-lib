package com.qinyuan.lib.lang;

import java.util.*;

/**
 * Tool class about random
 * Created by qinyuan on 15-4-4.
 */
public class RandomUtils {
    private RandomUtils() {
    }

    /**
     * Get one instance from list randomly
     *
     * @param list list to fetch element
     * @param <T>  type of list element
     * @return one element of list
     */
    public static <T> T getOne(List<? extends T> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    /**
     * disorganize order of certain list randomly
     *
     * @param list list to disorganize order
     * @param <T>  type of list element
     * @return list whose order is disorganized
     */
    public static <T> List<T> disorganizeOrder(List<? extends T> list) {
        return subList(list, list.size(), false, false);
    }

    /**
     * create sub list by fetching elements of certain list randomly
     *
     * @param list       parent list to generate sub list
     * @param size       length of sub list
     * @param repeatable whether each element can be fetched more than twice
     * @param sorted     whether order of subList is the same as parent list
     * @param <T>        type of list element
     * @return sub list
     */
    public static <T> List<T> subList(List<? extends T> list, int size, boolean repeatable, boolean sorted) {
        if (!repeatable && size > list.size()) {
            throw new IllegalArgumentException(
                    "sub list size can't be greater than parent list size if not repeatable, "
                            + "parent list size is " + list.size() + " but sub list size is " + size
            );
        }

        List<T> newList = new ArrayList<>();
        for (int i : nextIntegers(0, list.size() - 1, size, repeatable, sorted)) {
            newList.add(list.get(i));
        }
        return newList;
    }


    public static <T> List<T> subList(List<? extends T> list, int size) {
        return subList(list, size, false, true);
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

        List<Integer> integers = new ArrayList<>();
        Set<Integer> integerRecords = new HashSet<>();

        Random rand = new Random();
        int range = upperBound - lowerBound + 1;

        if (size > range && !repeatable) {
            throw new IllegalArgumentException(
                    "it's impossible to create unrepeatable list when bound range is less than size");
        }

        while (integers.size() < size) {
            int next = lowerBound + rand.nextInt(range);

            if (!repeatable) {
                if (integerRecords.contains(next)) {
                    continue;
                } else {
                    integerRecords.add(next);
                }
            }

            integers.add(next);
        }

        if (sorted) {
            Collections.sort(integers);
        }

        return integers;
    }

    public static List<Integer> nextIntegers(int lowerBound, int upperBound, int size) {
        return nextIntegers(lowerBound, upperBound, size, false, true);
    }
}
