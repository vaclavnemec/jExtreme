/**
 * Aug 22, 2013
 */
package jextreme.evolution.genetics;

import java.util.List;

/**
 * @author Vaclav
 * 
 */
public class Genotype {
	private List<Double> genes;

    /**
     *
     * @param genes
     */
    public Genotype(final List<Double> genes) {
		this.genes = genes;
	}

    /**
     *
     * @param genes
     */
    public void setGenes(final List<Double> genes) {
		this.genes = genes;
	}

    /**
     *
     * @return
     */
    public List<Double> getGenes() {
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
