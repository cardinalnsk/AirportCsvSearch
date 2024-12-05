package ru.cardinalnsk.model;

import java.util.List;

public class JsonReport {
    private final long initTime;
    private final List<SearchResult> result;

    public JsonReport(long initTime, List<SearchResult> result) {
        this.initTime = initTime;
        this.result = result;
    }

    @Override
    public String toString() {
        return "{\n" + "\"initTime\":" + initTime + ",\n" +
                "\"result\":" + result + "\n}";
    }
}
