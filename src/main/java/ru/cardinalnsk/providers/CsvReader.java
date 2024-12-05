package ru.cardinalnsk.providers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CsvReader {
    private final String filePath;

    public CsvReader(String filePath) {
        this.filePath = filePath;
    }

    public void readColumn(int columnId, Map<String, List<Integer>> indexMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] columns = parseCsvLine(line);
                if (columnId > columns.length) {
                    throw new IllegalArgumentException("Invalid column ID: " + columnId);
                }
                final String key = columns[columnId - 1].trim().toLowerCase();
                indexMap.computeIfAbsent(key, k -> new ArrayList<>()).add(Integer.valueOf(columns[0]));
            }
        }
    }

    private String[] parseCsvLine(String line) {
        final List<String> result = new ArrayList<>();
        final StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}
