package jextreme.algorithms;

import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.optimization.apache.DoubleChromosome;
import jextreme.optimization.apache.RandomMutationPolicy;
import org.apache.commons.math3.genetics.*;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vaclav
 */
public class ApacheGeneticAlgorithmAdapter extends AbstractOptimizationAlgorithm {

    private final double crossoverRatio;
    private final double mutationRate;
    private final int generations;
    private final int population;
    private final SolutionFactory solutionFactory;
    private final double elitisticRatio;
    final RandomAdaptor adaptor = new RandomAdaptor(new MersenneTwister());

    /**
     *
     * @return
     */
    @Override
    public Solution getOptimumSolution() {

        // initialize a new genetic algorithm
        final GeneticAlgorithm ga = new GeneticAlgorithm(new UniformCrossover<Double>(this.crossoverRatio), 1, new RandomMutationPolicy(this.adaptor,
                this.solutionFactory), this.mutationRate, new TournamentSelection(2));

        // initial population
        final Population initial = this.getInitialPopulation();

        // stopping condition
        final StoppingCondition stopCond = new FixedGenerationCount((int) this.generations);

        // run the algorithm
        final Population finalPopulation = ga.evolve(initial, stopCond);

        // best chromosome from the final population
        final Chromosome bestFinal = finalPopulation.getFittestChromosome();
        return getSolution(bestFinal);
    }

    private Solution getSolution(final Chromosome bestFinal) {
        return this.convertToSolution(bestFinal);
    }

    /**
     *
     * @param crossoverRatio
     * @param mutationRate
     * @param generations
     * @param population
     * @param solutionFactory
     * @param elitisticRatio
     */
    public ApacheGeneticAlgorithmAdapter(final double crossoverRatio, final double mutationRate, final int generations, final int population,
            final SolutionFactory solutionFactory, final double elitisticRatio) {
        super(solutionFactory);
        this.crossoverRatio = crossoverRatio;
        this.mutationRate = mutationRate;
        this.generations = generations;
        this.population = population;
        this.solutionFactory = solutionFactory;
        this.elitisticRatio = elitisticRatio;
    }

    private Population getInitialPopulation() {
        return new ElitisticListPopulation(this.createInitialChromosomes(), this.population, this.elitisticRatio);
    }

    private List<Chromosome> createInitialChromosomes() {
        return this.convert(this.createRandomPopulation(this.population));
    }

    private List<Chromosome> convert(final List<SolutionHolder> randomPopulation) {
        final List<Chromosome> result = new ArrayList<>();
        for (final SolutionHolder solution : randomPopulation) {
            result.add(this.convert(solution));
        }
        return result;
    }

    private Chromosome convert(final SolutionHolder solution) {
        return new DoubleChromosome(solution.getGenotype().getGenes(), this.solutionFactory);
    }

    protected Solution convertToSolution(final Chromosome chromosome) {

        return new Solution() {
            @Override
            public Double getFitness() {
                return chromosome.getFitness();
            }
        };
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "APACHE";
    }

}
