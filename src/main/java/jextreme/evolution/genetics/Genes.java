/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics;

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
     *
     * @return
     */
    @Override
	public String toString() {
		return this.genes.toString();
	}
}
