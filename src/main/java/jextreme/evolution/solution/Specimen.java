package jextreme.evolution.solution;

import java.util.Collections;
import java.util.List;
import jextreme.evolution.genetics.GeneDefinition;

/**
 * @author Vaclav
 */
public class Specimen {

    private final List<GeneDefinition> geneDefinitions;

    /**
     * @return
     */
    @Override
    public String toString() {
        return this.geneDefinitions.toString();
    }

    /**
     * @param geneRanges
     */
    public Specimen(final List<GeneDefinition> geneRanges) {
        this.geneDefinitions = Collections.unmodifiableList(geneRanges);
    }

    /**
     * @return the genesRanges
     */
    public List<GeneDefinition> getGeneDefinitions() {
        return this.geneDefinitions;
    }
}
