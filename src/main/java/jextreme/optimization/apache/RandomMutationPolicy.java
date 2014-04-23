package jextreme.optimization.apache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jextreme.evolution.genetics.GeneRange;
import jextreme.evolution.solution.SolutionFactory;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.random.RandomAdaptor;

/**
 *
 * @author Vaclav
 */
public class RandomMutationPolicy implements MutationPolicy {

	private final RandomAdaptor random;
	private final SolutionFactory solutionFactory;

    /**
     *
     * @param adaptor
     * @param solutionFactory
     */
    public RandomMutationPolicy(final RandomAdaptor adaptor, final SolutionFactory solutionFactory) {
		this.random = adaptor;
		this.solutionFactory = solutionFactory;
	}

	@Override
	public Chromosome mutate(final Chromosome original) throws MathIllegalArgumentException {
		final List<Double> genes = ((DoubleChromosome) original).getRepresentation();
		final List<Double> mutatedGenes = new ArrayList<>();
		final Iterator<GeneRange> defs = this.solutionFactory.getSpecimen().getGeneRanges().iterator();
		for (final Double gene : genes) {
			// 1/n probability to mutate gene
			if (this.random.nextInt(genes.size()) == 0) {
				mutatedGenes.add(this.mutateGene(defs.next()));
			} else {
				mutatedGenes.add(gene);
			}
		}
		return new DoubleChromosome(mutatedGenes, this.solutionFactory);
	}

    /**
     *
     * @param geneDefinition
     * @return
     */
    protected Double mutateGene(final GeneRange geneDefinition) {
		final Double mutatedGene = this.nextDouble(geneDefinition.getMinValue().doubleValue(), geneDefinition.getMaxValue().doubleValue());
		if (geneDefinition.getMinValue().compareTo(mutatedGene) > 0 || geneDefinition.getMaxValue().compareTo(mutatedGene) < 0) {
			throw new IllegalStateException();
		}
		return mutatedGene;
	}

    /**
     *
     * @param minValue
     * @param maxValue
     * @return
     */
    public double nextDouble(final double minValue, final double maxValue) {
		if (this.random.nextDouble() < 0.5) {
			return minValue + (maxValue - minValue) * (1 - this.random.nextDouble());
		} else {
			return minValue + (maxValue - minValue) * (this.random.nextDouble());
		}
	}

}