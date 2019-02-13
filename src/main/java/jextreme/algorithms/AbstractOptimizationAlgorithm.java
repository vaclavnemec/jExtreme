package jextreme.algorithms;

import jextreme.algorithms.params.OptimizationAlgorithmParams;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * @author Vaclav
 */
abstract class AbstractOptimizationAlgorithm implements OptimizationAlgorithm {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();

    private final OptimizationAlgorithmParams params;

    /**
     * @param params the parameters for the optimization
     */
    AbstractOptimizationAlgorithm(OptimizationAlgorithmParams params) {
        this.params = params;
    }

    /**
     *
     */
    final RandomAdapter random = RandomAdapterFactory.getInstance();

    /**
     * @return the specimen used in the optimization
     */
    Specimen getSpecimen() {
        return this.params.getSpecimen();
    }

    /**
     * @param genes the genes to be used
     * @return solution holder with the solution
     */
    SolutionHolder createSolution(final Genes genes) {
        final Solution solution = getSolution(genes);
        final SolutionHolder holder = new SolutionHolder();
        holder.setSolution(solution);
        holder.setGenes(genes);
        return holder;
    }

    private Solution getSolution(Genes genes) {
        return () -> params.getFitnessFunction().apply(genes);
    }

    /**
     * @param amount the size of the population
     * @return a random population of solutions
     */
    List<SolutionHolder> createRandomPopulation(final int amount) {
        final List<SolutionHolder> population = new ArrayList<>();
        while (population.size() < amount) {
            population.add(this.createRandomSolution());
        }
        return population;
    }

    /**
     * @return random solution
     */
    SolutionHolder createRandomSolution() {
        final SolutionHolder holder = new SolutionHolder();

        final Genes randomGenes = this.randomGenotype(this.getSpecimen());

        holder.setSolution(getSolution(randomGenes));

        holder.setGenes(randomGenes);
        return holder;
    }

    private Genes randomGenotype(final Specimen specimen) {
        Range[] ranges = specimen.getRanges();
        int length = ranges.length;
        double[] genes = new double[length];
        for (int i = 0; i < length; i ++) {
            Range range = ranges[i];
            genes[i] = this.random.nextDouble(range.getMinValue(), range.getMaxValue());
        }
        return new Genes(genes);
    }

    /**
     *
     */
    void release() {
        this.threadPool.shutdown();
    }

    /**
     *
     * @param holders
     */
    void retrieveFitness(final List<SolutionHolder> holders) {
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
    public Double call() {
        return this.holder.getSolution().getFitness();
    }
}
