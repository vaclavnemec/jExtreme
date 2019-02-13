package jextreme.evolution.selector;

import java.util.ArrayList;
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
	private final RandomAdapter random = RandomAdapterFactory.getInstance();

    /**
     * @param solutions the list of solutions to be initialized
     */
    @Override
	public void initSolutions(final List<SolutionHolder> solutions) {
		solutions.sort(new DescendingFitnessComparator());
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
			final double sufficientLevel = this.random.nextDouble();

			double sum = 0;
			for (final Iterator<SolutionHolder> iterator = copy.iterator(); iterator.hasNext();) {
				final SolutionHolder possibleParent = iterator.next();
				sum += possibleParent.getProbabilityToBeParent();
				if (sum > sufficientLevel) {
					parents.add(possibleParent);
					iterator.remove();
					break;
				}
			}

		}
		return parents;
	}
}
