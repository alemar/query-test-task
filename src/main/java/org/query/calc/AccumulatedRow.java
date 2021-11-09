package org.query.calc;

import java.math.BigDecimal;

class AccumulatedRow {
    private final BigDecimal key;
    private volatile BigDecimal accumulatedValue = BigDecimal.ZERO;

    public AccumulatedRow(BigDecimal key) {
        this.key = key;
    }

    public BigDecimal getKey() {
        return key;
    }

    synchronized void accumulateValue(BigDecimal value) {
        accumulatedValue = accumulatedValue.add(value);
    }

    BigDecimal getAccumulatedValue() {
        return accumulatedValue;
    }

    void setAccumulatedValue(BigDecimal accumulatedValue) {
        this.accumulatedValue = accumulatedValue;
    }
}
