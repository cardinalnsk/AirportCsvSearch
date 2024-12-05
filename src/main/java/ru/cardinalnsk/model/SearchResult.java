package ru.cardinalnsk.model;

import java.util.List;
import java.util.stream.Collectors;

public class SearchResult {
    private final String searchText;
    private final List<Integer> result;
    private final long time;

    public SearchResult(String searchText, List<Integer> result, long time) {
        this.searchText = searchText;
        this.result = result;
        this.time = time;
    }


    @Override
    public String toString() {
        StringBuilder json = new StringBuilder("{\n ");
        json.append("\"search\":\"").append(searchText).append("\",\n ");
        final String resultCollection = result
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(",", "[", "]"));
        json.append("\"result\":").append(resultCollection);
        json.append(",\n ");
        json.append("\"time\":").append(time);
        json.append("\n }");

        return json.toString();
    }
}
