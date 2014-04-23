package jextreme.evolution.genetics;

import jextreme.evolution.solution.Specimen;

/**
 *
 * @author Vaclav
 */
public interface Mutation {

    /**
     *
     * @param genotype
     * @param specimen
     * @return
     */
    Genotype mutate(Genotype genotype, Specimen specimen);

}
