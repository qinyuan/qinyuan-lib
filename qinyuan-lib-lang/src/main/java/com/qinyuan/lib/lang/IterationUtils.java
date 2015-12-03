package com.qinyuan.lib.lang;

public class IterationUtils {
    public static void repeat(int times, Repeatable repeatable) {
        for (int i = 0; i < times; i++) {
            repeatable.run();
        }
    }

    public static interface Repeatable {
        void run();
    }
}
