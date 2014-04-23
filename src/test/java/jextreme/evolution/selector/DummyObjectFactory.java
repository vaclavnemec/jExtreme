package jextreme.evolution.selector;


import java.util.ArrayList;
import java.util.List;

import jextreme.evolution.solution.SolutionHolder;
import jextreme.random.RandomAdapter;

public class DummyObjectFactory {
	static List<SolutionHolder> createSolutions(final RandomAdapter random, final int amount) {
		final List<SolutionHolder> strategyHolders = new ArrayList<>();

		for (int i = 0; i < amount; i++) {
			final SolutionHolder strategyHolder = createMockHolder(i);
			strategyHolder.setProbabilityToBeParent(0.0);
			strategyHolder.setFitness(random.nextDouble() * 500 - 200);
			strategyHolders.add(strategyHolder);
		}
		return strategyHolders;
	}

	static SolutionHolder createMockHolder(final int i) {
		final SolutionHolder holder = new SolutionHolder();
		holder.setId(i);
		return holder;
	}
}
