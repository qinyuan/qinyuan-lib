package com.qinyuan.lib.lang.time;

import java.text.DecimalFormat;

public class Time {
    private final static DecimalFormat FORMAT = new DecimalFormat("00");
    public final int hour;
    public final int minute;
    public final int second;

    public Time(int hour, int minute, int second) {
        if (!(hour >= 0 && hour <= 23)) {
            throw new IllegalArgumentException("hour must between 0 and 23");
        } else if (!(minute >= 0 && minute <= 59)) {
            throw new IllegalArgumentException("minute must between 0 and 59");
        } else if (!(second >= 0 && second <= 59)) {
            throw new IllegalArgumentException("second must between 0 and 59");
        }

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public String toString() {
        return FORMAT.format(hour) + ":" + FORMAT.format(minute) + ":" + FORMAT.format(second);
    }
}
