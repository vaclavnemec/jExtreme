/**
 * Aug 25, 2013
 */
package jextreme.evolution.solution;

import jextreme.evolution.genetics.Genotype;

/**
 * @author vaclavnemec
 * 
 */
public interface SolutionFactory {

    /**
     *
     * @param genotype
     * @return
     */
    public Solution createSolution(final Genotype genotype);

    /**
     *
     * @return
     */
    public Specimen getSpecimen();

}