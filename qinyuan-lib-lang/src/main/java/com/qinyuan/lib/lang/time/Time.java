package com.qinyuan.lib.lang.time;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class Time {
    private final static DecimalFormat FORMAT = new DecimalFormat("00");
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second) {
        init(hour, minute, second);
    }

    public Time(Date dateTime) {
        this(new SimpleDateFormat("HH:mm:ss").format(dateTime));
    }

    public Time(String time) {
        if (!TimeUtils.isTime(time)) {
            throw new IllegalArgumentException("invalid time string: " + time);
        }

        String[] arr = time.split("\\D+");
        init(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]));
    }

    private void init(int hour, int minute, int second) {
        if (hour != 24 || minute != 0 || second != 0) {   // 24:00:00 is valid
            if (!(hour >= 0 && hour <= 23)) {
                throw new IllegalArgumentException("hour must between 0 and 23, but real value is " + hour);
            } else if (!(minute >= 0 && minute <= 59)) {
                throw new IllegalArgumentException("minute must between 0 and 59, but real value is " + minute);
            } else if (!(second >= 0 && second <= 59)) {
                throw new IllegalArgumentException("second must between 0 and 59, but real value is " + second);
            }
        }

        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public boolean isLaterThan(Time other) {
        return other != null && TimeUtils.getSecondDiff(this, other) < 0;
    }

    public boolean isEarlierThan(Time other) {
        return other != null && TimeUtils.getSecondDiff(this, other) > 0;
    }

    @Override
    public String toString() {
        return FORMAT.format(hour) + ":" + FORMAT.format(minute) + ":" + FORMAT.format(second);
    }
}
