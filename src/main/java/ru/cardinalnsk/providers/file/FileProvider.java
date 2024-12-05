package ru.cardinalnsk.providers.file;

import java.io.IOException;

public interface FileProvider {

    default void writeOutputFile(String outputFile, Object content) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
