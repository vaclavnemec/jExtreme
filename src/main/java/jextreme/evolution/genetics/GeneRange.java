package jextreme.evolution.genetics;

import java.util.Objects;

/**
 *
 * @author Vaclav
 */
public class GeneRange {
 
    private final Double minValue;
    private final Double maxValue;

    /**
     *
     * @param minValue
     * @param maxValue
     */
    public GeneRange(Double minValue, Double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     *
     * @return
     */
    public Double getMinValue() {
        return minValue;
    }

    /**
     *
     * @return
     */
    public Double getMaxValue() {
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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.minValue);
        hash = 29 * hash + Objects.hashCode(this.maxValue);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeneRange other = (GeneRange) obj;
        if (!Objects.equals(this.minValue, other.minValue)) {
            return false;
        }
        if (!Objects.equals(this.maxValue, other.maxValue)) {
            return false;
        }
        return true;
    }

}
