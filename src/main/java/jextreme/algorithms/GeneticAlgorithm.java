package jextreme.algorithms;

import jextreme.algorithms.params.GeneticAlgorithmParams;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Mutation;
import jextreme.evolution.genetics.UniformMutation;
import jextreme.evolution.genetics.crossover.GeneticCrossover;
import jextreme.evolution.selector.EvolutionSelector;
import jextreme.evolution.selector.RankEvolutionSelector;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.util.DescendingFitnessComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author vaclavnemec
 */
public class GeneticAlgorithm extends AbstractOptimizationAlgorithm {

    private final EvolutionSelector selector = new RankEvolutionSelector();

    private final Mutation mutation = new UniformMutation();
    private final GeneticAlgorithmParams params;

    /**
     * @param params all the parameters for the genetic algorithm
     */
    public GeneticAlgorithm(GeneticAlgorithmParams params) {
        super(params);
        this.params = params;

        if (params.getElitismRatio() < 0 && params.getElitismRatio() > 1) {
            throw new IllegalArgumentException("Elitism rate not in range (0,1)");
        }
    }

    // STATE OF EVOLUTION

    /**
     * Evolution step counter. 0 is first.
     */
    private final AtomicInteger evolutionStep = new AtomicInteger(0);

    /**
     *
     * @return
     */
    @Override
    public Genes getOptimumSolution() {
        // generates null population pseudo-randomly
        List<SolutionHolder> solutions = this.createRandomPopulation(this.params.getPopulationSize());

        for (;;) {
            // report step
            this.params.getEvolutionListener().reportNewStep(evolutionStep.addAndGet(1));

            // simulate solutions
            this.retrieveFitness(solutions);

            // sort for report
            solutions.sort(new DescendingFitnessComparator());

            // report current solutions
            this.params.getEvolutionListener().reportGeneration(solutions);

            // end
            if (this.isComplete()) {
                break;
            }
            
            // select elite member as a parent
            final List<SolutionHolder> eliteMembers = this.getEliteMembers(solutions, this.params.getElitismRatio());

            // new generation
            solutions = this.createNewGeneration(this.params.getMutationProbability(), solutions, this.params.getPopulationSize() - eliteMembers.size());

            solutions.addAll(eliteMembers);

        }

        solutions.sort(new DescendingFitnessComparator());
        this.params.getEvolutionListener().reportBestSolution(solutions.iterator().next());

        this.release();

        return solutions.iterator().next().getGenes();
    }

    List<SolutionHolder> getEliteMembers(final List<SolutionHolder> solutions, final double elitismRate) {

        final long amountOfEliteSolutions = Math.round(solutions.size() * elitismRate);

        return solutions.stream().limit(amountOfEliteSolutions).collect(Collectors.toList());
    }

    /**
     *
     * @param mutationProbability
     * @param solutions
     * @param populationAmount
     * @return
     */
    private List<SolutionHolder> createNewGeneration(final double mutationProbability, final List<SolutionHolder> solutions, final Integer populationAmount) {
        final List<SolutionHolder> newSolutions = new ArrayList<>();

        this.selector.initSolutions(solutions);

        while (newSolutions.size() < populationAmount) {

            final Iterator<SolutionHolder> parents = this.selectParents(solutions).iterator();

            final SolutionHolder firstParent = parents.next();

            final SolutionHolder secondParent = parents.next();

            final GeneticCrossover crossover = this.selectCrossoverUniformly();

            final Genes[] genes = crossover.apply(firstParent.getGenes(), secondParent.getGenes());

            for (final Genes g : genes) {
                if (newSolutions.size() >= populationAmount) {
                    break;
                }
                if (this.random.nextDouble() < mutationProbability) {
                    final Genes mutatedGenes = this.mutation.mutate(g, super.getSpecimen());
                    newSolutions.add(this.createSolution(mutatedGenes));
                } else {
                    newSolutions.add(this.createSolution(g));
                }
            }
        }

        return newSolutions;
    }

    private List<SolutionHolder> selectParents(final List<SolutionHolder> solutions) {
        final List<SolutionHolder> solutionHolder = new ArrayList<>();

        while (solutionHolder.size() < 2) {
            final List<SolutionHolder> selectParents = this.selector.selectParents(solutions, 2);
            selectParents.sort(new DescendingFitnessComparator());
            solutionHolder.add(selectParents.iterator().next());
        }

        return solutionHolder;
    }

    private GeneticCrossover selectCrossoverUniformly() {
        return this.params.getCrossovers().get(this.random.nextInt(this.params.getCrossovers().size()));
    }

    private boolean isComplete() {
        return this.evolutionStep.get() >= this.params.getNumberOfGenerations();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "GA";
    }

}
