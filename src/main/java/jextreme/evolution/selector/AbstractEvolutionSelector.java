/**
 * Aug 9, 2013
 */
package jextreme.evolution.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jextreme.evolution.util.DescendingFitnessComparator;
import jextreme.random.RandomAdapterFactory;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.random.RandomAdapter;

/**
 * @author Vaclav
 */
public abstract class AbstractEvolutionSelector implements EvolutionSelector {

    /**
     *
     */
    protected RandomAdapter random = RandomAdapterFactory.getInstance();

    /**
     *
     * @param solutions
     */
    @Override
	public void initSolutions(final List<SolutionHolder> solutions) {
		Collections.sort(solutions, new DescendingFitnessComparator());
	}

    /**
     *
     * @param a
     * @param b
     * @param solutions
     */
    protected void normalize(final double a, final double b, final List<SolutionHolder> solutions) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for (final SolutionHolder solution : solutions) {
			final Double fitness = solution.getFitness();
			min = Math.min(min, fitness);
			max = Math.max(max, fitness);
		}
		double sum = 0;
		for (final SolutionHolder solution : solutions) {
			final double scaled = this.scale(a, b, max, min, solution.getFitness());
			solution.setScaledFitness(scaled);
			sum += Math.abs(scaled);
		}

		if (sum == 0) {
			System.err.println("Sum of scalled fitness values is 0");
			for (final SolutionHolder solution : solutions) {
				solution.setProbabilityToBeParent(1.0);
			}
		} else {
			for (final SolutionHolder solution : solutions) {
				solution.setProbabilityToBeParent(solution.getScaledFitness() / sum);
			}
		}
	}

	private double scale(final double a, final double b, final double max, final double min, final double value) {
		final double factor = max - min;
		if (factor == .0) {
			return a;
		}
		return (b - a) * (value - min) / factor + a;
	}

    /**
     *
     * @param solutions
     * @param amountOfParents
     * @return
     */
    @Override
	public List<SolutionHolder> selectParents(final List<SolutionHolder> solutions, final int amountOfParents) {
		final ArrayList<SolutionHolder> copy = new ArrayList<>(solutions);
		final List<SolutionHolder> parents = new ArrayList<>();

		while (parents.size() < amountOfParents) {
			final double sufficintLevel = this.random.nextDouble();

			double sum = 0;
			for (final Iterator<SolutionHolder> iterator = copy.iterator(); iterator.hasNext();) {
				final SolutionHolder possibleParent = iterator.next();
				sum += possibleParent.getProbabilityToBeParent();
				if (sum > sufficintLevel) {
					parents.add(possibleParent);
					iterator.remove();
					break;
				}
			}

		}
		return parents;
	}
}
