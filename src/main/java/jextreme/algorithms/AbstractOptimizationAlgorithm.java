package jextreme.algorithms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;
import jextreme.evolution.genetics.GeneDefinition;
import jextreme.evolution.genetics.Genotype;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

/**
 *
 * @author Vaclav
 */
public abstract class AbstractOptimizationAlgorithm implements OptimizationAlgorithm {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private long fitnessInvocationCount = 0;

    /**
     *
     * @param solutionFactory
     */
    public AbstractOptimizationAlgorithm(final SolutionFactory solutionFactory) {
        this.solutionFactory = solutionFactory;
    }

    /**
     *
     */
    protected final RandomAdapter random = RandomAdapterFactory.getInstance();

    private final SolutionFactory solutionFactory;

    private Specimen specimen;

    /**
     *
     * @return
     */
    public Specimen getSpecimen() {
        if (this.specimen == null) {
            this.specimen = this.solutionFactory.getSpecimen();
        }
        return this.specimen;
    }

    /**
     *
     * @param genotype
     * @return
     */
    public SolutionHolder createSolution(final Genotype genotype) {
        final Solution solution = this.solutionFactory.createSolution(genotype);
        final SolutionHolder holder = new SolutionHolder();
        holder.setSolution(solution);
        holder.setGenotype(genotype);
        return holder;
    }

    /**
     *
     * @param amount
     * @return
     */
    public List<SolutionHolder> createRandomPopulation(final Integer amount) {
        final List<SolutionHolder> population = new ArrayList<>();
        while (population.size() < amount) {
            population.add(this.createRandomSolution());
        }
        return population;
    }

    /**
     *
     * @return
     */
    public SolutionHolder createRandomSolution() {
        final SolutionHolder holder = new SolutionHolder();

        final Genotype randomGenotype = this.randomGenotype(this.getSpecimen());
        holder.setSolution(this.solutionFactory.createSolution(randomGenotype));

        holder.setGenotype(randomGenotype);
        return holder;
    }

    private Genotype randomGenotype(final Specimen specimen) {
        final List<GeneDefinition> geneDefinitions = specimen.getGeneDefinitions();
        final List<Double> genes = new ArrayList<>();
        geneDefinitions.stream().forEach((geneDefinition) -> {
            genes.add(this.random.nextDouble(geneDefinition.getMinValue(), geneDefinition.getMaxValue()));
        });
        return new Genotype(genes);
    }

    /**
     *
     */
    protected void release() {
        this.threadPool.shutdown();
    }

    /**
     *
     * @param holders
     */
    protected void retrieveFitness(final List<SolutionHolder> holders) {
        final List<Future<Double>> solutionFutures = new ArrayList<>();
        // loop over population and create time simulators to simulate it
        for (final SolutionHolder holder : holders) {
            if (holder.getFitness() == null) {
                // future result will contain result when simulation is done
                final Callable<Double> solutionRunnable = new SolutionCallable(holder);
                final Future<Double> submit = this.threadPool.submit(solutionRunnable);
                // collection of submitted future results
                solutionFutures.add(submit);
            }
        }
        final Iterator<SolutionHolder> holderIterator = holders.iterator();
        for (final Future<Double> solutionFuture : solutionFutures) {
            try {
                this.fitnessInvocationCount++;
                holderIterator.next().setFitness(solutionFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new IllegalStateException("Not able to simulate solution", e);
            }
        }
    }
}

class SolutionCallable implements Callable<Double> {

    private final SolutionHolder holder;

    public SolutionCallable(final SolutionHolder holder) {
        this.holder = holder;
    }

    @Override
    public Double call() throws Exception {
        return this.holder.getSolution().getFitness();
    }
}
