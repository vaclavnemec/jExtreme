package jextreme.evolution.solution;

import jextreme.evolution.genetics.Range;

import java.util.Arrays;

/**
 * @author Vaclav
 */
public class Specimen {

    private final Range[] ranges;

    @Override
    public String toString() {
        return Arrays.toString(this.ranges);
    }

    /**
     * @param ranges the limits for the optimised function
     */
    public Specimen(final Range[] ranges) {
        this.ranges = ranges;
    }

    public Range[] getRanges() {
        return this.ranges;
    }
}
