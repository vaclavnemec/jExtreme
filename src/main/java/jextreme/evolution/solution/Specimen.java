package jextreme.evolution.solution;

import jextreme.evolution.genetics.Range;

/**
 * @author Vaclav
 */
public class Specimen {

    private final Range[] ranges;

    /**
     * @return
     */
    @Override
    public String toString() {
        return this.ranges.toString();
    }

    /**
     * @param ranges
     */
    public Specimen(final Range[] ranges) {
        this.ranges = ranges;
    }

    /**
     * @return the genesRanges
     */
    public Range[] getRanges() {
        return this.ranges;
    }
}
