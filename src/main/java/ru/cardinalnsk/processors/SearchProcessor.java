package ru.cardinalnsk.processors;

import ru.cardinalnsk.model.SearchResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchProcessor {

    public List<SearchResult> processInputFile(String inputFile, IndexBuilderProcessor indexBuilderProcessor) throws IOException {
        final List<SearchResult> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final long searchStart = System.currentTimeMillis();
                final List<Integer> matches = indexBuilderProcessor.search(line.trim());
                final long searchTime = System.currentTimeMillis() - searchStart;
                results.add(new SearchResult(line.trim(), matches, searchTime));
            }
        }
        return results;
    }
}
