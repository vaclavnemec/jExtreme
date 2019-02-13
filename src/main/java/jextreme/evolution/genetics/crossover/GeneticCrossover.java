/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genes;

/**
 * Genetic crossover provides a way to combine (cross-over) genes.
 *
 * @author Vaclav
 */
public interface GeneticCrossover {

    /**
     * @param genes1 the first genes provided
     * @param genes2 the second genes provided
     * @return the crossed over genes
     */
    Genes[] apply(Genes genes1, Genes genes2);

}
