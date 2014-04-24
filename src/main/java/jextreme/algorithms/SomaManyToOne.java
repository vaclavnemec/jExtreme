/**
 * 28. 12. 2013
 */
package jextreme.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jextreme.evolution.genetics.GeneRange;
import jextreme.evolution.genetics.Genotype;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.evolution.util.DescendingFitnessComparator;

/**
 * @author Vaclav
 *
 */
public class SomaManyToOne extends AbstractOptimizationAlgorithm {

    private final long amountOfMigrations;
    private final int populationSize;
    private final double prt;
    private final double step;
    private final double pathLength;

    /**
     *
     * @param pathLength
     * @param step
     * @param perturbationLevel
     * @param populationSize
     * @param amountOfMigrations
     * @param solutionFactory
     */
    public SomaManyToOne(final double pathLength, final double step, final double perturbationLevel, final int populationSize, final long amountOfMigrations,
            final SolutionFactory solutionFactory) {
        super(solutionFactory);
        if (pathLength <= 1) {
            throw new IllegalArgumentException("Path length should not be smaller or equal to 1");
        }
        if (step > pathLength) {
            throw new IllegalArgumentException("Step should not be greater than path length");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("Step should be greater than 0");
        }
        if (perturbationLevel < 0 || perturbationLevel > 1) {
            throw new IllegalArgumentException("Perturbation level range is <0,1>");
        }
        if (populationSize < 2) {
            throw new IllegalArgumentException("Population size should be bigger than 2");
        }
        if (amountOfMigrations < 1) {
            throw new IllegalArgumentException("Amount of migrations must be at least 1 or bigger");
        }
        this.pathLength = pathLength;
        this.step = step;
        this.prt = perturbationLevel;
        this.populationSize = populationSize;
        this.amountOfMigrations = amountOfMigrations;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.vtraderplatform.evolution.EvolutionAlgorithm#evolution()
     */

    /**
     *
     * @return
     */
    
    @Override
    public Solution getOptimumSolution() {
        List<SolutionHolder> population = this.createRandomPopulation(this.populationSize);
        SolutionHolder leader = null;

        for (int i = 0; i < this.amountOfMigrations; i++) {
            super.retrieveFitness(population);

            Collections.sort(population, new DescendingFitnessComparator());

            final Iterator<SolutionHolder> iterator = population.iterator();
            leader = iterator.next();
            iterator.remove();

            population = this.migrate(leader, population);
        }

        super.release();

        return leader.getSolution();
    }

    private List<SolutionHolder> migrate(final SolutionHolder leader, final List<SolutionHolder> population) {

        final List<SolutionHolder> newPopulation = new ArrayList<>();
        newPopulation.add(leader);
        for (final SolutionHolder solution : population) {
            final List<SolutionHolder> possibleSolutions = this.migrate(leader, solution);
            super.retrieveFitness(possibleSolutions);
            Collections.sort(possibleSolutions, new DescendingFitnessComparator());
            final SolutionHolder bestVariant = possibleSolutions.iterator().next();
            newPopulation.add(bestVariant);
        }

        return newPopulation;
    }

    private List<SolutionHolder> migrate(final SolutionHolder leader, final SolutionHolder solution) {
        final List<Double> leaderGenes = leader.getGenotype().getGenes();
        final List<Double> solutionGenes = solution.getGenotype().getGenes();
        final List<SolutionHolder> possibleSolutions = new ArrayList<>();

        final Double[] actualPosition = solutionGenes.toArray(new Double[0]);

        // true means adding step, false subtracting step
        final boolean[] directions = new boolean[leaderGenes.size()];
        for (int i = 0; i < leaderGenes.size(); i++) {
            if (actualPosition[i] < leaderGenes.get(i)) {
                directions[i] = true;
            } else if (actualPosition[i] > leaderGenes.get(i)) {
                directions[i] = false;
            } else {
                throw new RuntimeException("Has same dimension value. Think how to solve this. TODO");
            }
        }

        final Specimen specimen = this.getSpecimen();

        final Double targetDistance = getDistance(leader, solution) * this.pathLength;
        
        double distance = 0.0;
        while ((distance += this.step) < targetDistance) {
            final List<Double> possibleSolutionGenes = new ArrayList<>();

            for (int i = 0; i < actualPosition.length; i++) {
                if (this.random.nextDouble() >= this.prt) {
                    if (directions[i]) {
                        actualPosition[i] += this.step;
                    } else {
                        actualPosition[i] -= this.step;
                    }
                }
                final GeneRange geneDefinition = specimen.getGeneRanges().get(i);
                if (geneDefinition.getMaxValue() < actualPosition[i] || geneDefinition.getMinValue() > actualPosition[i]) {
                    possibleSolutions.add(this.createRandomSolution());
                    return possibleSolutions;
                } else {
                    possibleSolutionGenes.add(actualPosition[i]);
                }
            }

            possibleSolutions.add(this.createSolution(new Genotype(possibleSolutionGenes)));
        }

        return possibleSolutions;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "SOMA";
    }

    private Double getDistance(SolutionHolder leader, SolutionHolder solution) {
        double sum = .0;
        Iterator<Double> leaderIterator = leader.getGenotype().getGenes().iterator();
        Iterator<Double> solutionIterator = solution.getGenotype().getGenes().iterator();
        while (leaderIterator.hasNext()) {
            Double leaderGene = leaderIterator.next();
            Double solutionGene = solutionIterator.next();
            sum += Math.pow(leaderGene - solutionGene, 2);
        }
        return Math.sqrt(sum);
    }
}