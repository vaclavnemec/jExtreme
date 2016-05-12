package jextreme.evolution.genetics.crossover;

/**
 *
 * @author Vaclav
 */
public class UniformCrossover extends AbstractGeneticCrossover {

	public static final int RESULT_SIZE = 2;
	private final double crossoverRatio;

    /**
     *
     * @param crossoverRatio
     */
    public UniformCrossover(final double crossoverRatio) {
		this.crossoverRatio = crossoverRatio;
	}

    /**
     *
     * @param d1
     * @param d2
     * @return
     */
    @Override
	protected Double[] apply(final Double d1, final Double d2) {
		final Double[] pair = new Double[2];
		if (this.random.nextDouble() > this.crossoverRatio) {
			pair[0] = d2;
			pair[1] = d1;
		} else {
			pair[0] = d1;
			pair[1] = d2;
		}
		return pair;
	}

	@Override
	protected int crossoverResultSize() {
		return RESULT_SIZE;
	}
}
