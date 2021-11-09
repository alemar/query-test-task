package org.query.calc;

import java.math.BigDecimal;

public class Row implements Comparable<Row> {
    private final BigDecimal first;
    private final BigDecimal second;

    public Row(BigDecimal first, BigDecimal second) {
        this.first = first;
        this.second = second;
    }

    public BigDecimal getFirst() {
        return first;
    }

    public BigDecimal getSecond() {
        return second;
    }

    @Override
    public int compareTo(Row o) {
        return o.getSecond().compareTo(second);
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}
