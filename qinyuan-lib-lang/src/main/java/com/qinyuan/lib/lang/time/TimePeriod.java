package com.qinyuan.lib.lang.time;

public class TimePeriod {
    public final Time start;
    public final Time end;

    public TimePeriod(Time start, Time end) {
        if (TimeUtils.getSecondDiff(start, end) < 0) {
            throw new RuntimeException("start time shouldn't be later than end time");
        }

        this.start = start;
        this.end = end;
    }

    /**
     * @return how many seconds this period lasts
     */
    public int getSeconds() {
        return TimeUtils.getSecondDiff(start, end);
    }
}
