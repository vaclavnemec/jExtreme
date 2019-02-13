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

}
