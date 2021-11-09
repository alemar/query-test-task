package org.query.calc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

class MainTable extends Table {
    private final Map<Double, AccumulatedRow> rows = new LinkedHashMap<>();

    public MainTable(Path path) throws IOException {
        super(path);
    }

    @Override
    protected void initRows(int size) {
        //do nothing
    }

    @Override
    protected void processLine(double first, double second) {
        rows.computeIfAbsent(first, AccumulatedRow::new).accumulateValue(second);
    }

    public Stream<AccumulatedRow> getRowsStream() {
        return rows.values().stream();
    }

}
