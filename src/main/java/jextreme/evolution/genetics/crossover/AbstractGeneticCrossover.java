/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

import java.util.ArrayList;
import java.util.List;

import jextreme.random.RandomAdapterFactory;
import jextreme.evolution.genetics.Genotype;
import jextreme.random.RandomAdapter;

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
     * @param genotype1
     * @param genotype2
     * @return
     */
    @Override
	public Genotype[] apply(final Genotype genotype1, final Genotype genotype2) {

		final List<Double> first = genotype1.getGenes();
		final List<Double> second = genotype2.getGenes();

		final List<List<Double>> m = new ArrayList<>();

		final int size = first.size();
		for (int i = 0; i < size; i++) {
			final Double[] mutated = this.apply(first.get(i), second.get(i));
			for (int j = 0; j < mutated.length; j++) {
				if (m.size() <= j) {
					m.add(new ArrayList<Double>());
				}
				m.get(j).add(mutated[j]);
			}
		}

		final Genotype[] g = new Genotype[m.size()];
		int i = 0;
		for (final List<Double> d : m) {
			g[i++] = new Genotype(d);
		}
		return g;
	}

    /**
     *
     * @param d1
     * @param d2
     * @return
     */
    protected abstract Double[] apply(Double d1, Double d2);

}
