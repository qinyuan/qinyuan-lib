package com.qinyuan.lib.lang.time;

public class TimePeriod {
    public final Time start;
    public final Time end;

    public TimePeriod(Time start, Time end) {
        if (start == null) {
            throw new IllegalArgumentException("start is null");
        } else if (end == null) {
            throw new IllegalArgumentException("end is null");
        } else if (TimeUtils.getSecondDiff(start, end) < 0) {
            throw new IllegalArgumentException("start time shouldn't be later than end time");
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

    @Override
    public String toString() {
        return start.toString() + "~" + end.toString();
    }
}
