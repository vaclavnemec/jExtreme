package jextreme.evolution.genetics;

import jextreme.evolution.solution.Specimen;

/**
 *
 * @author Vaclav
 */
public interface Mutation {

    /**
     *
     * @param genes
     * @param specimen
     * @return
     */
    Genes mutate(Genes genes, Specimen specimen);

}
