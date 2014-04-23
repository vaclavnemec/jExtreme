package jextreme.functions;

import java.util.Arrays;

import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class Schwefel extends AbstractTestFuntion {

	private final Genotype genotype;

    /**
     *
     * @param genotype
     */
    public Schwefel(final Genotype genotype) {
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
		return this.evaluate(this.genotype.getGenes().toArray(new Double[0]));
	}

    /**
     *
     * @param inputs
     * @return
     */
    public double evaluate(final Double[] inputs) {
		double result = 0;

		for (final double input : inputs) {
			result += (-1) * input * Math.sin(Math.sqrt(Math.abs(input)));
		}
		// result += 418.9829 * inputs.length;
		return -result;
	}

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
		final Double fitness = new Schwefel(new Genotype(Arrays.asList(420.9687, 420.9687))).getFitness();
		System.err.println(fitness);
	}

}