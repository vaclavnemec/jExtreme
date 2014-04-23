package jextreme.functions;

import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class Rosenbrock extends AbstractTestFuntion {

	private final Genotype genotype;

    /**
     *
     * @param genotype
     */
    public Rosenbrock(final Genotype genotype) {
		this.genotype = genotype;
	}

    /**
     *
     * @return
     */
    @Override
	public Genotype getGenotype() {
		return this.genotype;
	}

    /**
     *
     * @return
     */
    @Override
	public Double getFitness() {
		final Double y = this.genotype.getGenes().get(0);
		final Double x = this.genotype.getGenes().get(1);
		return -(100 * (y - x * x) * (y - x * x) + (1 - x) * (1 - x));
	}

}