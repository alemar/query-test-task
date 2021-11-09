package org.query.calc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class Table {
    private final BufferedReader reader;
    private static final String DELIMITER = " ";
    private List<Row> rows;

    Table(Path path) throws IOException {
        reader = getReader(path);
    }

    void populate() throws IOException {
        int size = readSize(reader);
        initRows(size);
        for (int i = 0; i < size; i++) {
            String[] split = reader.readLine().split(DELIMITER);
            processLine(new BigDecimal(split[0]), new BigDecimal(split[1]));
        }
        reader.close();
    }

    protected void initRows(int size){
        rows = new ArrayList<>(size);
    }

    protected void processLine(BigDecimal first, BigDecimal second) {
        rows.add(new Row(first, second));
    }

    protected List<Row> getRows() {
        return rows;
    }

    private int readSize(BufferedReader reader) throws IOException {
        return Integer.parseInt(reader.readLine());
    }

    private BufferedReader getReader(Path path) throws FileNotFoundException {
        return new BufferedReader(new FileReader(path.toFile()));
    }
}
