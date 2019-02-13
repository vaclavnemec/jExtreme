package jextreme.evolution.genetics;

import jextreme.evolution.solution.Specimen;

/**
 * @author Vaclav
 */
public interface Mutation {

    /**
     * @param genes the genes to be mutated
     * @param specimen the limits for the dimensions
     * @return mutated genes
     */
    Genes mutate(Genes genes, Specimen specimen);

}
