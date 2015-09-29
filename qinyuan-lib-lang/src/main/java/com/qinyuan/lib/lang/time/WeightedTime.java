package com.qinyuan.lib.lang.time;

import org.apache.commons.lang3.tuple.Pair;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Different period of day has different weight,
 * and this class is used in this case
 * Created by qinyuan on 15-9-29.
 */
public class WeightedTime {
    private final Date startTime;
    private final Date endTime;

    private final List<Pair<TimePeriod, Integer>> weights = new ArrayList<>();

    public WeightedTime(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public WeightedTime(String startTime, String endTime) {
        this(DateUtils.newDate(startTime), DateUtils.newDate(endTime));
    }

    public WeightedTime addWeight(Time start, Time end, int weight) {
        // validate
        if (start == null) {
            throw new IllegalArgumentException("start shouldn't be null");
        } else if (end == null) {
            throw new IllegalArgumentException("end shouldn't be null");
        }

        TimePeriod period = new TimePeriod(start, end);

        // validate intersection
        for (Pair<TimePeriod, Integer> addedWeight : weights) {
            TimePeriod oldPeriod = addedWeight.getLeft();
            if (TimeUtils.isIntersected(period, oldPeriod)) {
                throw new IllegalArgumentException(period + " is intersected with old period " + oldPeriod);
            }
        }

        weights.add(Pair.of(period, weight));
        return this;
    }

    public WeightedTime clearWeight() {
        weights.clear();
        return this;
    }

    public long countSeconds() {
        if (startTime.getTime() > endTime.getTime()) {
            return 0;
        }

        int dayDiff = getDayDiff();
        if (dayDiff == 0) {
            return getWeightedSecondsInOneDay(new Time(startTime), new Time(endTime));
        }

        long seconds = 0;

        // calculate weighted seconds of whole day
        int wholeDayCount = DateUtils.getDayDiff(startTime, endTime) - 1;
        seconds += getWeightedSecondsOfOneDay() * wholeDayCount;

        // calculate weighted seconds of first day
        seconds += getWeightedSecondsInOneDay(new Time(startTime), new Time(24, 0, 0));

        // calculate weighted seconds of last day
        seconds += getWeightedSecondsInOneDay(new Time(0, 0, 0), new Time(endTime));

        return seconds;
    }

    private int getDayDiff() {
        Date startDate = DateUtils.newDate(DateUtils.getDatePart(startTime.toString()));
        Date endDate = DateUtils.newDate(DateUtils.getDatePart(endTime.toString()));
        return DateUtils.getDayDiff(startDate, endDate);
    }

    private long getWeightedSecondsOfOneDay() {
        long seconds = 3600 * 24; // seconds of one day

        for (Pair<TimePeriod, Integer> weight : weights) {
            int secondsInPeriod = weight.getLeft().getSeconds();
            seconds = seconds - secondsInPeriod + secondsInPeriod * weight.getRight();
        }

        return seconds;
    }

    private long getWeightedSecondsInOneDay(Time start, Time end) {
        TimePeriod fullPeriod = new TimePeriod(start, end);
        long seconds = fullPeriod.getSeconds();

        for (Pair<TimePeriod, Integer> weight : weights) {
            TimePeriod intersection = TimeUtils.intersect(weight.getLeft(), fullPeriod);
            if (intersection != null) {
                int intersectSeconds = intersection.getSeconds();
                seconds = seconds - intersectSeconds + intersectSeconds * weight.getRight();
            }
        }

        return seconds;
    }
}
