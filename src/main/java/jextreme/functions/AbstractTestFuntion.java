package jextreme.functions;

import jextreme.evolution.solution.Solution;
import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public abstract class AbstractTestFuntion implements Solution {

    /**
     *
     * @return
     */
    @Override
	public String toString() {
		return this.getFitness() + " " + this.getGenotype();
	}

    /**
     *
     * @return
     */
    public abstract Genotype getGenotype();
}
