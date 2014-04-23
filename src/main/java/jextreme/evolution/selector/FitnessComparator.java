package jextreme.evolution.selector;

import java.util.Comparator;

import jextreme.evolution.solution.SolutionHolder;

/**
 *
 * @author Vaclav
 */
public class FitnessComparator implements Comparator<SolutionHolder> {

    /**
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(final SolutionHolder o1, final SolutionHolder o2) {
        return o1.getFitness().compareTo(o2.getFitness());
    }

}
