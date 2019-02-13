package jextreme.evolution.solution;

import jextreme.evolution.genetics.Range;

import java.util.Arrays;

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
        return Arrays.toString(this.ranges);
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
