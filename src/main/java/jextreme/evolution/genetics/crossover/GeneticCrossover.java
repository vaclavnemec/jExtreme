/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genes;

/**
 * @author Vaclav
 * 
 */
public interface GeneticCrossover {

    /**
     *
     * @param genes1
     * @param genes2
     * @return
     */
    Genes[] apply(Genes genes1, Genes genes2);

}
