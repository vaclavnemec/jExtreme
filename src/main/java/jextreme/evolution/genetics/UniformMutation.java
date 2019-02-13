package jextreme.evolution.genetics;

import jextreme.evolution.solution.Specimen;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

/**
 *
 * @author Vaclav
 */
public class UniformMutation implements Mutation {

    private final RandomAdapter random = RandomAdapterFactory.getInstance();

    @Override
    public Genes mutate(final Genes genotype, final Specimen specimen) {
        final double[] genes = genotype.getGenes();
        final double[] mutatedGenes = new double[genes.length];
        final Range[] ranges = specimen.getRanges();
        for (int i = 0 ; i < genes.length; i ++) {
            final Range nextGene = ranges[i];
            // 1/n probability to mutate gene
            if (this.random.nextInt(genes.length) == 0) {
                mutatedGenes[i] = this.mutateGene(nextGene);
                // System.err.println("orig: " + gene + " mutated: " +
                // mutatedGenes.get(mutatedGenes.size() - 1));
            } else {
                mutatedGenes[i] = genes[i];
            }
        }
        return new Genes(mutatedGenes);
    }

    private double mutateGene(final Range range) {
        final double mutatedGene = this.random.nextDouble(range.getMinValue(), range.getMaxValue());
        if (range.getMinValue() > mutatedGene || range.getMaxValue() < mutatedGene) {
            throw new IllegalStateException();
        }
        return mutatedGene;
    }

}
