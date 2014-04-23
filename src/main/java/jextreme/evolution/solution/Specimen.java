package jextreme.evolution.solution;

import java.util.Collections;
import java.util.List;
import jextreme.evolution.genetics.GeneRange;

/**
 * @author Vaclav
 */
public class Specimen {

    private final List<GeneRange> genesRanges;

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return this.genesRanges.toString();
    }

    /**
     *
     * @param geneRanges
     */
    public Specimen(final List<GeneRange> geneRanges) {
        this.genesRanges = Collections.unmodifiableList(geneRanges);
    }

    /**
     * @return the genesRanges
     */
    public List<GeneRange> getGeneRanges() {
        return this.genesRanges;
    }
}
