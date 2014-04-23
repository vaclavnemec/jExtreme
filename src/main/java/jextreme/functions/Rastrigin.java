package jextreme.functions;

import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class Rastrigin extends AbstractTestFuntion {

	private final Genotype genotype;

    /**
     *
     * @param genotype
     */
    public Rastrigin(final Genotype genotype) {
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
		final Double x = this.genotype.getGenes().get(0);
		final Double y = this.genotype.getGenes().get(1);
		return -(10 * 2 + (x * x - 10 * Math.cos(2 * Math.PI * x)) + (y * y - 10 * Math.cos(2 * Math.PI + y)));
	}

}