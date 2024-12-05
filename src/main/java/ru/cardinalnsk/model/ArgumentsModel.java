package ru.cardinalnsk.model;

public class ArgumentsModel {

    private final String dataFile;
    private final String inputFile;
    private final String outputFile;
    private final int columnId;
    private final boolean debug;

    private ArgumentsModel(String dataFile, String inputFile, String outputFile, int columnId, boolean debug) {
        this.dataFile = dataFile;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.columnId = columnId;
        this.debug = debug;
    }

    public String getDataFile() {
        return dataFile;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public int getColumnId() {
        return columnId;
    }

    @Override
    public String toString() {
        return "ArgumentsModel{" +
                "dataFile='" + dataFile + '\'' +
                ", inputFile='" + inputFile + '\'' +
                ", outputFile='" + outputFile + '\'' +
                ", columnId=" + columnId +
                '}';
    }

    public boolean isDebug() {
        return debug;
    }

    public static class Builder {

        private String dataFile;
        private String inputFile;
        private String outputFile;
        private int columnId;
        private boolean debug;

        public Builder dataFile(String dataFile) {
            this.dataFile = dataFile;
            return this;
        }

        public Builder inputFile(String inputFile) {
            this.inputFile = inputFile;
            return this;
        }

        public Builder outputFile(String outputFile) {
            this.outputFile = outputFile;
            return this;
        }

        public Builder columnId(int columnId) {
            this.columnId = columnId;
            return this;
        }

        public Builder debug(Boolean debug) {
            this.debug = debug;
            return this;
        }

        public ArgumentsModel build() {
            validate(dataFile, "--data", "Data file is null or empty, use '--data' to specify data file");
            validate(inputFile, "--input-file", "Input file is null or empty, use '--input-file' to specify search input file");
            validate(outputFile, "--output-file", "Output file is null or empty, use '--output-file' to specify search output JSON file");

            if (columnId <= 0) {
                throw new IllegalArgumentException("Column id must be greater than 0, use '--indexed-column-id' to specify search column id");
            }
            return new ArgumentsModel(dataFile, inputFile, outputFile, columnId, debug);
        }

        private void validate(String value, String argumentName, String errorMessage) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException(errorMessage + " [" + argumentName + "]");
            }
        }
    }
}
