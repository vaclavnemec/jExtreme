/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics.crossover;

/**
 * @author Vaclav
 * 
 */
public class ArithmeticCrossover extends AbstractGeneticCrossover {

    /**
     *
     * @param g1
     * @param g2
     * @return
     */
    @Override
	protected Double[] apply(final Double g1, final Double g2) {
		return new Double[] { (g1 + g2) / 2 };
	}

	@Override
	protected int crossoverResultSize() {
		return 1;
	}

}
