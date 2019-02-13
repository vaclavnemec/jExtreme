package jextreme.evolution.selector;

import java.util.List;
import java.util.stream.IntStream;

import jextreme.evolution.solution.SolutionHolder;

/**
 * @author Vaclav
 */
public class RankEvolutionSelector extends AbstractEvolutionSelector {

    @Override
	public void initSolutions(final List<SolutionHolder> solutions) {

		super.initSolutions(solutions);

		final int size = this.getArithmeticSum(solutions.size());

		int i = 1;

		for (int j = solutions.size() - 1; j >= 0; j--) {
			solutions.get(j).setProbabilityToBeParent((double) i++ / size);
		}
	}

    /**
     * @param limit the upper limit for the calculation
     * @return the arithmetic sum from 1 to the provided limit parameter
     */
    protected int getArithmeticSum(final int limit) {
		if (limit < 1) {
			throw new IllegalArgumentException("The parameter 'limit' should be at least 1.");
		}
		return IntStream.rangeClosed(1, limit).sum();
	}

}
