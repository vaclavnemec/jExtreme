package jextreme.evolution.genetics;

import java.util.Objects;

/**
 *
 * @author Vaclav
 */
public class Range {
 
    private final double minValue;
    private final double maxValue;

    /**
     *
     * @param minValue
     * @param maxValue
     */
    public Range(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     *
     * @return
     */
    public double getMinValue() {
        return minValue;
    }

    /**
     *
     * @return
     */
    public double getMaxValue() {
        return maxValue;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "GeneDefinition{" + "minValue=" + minValue + ", maxValue=" + maxValue + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return Double.compare(range.minValue, minValue) == 0 &&
                Double.compare(range.maxValue, maxValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minValue, maxValue);
    }
}
