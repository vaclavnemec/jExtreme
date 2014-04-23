package jextreme.functions;

import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class RotatedHyperEllipsoid extends AbstractTestFuntion {

	private final Genotype genotype;

    /**
     *
     * @param genotype
     */
    public RotatedHyperEllipsoid(final Genotype genotype) {
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
		final Double d1 = this.genotype.getGenes().get(0);
		final Double d2 = this.genotype.getGenes().get(1);
		return -(d1 * d1 + 2 * d2 * d2);
	}

}