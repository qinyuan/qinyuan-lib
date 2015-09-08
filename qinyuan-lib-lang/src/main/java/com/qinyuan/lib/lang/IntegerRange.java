package com.qinyuan.lib.lang;

public class IntegerRange {
    private final int start;
    private final int end;

    public IntegerRange(int start, int end) {
        this.start = Math.min(start, end);
        this.end = Math.max(start, end);
    }

    public IntegerRange(String range) {
        if (!validate(range)) {
            throw new IllegalArgumentException("Invalid range: " + range);
        }
        String[] rangeArray = range.split(SEPARATOR);
        int value1 = Integer.parseInt(rangeArray[0]);
        int value2 = Integer.parseInt(rangeArray[1]);
        this.start = Math.min(value1, value2);
        this.end = Math.max(value1, value2);
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getDiff() {
        return end - start;
    }

    @Override
    public String toString() {
        return start + SEPARATOR + end;
    }

    public final static String SEPARATOR = "~";

    public static boolean validate(String range) {
        return range.matches("^\\d+~\\d+$");
    }
}
