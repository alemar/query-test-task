package org.query.calc;

import java.io.IOException;
import java.util.*;

public class CartesianProductTable {
    private final Table table1;
    private final Table table2;

    public CartesianProductTable(Table table1, Table table2) {
        this.table1 = table1;
        this.table2 = table2;
    }

    public void populate() throws IOException {
        table1.populate();
        table2.populate();
    }

    public Map<Double, Row> groupByLevels(double[] keys) {
        double[] sortedLevels = Arrays.stream(keys).sorted().toArray();

        Collection<Row> rows1;
        TreeMap<Double, Row> rows2;
        if (table1.getRows().size() < table2.getRows().size()) {
            rows1 = table1.getRows().values();
            rows2 = new TreeMap<>(table2.getRows());
        } else {
            rows1 = table2.getRows().values();
            rows2 = new TreeMap<>(table1.getRows());
        }

        Map<Double, Row> result = new HashMap<>(sortedLevels.length);
        for (double level : sortedLevels) {
            result.put(level, new Row(level));
        }

        rows1.stream().parallel().forEach(row1 -> {
            //rows1.stream().forEach(row1 -> {
            int state = 0;
            int index = 0;
            double a = sortedLevels[0];
            double aNext;
            if (sortedLevels.length > 1) {
                aNext = sortedLevels[1];
            } else {
                aNext = a;
            }
            double b = row1.getKey();
            double y = row1.getAccumulatedValue();

            for (Row row2 : rows2.values()) {
                //row2 is sorted, this means that "b + c" should only increase
                double bPlusC = b + row2.getKey();

                if (state == 0) {
                    if (a >= bPlusC) {
                        //ignore levels that greater that b+C
                        continue;
                    } else {
                        state = 1;
                    }
                }
                double value = y * row2.getAccumulatedValue();

                if (state == 1) {
                    //looking for the maximum level that confirm the condition
                    while (aNext < bPlusC) {
                        index++;
                        a = aNext;
                        if (index >= sortedLevels.length) {
                            state = 2;
                            break;
                        }
                        aNext = sortedLevels[index];
                    }
                    if (state == 1) {
                        result.get(a).accumulateValue(value);
                    }
                }

                //all the following values will correspond to the last (maximum) level
                if (state == 2) {
                    result.get(a).accumulateValue(value);
                }
            }
        });

        double sum = 0;
        for (int i = sortedLevels.length - 1; i >= 0; i--) {
            Row row = result.get(sortedLevels[i]);
            double accumulatedValue = row.getAccumulatedValue();
            sum += accumulatedValue;
            row.setAccumulatedValue(sum);
        }

        return result;
    }
}
