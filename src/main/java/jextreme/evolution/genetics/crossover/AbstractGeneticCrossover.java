/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

import jextreme.evolution.genetics.Genes;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

/**
 * @author Vaclav
 * 
 */
public abstract class AbstractGeneticCrossover implements GeneticCrossover {

    /**
     *
     */
    protected RandomAdapter random = RandomAdapterFactory.getInstance();

    /**
     *
     * @param genes1
     * @param genes2
     * @return
     */
    @Override
	public Genes[] apply(final Genes genes1, final Genes genes2) {

		final double[] first = genes1.getGenes();
		final double[] second = genes2.getGenes();

		final int size = first.length;

		Genes[] result = initializeResult(crossoverResultSize(), size);;

		for (int i = 0; i < size; i++) {
			final Double[] mutated = this.apply(first[i], second[i]);

			for (int j = 0; j < mutated.length; j++) {
				result[j].getGenes()[i] = mutated[j];
			}
		}

		return result;
	}

	private Genes[] initializeResult(int resultSize, int genesSize) {
		Genes[] genes = new Genes[resultSize];
		for (int i = 0; i < resultSize; i ++) {
			genes[i] = new Genes(new double[genesSize]);
		}
		return genes;
	}

	/**
     *
     * @param d1
     * @param d2
     * @return
     */
    protected abstract Double[] apply(Double d1, Double d2);

	protected abstract int crossoverResultSize();

}
