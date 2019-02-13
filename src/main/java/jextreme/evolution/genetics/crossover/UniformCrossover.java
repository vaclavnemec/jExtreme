package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genes;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

/**
 * @author Vaclav
 */
public class UniformCrossover implements GeneticCrossover {

	private final double crossoverRatio;

	protected RandomAdapter random = RandomAdapterFactory.getInstance();

	/**
	 * @param crossoverRatio you should provide a number from 0 to 1. The lower the number the bigger the chance
	 *                       of swapping genes
	 */
	public UniformCrossover(final double crossoverRatio) {
		this.crossoverRatio = crossoverRatio;
	}

	@Override
	public Genes[] apply(final Genes genes1, final Genes genes2) {

		final double[] first = genes1.getGenes();
		final double[] second = genes2.getGenes();

		final int size = first.length;

		Genes[] result = new Genes[] {
				new Genes(new double[size]),
				new Genes(new double[size])
		};

		for (int i = 0; i < size; i++) {
			final double[] crossedOver = this.apply(first[i], second[i]);

			result[0].getGenes()[i] = crossedOver[0];
			result[1].getGenes()[i] = crossedOver[1];
		}

		return result;
	}

	private double[] apply(final Double d1, final Double d2) {
		final double[] pair = new double[2];
		if (this.random.nextDouble() > this.crossoverRatio) {
			pair[0] = d2;
			pair[1] = d1;
		} else {
			pair[0] = d1;
			pair[1] = d2;
		}
		return pair;
	}

}
