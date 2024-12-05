package ru.cardinalnsk.processors;

import ru.cardinalnsk.providers.CsvReader;

import java.io.IOException;
import java.util.*;

public class IndexBuilderProcessor {
    private final TreeMap<String, List<Integer>> indexMap = new TreeMap<>();

    public void buildIndex(String filePath, int columnId) throws IOException {
        final CsvReader csvReader = new CsvReader(filePath);
        csvReader.readColumn(columnId, indexMap);
    }

    public List<Integer> search(String prefix) {
        final String lowerPrefix = prefix.toLowerCase();
        final String nextPrefix = getNextPrefix(lowerPrefix);

        // Так как treemap отсортирована, берем только диапазон который подходит под наш префикс и работаем с ним
        final NavigableMap<String, List<Integer>> subMap = indexMap.subMap(lowerPrefix, true, nextPrefix, false);

        final List<Integer> results = new ArrayList<>();
        for (List<Integer> value : subMap.values()) {
            results.addAll(value);
        }

        // Перебираем всю map
//        for (Map.Entry<String, List<Integer>> entry : indexMap.entrySet()) {
//            if (entry.getKey().startsWith(lowerPrefix)) {
//                results.addAll(entry.getValue());
//            }
//        }
        results.sort(Comparator.naturalOrder());
        return results;
    }

    private String getNextPrefix(String prefix) {
        char[] chars = prefix.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != Character.MAX_VALUE) {
                chars[i]++;
                return new String(chars, 0, i + 1);
            }
        }
        return prefix;
    }
}
