package jextreme.evolution.selector;

import java.util.List;

import jextreme.evolution.solution.SolutionHolder;

/**
 * @author vaclavnemec
 */
public class RouletteEvolutionSelector extends AbstractEvolutionSelector {

    /**
     *
     * @param solutions
     */
    @Override
	public void initSolutions(final List<SolutionHolder> solutions) {
		super.initSolutions(solutions);
		// normalized fitness values
		this.normalize(solutions);
	}

	/**
	 * @param solutions
	 */
	protected void normalize(final List<SolutionHolder> solutions) {

		if (solutions.size() == 1) {
			solutions.get(0).setProbabilityToBeParent(1.0);
			solutions.get(0).setScaledFitness(1.0);
			return;
		}

		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (final SolutionHolder solution : solutions) {
			final double fitness = solution.getFitness();
			min = Math.min(min, fitness);
			max = Math.max(max, fitness);
		}

		double sum = 0;
		for (final SolutionHolder solution : solutions) {
			final double scaled = this.scale(0, 1, max, min, solution.getFitness());
			solution.setScaledFitness(scaled);
			sum += scaled;
		}

		for (final SolutionHolder solution : solutions) {
			solution.setProbabilityToBeParent(solution.getScaledFitness() / sum);
		}
	}

	double scale(final double from, final double to, final double max, final double min, final double value) {
		final double factor = max - min;
		if (factor == .0) {
			return from;
		}
		return (to - from) * (value - min) / factor + from;
	}
}
