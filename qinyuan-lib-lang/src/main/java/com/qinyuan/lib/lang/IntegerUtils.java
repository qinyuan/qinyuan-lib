package com.qinyuan.lib.lang;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Collections;
import java.util.List;

/**
 * Utility class for integer
 * Created by qinyuan on 15-2-27.
 */
public class IntegerUtils {
    public static boolean isPositive(Integer integer) {
        return integer != null && integer > 0;
    }

    public static boolean isPositive(String string) {
        return NumberUtils.isNumber(string) && isPositive(NumberUtils.toInt(string));
    }

    public static boolean isNotNegative(Integer integer) {
        return integer != null && integer >= 0;
    }

    public static boolean isNotNegative(String string) {
        return NumberUtils.isNumber(string) && isNotNegative(NumberUtils.toInt(string));
    }

    public static int getMaxContinuationSize(Iterable<Integer> integers) {
        List<Integer> list = Lists.newArrayList(integers);
        Collections.sort(list);

        Integer lastValue = null;
        int maxContinuationSize = 1, currentContinuationSize = 1;
        for (Integer value : list) {
            if (value == null) {
                continue;
            }

            if (lastValue != null) {
                if (value - lastValue == 1) {
                    currentContinuationSize++;
                    if (currentContinuationSize > maxContinuationSize) {
                        maxContinuationSize = currentContinuationSize;
                    }
                } else if (value - lastValue > 1) {
                    currentContinuationSize = 1;
                }
            }
            lastValue = value;
        }

        return maxContinuationSize;
    }

    /**
     * validate if two number is in opposite direction
     *
     * @param n1 first number
     * @param n2 first number
     * @return true if one number is negative and the other is positive
     */
    public static boolean inOppositeDirection(int n1, int n2) {
        return (n1 < 0 && n2 > 0) || (n1 > 0 && n2 < 0);
    }
}
