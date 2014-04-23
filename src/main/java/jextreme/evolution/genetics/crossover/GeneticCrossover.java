/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genotype;

/**
 * @author Vaclav
 * 
 */
public interface GeneticCrossover {

    /**
     *
     * @param genotype1
     * @param genotype2
     * @return
     */
    Genotype[] apply(Genotype genotype1, Genotype genotype2);

}
