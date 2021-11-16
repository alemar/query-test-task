package org.query.calc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class MainTable extends Table {
    int index = 0;
    private double[] keys;

    MainTable(Path path) throws IOException {
        super(path);
    }

    @Override
    protected void initRows(int size) {
        super.initRows(size);
        keys = new double[size];
    }

    @Override
    protected void populate() throws IOException {
        super.populate();
        keys = Arrays.stream(keys).distinct().toArray();
    }

    @Override
    protected void processLine(double key, double value) {
        super.processLine(key, value);
        keys[index++] = key;
    }

    Stream<Row> getRowsStream() {
        return Arrays.stream(keys).mapToObj(rows::get);
    }

    public double[] getKeys() {
        return keys;
    }
}
