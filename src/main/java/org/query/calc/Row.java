package org.query.calc;

class Row implements Comparable<Row> {
    private final double key;
    private volatile double accumulatedValue = 0;

    public Row(double key) {
        this.key = key;
    }

    public Row(double key, double accumulatedValue) {
        this.key = key;
        this.accumulatedValue = accumulatedValue;
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

    @Override
    public int compareTo(Row o) {
        return Double.compare(o.getAccumulatedValue(), accumulatedValue);
    }

    @Override
    public String toString() {
        return key + " " + accumulatedValue;
    }
}
