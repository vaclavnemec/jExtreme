package jextreme.algorithms;

import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.solution.FitnessFunction;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.random.RandomAdapter;
import jextreme.random.RandomAdapterFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author Vaclav
 */
public abstract class AbstractOptimizationAlgorithm implements OptimizationAlgorithm {

    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private long fitnessInvocationCount = 0;

    /**
     *
     * @param fitnessFunction
     */
    public AbstractOptimizationAlgorithm(FitnessFunction fitnessFunction, final Specimen specimen) {
        this.fitnessFunction = fitnessFunction;
        this.specimen = specimen;
    }

    /**
     *
     */
    protected final RandomAdapter random = RandomAdapterFactory.getInstance();

    protected final FitnessFunction fitnessFunction;

    protected final Specimen specimen;

    /**
     *
     * @return
     */
    public Specimen getSpecimen() {
        return this.specimen;
    }

    /**
     *
     * @param genes
     * @return
     */
    public SolutionHolder createSolution(final Genes genes) {
        final Solution solution = getSolution(genes);
        final SolutionHolder holder = new SolutionHolder();
        holder.setSolution(solution);
        holder.setGenes(genes);
        return holder;
    }

    private Solution getSolution(Genes genes) {
        final Double fitness = fitnessFunction.apply(genes);
        return new Solution() {
            @Override
            public Double getFitness() {
                return fitness;
            }
        };
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
