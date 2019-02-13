package jextreme.evolution.util;

import jextreme.evolution.solution.SolutionHolder;

import java.util.Comparator;

/**
 * @author vaclavnemec
 * 
 */
public class DescendingFitnessComparator implements Comparator<SolutionHolder> {

    @Override
	public int compare(final SolutionHolder s1, final SolutionHolder s2) {
		return s2.getFitness().compareTo(s1.getFitness());
	}

}
