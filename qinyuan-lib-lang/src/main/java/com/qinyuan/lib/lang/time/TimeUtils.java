package com.qinyuan.lib.lang.time;

import com.qinyuan.lib.lang.IntegerUtils;

public class TimeUtils {
    final static String TIME_PATTERN = "\\d{1,2}:\\d{1,2}:\\d{1,2}";

    public static boolean isTime(String time) {
        return time != null && time.matches("^" + TIME_PATTERN + "$");
    }

    /**
     * calculate how many seconds between two time
     *
     * @param t1 Time instance
     * @param t2 Time instance
     * @return positive value if t1 is earlier than t2
     */
    public static int getSecondDiff(Time t1, Time t2) {
        return getTotalSecond(t2) - getTotalSecond(t1);
    }

    private static int getTotalSecond(Time t) {
        return t.getHour() * 3600 + t.getMinute() * 60 + t.getSecond();
    }

    public static boolean isIntersected(TimePeriod p1, TimePeriod p2) {
        return !(p1 == null || p2 == null) && IntegerUtils.inOppositeDirection(
                TimeUtils.getSecondDiff(p1.start, p2.end), TimeUtils.getSecondDiff(p1.end, p2.start));
    }

    public static TimePeriod intersect(TimePeriod p1, TimePeriod p2) {
        if (!isIntersected(p1, p2)) {
            return null;
        }

        Time start = p1.start.isLaterThan(p2.start) ? p1.start : p2.start;
        Time end = p1.end.isEarlierThan(p2.end) ? p1.end : p2.end;
        return new TimePeriod(start, end);
    }
}
