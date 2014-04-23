package jextreme.functions;

import java.util.Arrays;
import java.util.List;

import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class Griewangk extends AbstractTestFuntion {

	private final Genotype genotype;

    /**
     *
     * @param genotype
     */
    public Griewangk(final Genotype genotype) {
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
		final List<Double> inputs = this.genotype.getGenes();

		double result = 0;

		double sumPart = 0;
		for (final Double i : inputs) {
			sumPart += (i / 4000);
		}

		double productPart = 1;
		for (int i = 1; i <= inputs.size(); i += 1) {
			productPart *= Math.cos(inputs.get(i - 1) / Math.sqrt(i));
		}

		result = 1 + sumPart - productPart;

		return -result;
	}

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
		System.err.println(new Griewangk(new Genotype(Arrays.asList(0.0, 0.0))).getFitness());
	}
}