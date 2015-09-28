package com.qinyuan.lib.lang.time;

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
        return t.hour * 3600 + t.minute * 60 + t.second;
    }

    public static boolean isCrossed(TimePeriod p1, TimePeriod p2) {
        return TimeUtils.getSecondDiff(p1.start, p2.end) * TimeUtils.getSecondDiff(p1.end, p2.start) < 0;
    }
}
