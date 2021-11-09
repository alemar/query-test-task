package org.query.calc;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class CartesianProductTable {
    private final TreeMap<BigDecimal, AccumulatedRow> rows = new TreeMap<>(Collections.reverseOrder());
    BigDecimal accumulatedSum = BigDecimal.ZERO;

    private Table table1;
    private Table table2;

    public CartesianProductTable(Table table1, Table table2) {
        this.table1 = table1;
        this.table2 = table2;
    }

    public BigDecimal getMultiplier(BigDecimal key) {
        return Optional.ofNullable(rows.ceilingEntry(key))
                .map(entry -> entry.getValue().getAccumulatedValue())
                .orElse(accumulatedSum);
    }

    public void populate() throws IOException {
        table1.populate();
        table2.populate();

//        table1.getRows().forEach(row1 -> {
//            table2.getRows().forEach(row2 -> {
//                BigDecimal key = row1.getFirst().add(row2.getFirst());
//                BigDecimal value = row1.getSecond().multiply(row2.getSecond());
//                rows.computeIfAbsent(key, AccumulatedRow::new).accumulateValue(value);
//            });
//        });

        Map<BigDecimal, AccumulatedRow> map = new ConcurrentHashMap<>();
        //use parallel stream
        table1.getRows().stream().parallel().forEach(row1 -> {
            table2.getRows().forEach(row2 -> {
                BigDecimal key = row1.getFirst().add(row2.getFirst());
                BigDecimal value = row1.getSecond().multiply(row2.getSecond());
                map.computeIfAbsent(key, AccumulatedRow::new).accumulateValue(value);
            });
        });
        rows.putAll(map);

        for (AccumulatedRow row : rows.values()) {
            BigDecimal accumulatedValue = row.getAccumulatedValue();
            row.setAccumulatedValue(accumulatedSum);
            accumulatedSum = accumulatedSum.add(accumulatedValue);
        }

        table1 = null;
        table2 = null;
    }
}
