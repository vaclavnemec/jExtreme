package jextreme.evolution.selector;


import jextreme.evolution.selector.DummyObjectFactory;
import jextreme.evolution.selector.RouletteEvolutionSelector;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.random.ApacheCommonsRandomAdapter;
import jextreme.random.RandomAdapter;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RouletteEvolutionSelectorTest {

	protected RandomAdapter random = new ApacheCommonsRandomAdapter();

	protected RouletteEvolutionSelector selector = new RouletteEvolutionSelector();

	@Test
	public void testNormalizeDoubleDoubleMapOfIntegerDouble() {
		final RandomAdapter random = new ApacheCommonsRandomAdapter();
		final List<SolutionHolder> strategies = new ArrayList<>();

		for (int i = 0; i < 20; i++) {
			final SolutionHolder strategyWrapperImpl = DummyObjectFactory.createMockHolder(i);
			strategyWrapperImpl.setFitness(random.nextDouble() * 300 - 150);
			strategies.add(strategyWrapperImpl);
		}

		this.selector.normalize(0, 1, strategies);
		double sum = 0;
		for (final SolutionHolder val : strategies) {
			sum += val.getProbabilityToBeParent();
		}
		Assert.assertEquals(1.0, sum, 0.00000000001);
	}

	/**
	 * Central limit theorem test for lot of selections
	 */
	@Test(invocationCount = 100)
	public void testSelectParents_Clt() {
		final List<SolutionHolder> solutions = DummyObjectFactory.createSolutions(this.random, 3);
		// 66.6 probability to be chosen
		solutions.get(0).setFitness(2 / 3.0);
		// 33.3 probability to be chosen
		solutions.get(1).setFitness(1 / 3.0);
		// 0 probability to be chosen
		solutions.get(2).setFitness(0 / 3.0);

		final int[] frequencies = new int[3];
		for (int i = 0; i < 99999; i++) {
			this.selector.initSolutions(new ArrayList<>(solutions));
			final List<SolutionHolder> parents = this.selector.selectParents(solutions, 1);
			final int index = parents.get(0).getId();
			frequencies[index] = frequencies[index] + 1;
		}
		final double delta = 500.0;
		Assert.assertEquals(frequencies[0], 66666, delta);
		Assert.assertEquals(frequencies[1], 33333, delta);
		Assert.assertEquals(frequencies[2], 0, delta);
	}

}
