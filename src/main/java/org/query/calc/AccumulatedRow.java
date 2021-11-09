package org.query.calc;

class AccumulatedRow {
    private final double key;
    private volatile double accumulatedValue = 0;

    public AccumulatedRow(double key) {
        this.key = key;
    }

    public double getKey() {
        return key;
    }

    synchronized void accumulateValue(double value) {
        accumulatedValue += value;
    }

    double getAccumulatedValue() {
        return accumulatedValue;
    }

    void setAccumulatedValue(double accumulatedValue) {
        this.accumulatedValue = accumulatedValue;
    }
}
