package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genes;

import java.util.stream.IntStream;

/**
 * The arithmetic crossover simply compute an average from the two numbers provided.
 *
 * @author Vaclav
 */
public class ArithmeticCrossover implements GeneticCrossover {

	@Override
	public Genes[] apply(Genes genes1, Genes genes2) {

		double[] g1 = genes1.getGenes();
		double[] g2 = genes2.getGenes();

		Genes crossedOverGenes = new Genes(((IntStream.range(0, g1.length)
				.mapToDouble(i -> (g1[i] + g2[i]) / 2)
				.toArray())));
		return new Genes[] {crossedOverGenes};
	}
}
