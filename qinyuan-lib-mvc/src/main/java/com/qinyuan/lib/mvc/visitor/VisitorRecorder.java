package com.qinyuan.lib.mvc.visitor;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class VisitorRecorder {
    public final static int DEFAULT_SIZE = 1000;
    private Queue<VisitRecord> records = new LinkedList<>();
    private int size = DEFAULT_SIZE;

    public void setSize(int size) {
        this.size = size;
    }

    public void add(String ip, String time, String userAgent, String url) {
        add(new VisitRecord(ip, time, userAgent, url));
    }

    private synchronized void add(VisitRecord visitRecord) {
        records.add(visitRecord);
        while (records.size() > 0 && records.size() > size) {
            records.poll();
        }
    }

    public List<VisitRecord> getRecords() {
        return Lists.newArrayList(records);
    }
}
