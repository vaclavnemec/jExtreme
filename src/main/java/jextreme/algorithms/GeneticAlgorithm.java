package jextreme.algorithms;

import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Mutation;
import jextreme.evolution.genetics.UniformMutation;
import jextreme.evolution.genetics.crossover.GeneticCrossover;
import jextreme.evolution.selector.EvolutionSelector;
import jextreme.evolution.selector.RankEvolutionSelector;
import jextreme.evolution.solution.FitnessFunction;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.evolution.util.DescendingFitnessComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author vaclavnemec
 */
public class GeneticAlgorithm extends AbstractOptimizationAlgorithm {

    private final EvolutionListener evolutionListener;
    
    private final List<GeneticCrossover> crossovers;

    private final EvolutionSelector selector = new RankEvolutionSelector();
    
    private final Mutation mutation = new UniformMutation();
    
    private final int populationSize;

    private final long maximumNumberOfSteps;

    private final double mutationRate;

    private final double elitismRate;

    /**
     *
     * @param fitnesFunction
     * @param crossovers
     * @param evolutionListener
     * @param populationSize
     * @param steps
     * @param mutationRate
     * @param elitismRate
     */
    public GeneticAlgorithm(final FitnessFunction fitnesFunction, final Specimen specimen, final List<GeneticCrossover> crossovers, EvolutionListener evolutionListener, final int populationSize,
                            final long steps, final double mutationRate, final double elitismRate) {
        super(fitnesFunction, specimen);
        this.crossovers = crossovers;
        this.populationSize = populationSize;
        this.maximumNumberOfSteps = steps;
        this.mutationRate = mutationRate;
        this.elitismRate = elitismRate;
        this.evolutionListener = evolutionListener;
    }

    // STATE OF EVOLUTION
    /**
     * Collection with current population
     */
    private List<SolutionHolder> solutions;

    /**
     * Evolution step counter. 0 is first.
     */
    private int evolutionStep = 0;

    /**
     *
     * @return
     */
    @Override
    public Genes getOptimumSolution() {
        // generates null population pseudo-randomly
        this.solutions = this.createRandomPopulation(this.populationSize);

        for (;;) {
            // report step
            this.evolutionListener.reportNewStep(++this.evolutionStep);

            // simulate solutions
            this.retrieveFitness(this.solutions);

            // sort for report
            Collections.sort(this.solutions, new DescendingFitnessComparator());

            // report current solutions
            this.evolutionListener.reportGeneration(this.solutions);

            // end
            if (this.isComplete()) {
                break;
            }
            
            // select elite member as a parent
            final List<SolutionHolder> eliteMembers = this.getEliteMembers(this.solutions, this.elitismRate);

            // new generation
            this.solutions = this.createNewGeneration(this.mutationRate, this.solutions, this.populationSize - eliteMembers.size());

            this.solutions.addAll(eliteMembers);

        }

        Collections.sort(this.solutions, new DescendingFitnessComparator());
        this.evolutionListener.reportBestSolution(this.solutions.iterator().next());

        this.release();

        return this.solutions.iterator().next().getGenes();
    }

    private List<SolutionHolder> getEliteMembers(final List<SolutionHolder> solutions, final double elitismRate) {

        final Iterator<SolutionHolder> iterator = solutions.iterator();
        final List<SolutionHolder> eliteMembers = new ArrayList<>();
        final long amountOfEliteSolutions = Math.round(solutions.size() * elitismRate);

        for (int i = 0; i < amountOfEliteSolutions; i++) {
            final SolutionHolder eliteMember = iterator.next();
            eliteMembers.add(eliteMember);
        }
        return eliteMembers;
    }

    /**
     *
     * @param mutationRate
     * @param solutions
     * @param populationAmount
     * @return
     */
    public List<SolutionHolder> createNewGeneration(final double mutationRate, final List<SolutionHolder> solutions, final Integer populationAmount) {
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
                if (this.random.nextDouble() < mutationRate) {
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
            Collections.sort(selectParents, new DescendingFitnessComparator());
            solutionHolder.add(selectParents.iterator().next());
        }

        return solutionHolder;
    }

    private GeneticCrossover selectCrossoverUniformly() {
        return this.crossovers.get(this.random.nextInt(this.crossovers.size()));
    }

    private boolean isComplete() {
        return this.evolutionStep >= this.maximumNumberOfSteps;
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
