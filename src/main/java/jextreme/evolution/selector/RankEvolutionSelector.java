package jextreme.evolution.selector;

import java.util.List;

import jextreme.evolution.solution.SolutionHolder;

/**
 *
 * @author Vaclav
 */
public class RankEvolutionSelector extends AbstractEvolutionSelector {

    /**
     *
     * @param solutions
     */
    @Override
	public void initSolutions(final List<SolutionHolder> solutions) {

		super.initSolutions(solutions);

		final int size = this.getArithmetricSum(1, solutions.size());

		int i = 1;

		for (int j = solutions.size() - 1; j >= 0; j--) {
			solutions.get(j).setProbabilityToBeParent((double) i++ / size);
		}
	}

    /**
     *
     * @param from
     * @param to
     * @return
     */
    protected int getArithmetricSum(final int from, final int to) {
		if (to < from) {
			throw new IllegalArgumentException("Parameter 'to' is smaller than 'from'");
		}
		int sum = 0;
		for (int i = from; i <= to; i++) {
			sum += i;
		}
		return sum;
	}

}
