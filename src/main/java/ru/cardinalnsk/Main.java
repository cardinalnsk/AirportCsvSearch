package ru.cardinalnsk;

import ru.cardinalnsk.model.ArgumentsModel;
import ru.cardinalnsk.model.JsonReport;
import ru.cardinalnsk.model.SearchResult;
import ru.cardinalnsk.processors.IndexBuilderProcessor;
import ru.cardinalnsk.processors.SearchProcessor;
import ru.cardinalnsk.providers.arguments.ArgumentParser;
import ru.cardinalnsk.providers.arguments.impl.JvmArgumentParser;
import ru.cardinalnsk.providers.file.FileProvider;
import ru.cardinalnsk.providers.file.ObjectFileWriter;

import java.util.List;
import java.util.logging.*;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String HELP_MESSAGE = "\n\nRequired arguments:\n--data airports.csv — путь до файла csv с аэропортами,\n" +
            "--indexed-column-id 3 — колонка, по которой происходит поиск,\n" +
            "--input-file input-path-to-file.txt — путь до файла с входными строками поиска. " +
            "Формат — обычный текст. Каждая строка — текст, по которому программа должна выполнить поиск\n" +
            "--output-file output-path-to-file.json — путь до файла с результатами поиска.";

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelpAndExit();
        }

        final ArgumentParser argumentParser = new JvmArgumentParser(args);
        final ArgumentsModel arguments = argumentParser.getArguments();
        if (arguments == null) {
            printHelpAndExit();
        }

        try {
            final long startTime = System.currentTimeMillis();

            final IndexBuilderProcessor indexBuilderProcessor = new IndexBuilderProcessor();
            indexBuilderProcessor.buildIndex(arguments.getDataFile(), arguments.getColumnId());
            final long initTime = System.currentTimeMillis() - startTime;

            final SearchProcessor processor = new SearchProcessor();
            final List<SearchResult> results = processor.processInputFile(arguments.getInputFile(), indexBuilderProcessor);

            final JsonReport report = new JsonReport(initTime, results);
            final FileProvider fileProvider = new ObjectFileWriter();
            fileProvider.writeOutputFile(arguments.getOutputFile(), report);

            LOGGER.info("Application completed successfully.");
            LOGGER.info(() -> String.format("Initialization time: %d ms%n", initTime));
            if (arguments.isDebug()) {
                LOGGER.info(report.toString());
            }
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
        }

    }


    private static void printHelpAndExit() {
        LOGGER.severe(HELP_MESSAGE);
        System.exit(1);
    }

}
