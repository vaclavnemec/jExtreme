package jextreme.evolution.genetics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jextreme.random.RandomAdapterFactory;
import jextreme.evolution.solution.Specimen;
import jextreme.random.RandomAdapter;

/**
 *
 * @author Vaclav
 */
public class UniformMutation implements Mutation {

    private final RandomAdapter random = RandomAdapterFactory.getInstance();

    /**
     *
     * @param genotype
     * @param specimen
     * @return
     */
    @Override
    public Genotype mutate(final Genotype genotype, final Specimen specimen) {
        final List<Double> genes = genotype.getGenes();
        final List<Double> mutatedGenes = new ArrayList<>();
        final Iterator<GeneRange> defs = specimen.getGeneRanges().iterator();
        for (final Double gene : genes) {
            final GeneRange nextGene = defs.next();
            // 1/n probability to mutate gene
            if (this.random.nextInt(genes.size()) == 0) {
                mutatedGenes.add(this.mutateGene(nextGene));
				// System.err.println("orig: " + gene + " mutated: " +
                // mutatedGenes.get(mutatedGenes.size() - 1));
            } else {
                mutatedGenes.add(gene);
            }
        }
        return new Genotype(mutatedGenes);
    }

    /**
     *
     * @param geneDefinition
     * @return
     */
    protected Double mutateGene(final GeneRange geneDefinition) {
        final Double mutatedGene = this.random.nextDouble(geneDefinition.getMinValue().doubleValue(), geneDefinition.getMaxValue().doubleValue());
        if (geneDefinition.getMinValue().compareTo(mutatedGene) > 0 || geneDefinition.getMaxValue().compareTo(mutatedGene) < 0) {
            throw new IllegalStateException();
        }
        return mutatedGene;
    }

}
