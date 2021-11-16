package org.query.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class Table {
    private static final String DELIMITER = " ";
    private final BufferedReader reader;
    protected Map<Double, Row> rows;

    Table(Path path) throws IOException {
        reader = getReader(path);
    }

    protected void populate() throws IOException {
        int size = readSize(reader);
        initRows(size);
        for (int i = 0; i < size; i++) {
            String[] split = reader.readLine().split(DELIMITER);
            processLine(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        }
        reader.close();
    }

    protected void initRows(int size){
        rows = new HashMap<>(size);
    }

    protected void processLine(double key, double value) {
        Row row = rows.get(key);
        if (row == null) {
            row = new Row(key, value);
            rows.put(key, row);
        } else {
            row.accumulateValue(value);
        }
    }

    protected Map<Double, Row> getRows() {
        return rows;
    }

    private int readSize(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private BufferedReader getReader(Path path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(path.toFile()));
    }
}
