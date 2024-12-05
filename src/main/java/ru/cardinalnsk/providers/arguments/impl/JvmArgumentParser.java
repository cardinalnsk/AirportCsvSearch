package ru.cardinalnsk.providers.arguments.impl;

import ru.cardinalnsk.model.ArgumentsModel;
import ru.cardinalnsk.providers.arguments.ArgumentParser;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class JvmArgumentParser implements ArgumentParser {
    private static final String DATA_FILE_ARGUMENT = "--data";
    private static final String INPUT_FILE_ARGUMENT = "--input-file";
    private static final String OUTPUT_FILE_ARGUMENT = "--output-file";
    private static final String COLUMN_ID_ARGUMENT = "--indexed-column-id";
    private static final String DEBUG_ARGUMENT = "--debug";

    private final Map<String, Consumer<String>> argumentHandlers = new HashMap<>();
    private final ArgumentsModel.Builder argumentsBuilder = new ArgumentsModel.Builder();
    private final String[] args;

    public JvmArgumentParser(String[] args) {
        this.args = args;

        argumentHandlers.put(DATA_FILE_ARGUMENT, argumentsBuilder::dataFile);
        argumentHandlers.put(INPUT_FILE_ARGUMENT, argumentsBuilder::inputFile);
        argumentHandlers.put(OUTPUT_FILE_ARGUMENT, argumentsBuilder::outputFile);
        argumentHandlers.put(COLUMN_ID_ARGUMENT, value -> {
            try {
                argumentsBuilder.columnId(Integer.parseInt(value));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid column id: " + value);
            }
        });
        argumentHandlers.put(DEBUG_ARGUMENT, value -> argumentsBuilder.debug(Boolean.valueOf(value)));

    }

    public ArgumentsModel getArguments() {
        for (int i = 0; i < args.length; i++) {
            final String argument = args[i];
            if (argumentHandlers.containsKey(argument)) {
                if (i + 1 < args.length) {
                    argumentHandlers.get(argument).accept(args[i + 1]);
                    i++;
                } else {
                    throw new IllegalArgumentException("Argument " + argument + " is missing");
                }
            } else {
                throw new IllegalArgumentException("Unknown argument: " + argument);
            }
        }

        try {
            return argumentsBuilder.build();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
