package ru.cardinalnsk.providers.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ObjectFileWriter implements FileProvider {
    @Override
    public void writeOutputFile(String outputFile, Object content) throws IOException  {
        try (Writer writer = new FileWriter(outputFile)) {
            writer.write(content.toString());
        }
    }
}
