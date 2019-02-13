package jextreme.evolution.genetics;

import java.util.Arrays;

/**
 * @author Vaclav
 * 
 */
public class Genes {

	private final double[] genes;

    /**
     *
     * @param genes
     */
    public Genes(final double[] genes) {
		this.genes = genes;
	}

    /**
     *
     * @return
     */
    public double[] getGenes() {
		return this.genes;
	}

	/**
	 * Convenient method to get a value of the n-th dimension
	 * @param n to identify the dimension
	 * @return the value of the dimension
	 */
	public double getDimension(int n) {
    	return this.genes[n];
	}

    /**
     *
     * @return
     */
    @Override
	public String toString() {
		return Arrays.toString(this.genes);
	}
}
