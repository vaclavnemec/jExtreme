/**
 * Sep 13, 2013
 */
package jextreme.evolution.util;

import jextreme.evolution.solution.SolutionHolder;

import java.util.Comparator;

/**
 * @author vaclavnemec
 * 
 */
public class DescendingFitnessComparator implements Comparator<SolutionHolder> {

    /**
     *
     * @param s1
     * @param s2
     * @return
     */
    @Override
	public int compare(final SolutionHolder s1, final SolutionHolder s2) {
		return s2.getFitness().compareTo(s1.getFitness());
	}

}
