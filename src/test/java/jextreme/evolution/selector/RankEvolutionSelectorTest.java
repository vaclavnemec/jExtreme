package jextreme.evolution.selector;


import jextreme.evolution.selector.RankEvolutionSelector;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import jextreme.evolution.solution.SolutionHolder;
import jextreme.random.ApacheCommonsRandomAdapter;
import jextreme.random.RandomAdapter;

public class RankEvolutionSelectorTest {

	protected RandomAdapter random = new ApacheCommonsRandomAdapter();

	protected RankEvolutionSelector selector = new RankEvolutionSelector();

	@Test
	public void testGetArithmetricSum() {
		Assert.assertEquals(this.selector.getArithmetricSum(1, 2), 3);
		Assert.assertEquals(this.selector.getArithmetricSum(0, 2), 3);
		Assert.assertEquals(this.selector.getArithmetricSum(-1, 1), 0);
		Assert.assertEquals(this.selector.getArithmetricSum(0, 0), 0);
		Assert.assertEquals(this.selector.getArithmetricSum(5, 5), 5);
		Assert.assertEquals(this.selector.getArithmetricSum(-5, 5), 0);
		Assert.assertEquals(this.selector.getArithmetricSum(3, 5), 12);
		Assert.assertEquals(this.selector.getArithmetricSum(-5, 2), -12);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testGetArithmetricSumException() {
		this.selector.getArithmetricSum(5, 2);
	}

	/**
	 * Central limit theorem test for lot of selections
	 */
	@Test(invocationCount = 100)
	public void testSelectParents_Clt() {
		final List<SolutionHolder> solutions = DummyObjectFactory.createSolutions(this.random, 3);
		// 3/6 probability to be chosen
		solutions.get(0).setFitness(2000.0);
		// 2/6 probability to be chosen
		solutions.get(1).setFitness(5.0);
		// 1/6 probability to be chosen
		solutions.get(2).setFitness(-30.0);

		final int[] frequencies = new int[3];
		for (int i = 1; i < 100000; i++) {
			this.selector.initSolutions(new ArrayList<>(solutions));
			final int index = this.selector.selectParents(solutions, 1).get(0).getId();
			frequencies[index] = frequencies[index] + 1;
		}
		final double delta = 500.0;
		Assert.assertEquals(frequencies[0], 100000 / 2.0, delta);
		Assert.assertEquals(frequencies[1], 100000 / 3.0, delta);
		Assert.assertEquals(frequencies[2], 100000 / 6.0, delta);
	}

}
