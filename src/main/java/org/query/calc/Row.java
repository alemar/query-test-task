package org.query.calc;


public class Row implements Comparable<Row> {
    private final double first;
    private final double second;

    public Row(double first, double second) {
        this.first = first;
        this.second = second;
    }

    public double getFirst() {
        return first;
    }

    public double getSecond() {
        return second;
    }

    @Override
    public int compareTo(Row o) {
        return Double.compare(o.getSecond(), second);
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}
