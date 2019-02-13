package jextreme.algorithms;

import jextreme.algorithms.params.SomaParams;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;
import jextreme.evolution.util.DescendingFitnessComparator;

import java.util.*;

/**
 * @author Vaclav
 *
 */
public class SomaManyToOne extends AbstractOptimizationAlgorithm {


    private SomaParams params;

    public SomaManyToOne(SomaParams params) {
        super(params);
        this.params = params;
        if (this.params.getPathLength() <= 1) {
            throw new IllegalArgumentException("Path length should not be smaller or equal to 1");
        }
        if (this.params.getStep() > this.params.getPathLength()) {
            throw new IllegalArgumentException("Step should not be greater than path length");
        }
        if (this.params.getStep() <= 0) {
            throw new IllegalArgumentException("Step should be greater than 0");
        }
        if (this.params.getPerturbationLevel() < 0 || this.params.getPerturbationLevel() > 1) {
            throw new IllegalArgumentException("Perturbation level range is <0,1>");
        }
        if (this.params.getPopulationSize() < 2) {
            throw new IllegalArgumentException("Population size should be bigger than 2");
        }
        if (this.params.getAmountOfMigrations() < 1) {
            throw new IllegalArgumentException("Amount of migrations must be at least 1 or bigger");
        }
    }

    @Override
    public Genes getOptimumSolution() {
        List<SolutionHolder> population = this.createRandomPopulation(this.params.getPopulationSize());
        SolutionHolder leader = null;

        for (int i = 0; i < this.params.getAmountOfMigrations(); i++) {
            super.retrieveFitness(population);

            population.sort(new DescendingFitnessComparator());

            final Iterator<SolutionHolder> iterator = population.iterator();
            leader = iterator.next();
            iterator.remove();

            population = this.migrate(leader, population);
        }

        super.release();

        return leader.getGenes();
    }

    private List<SolutionHolder> migrate(final SolutionHolder leader, final List<SolutionHolder> population) {

        final List<SolutionHolder> newPopulation = new ArrayList<>();
        newPopulation.add(leader);
        for (final SolutionHolder solution : population) {
            final List<SolutionHolder> possibleSolutions = this.migrate(leader, solution);
            super.retrieveFitness(possibleSolutions);
            possibleSolutions.sort(new DescendingFitnessComparator());
            Optional<SolutionHolder> first = possibleSolutions.stream().findFirst();
            first.ifPresent(newPopulation::add);
        }

        return newPopulation;
    }

    private List<SolutionHolder> migrate(final SolutionHolder leader, final SolutionHolder solution) {
        final double[] leaderGenes = leader.getGenes().getGenes();
        final double[] solutionGenes = solution.getGenes().getGenes();
        final List<SolutionHolder> possibleSolutions = new ArrayList<>();

        final double[] actualPosition = Arrays.copyOf(solutionGenes, solutionGenes.length);

        // true means adding step, false subtracting step
        final boolean[] directions = new boolean[leaderGenes.length];
        for (int i = 0; i < leaderGenes.length; i++) {
            if (actualPosition[i] < leaderGenes[i]) {
                directions[i] = true;
            } else if (actualPosition[i] > leaderGenes[i]) {
                directions[i] = false;
            } else {
                throw new RuntimeException("Has same dimension value. Think how to solve this. TODO");
            }
        }

        final Specimen specimen = this.getSpecimen();

        final double targetDistance = getDistance(leader, solution) * this.params.getPathLength();
        
        double distance = 0.0;
        while ((distance += this.params.getStep()) < targetDistance) {
            final double[] possibleSolutionGenes = new double[actualPosition.length];

            for (int i = 0; i < actualPosition.length; i++) {
                if (this.random.nextDouble() >= this.params.getPerturbationLevel()) {
                    if (directions[i]) {
                        actualPosition[i] += this.params.getStep();
                    } else {
                        actualPosition[i] -= this.params.getStep();
                    }
                }
                final Range range = specimen.getRanges()[i];
                if (range.getMaxValue() < actualPosition[i] || range.getMinValue() > actualPosition[i]) {
                    possibleSolutions.add(this.createRandomSolution());
                    return possibleSolutions;
                } else {
                    possibleSolutionGenes[i] = actualPosition[i];
                }
            }

            possibleSolutions.add(this.createSolution(new Genes(possibleSolutionGenes)));
        }

        return possibleSolutions;
    }

    @Override
    public String toString() {
        return "SOMA";
    }

    private Double getDistance(SolutionHolder leader, SolutionHolder solution) {
        double sum = .0;
        double[] leaderPosition = leader.getGenes().getGenes();
        double[] solutionPosition = solution.getGenes().getGenes();
        for (int i = 0; i < leaderPosition.length; i++) {
            sum += Math.pow(leaderPosition[i] - solutionPosition[i], 2);
        }
        return Math.sqrt(sum);
    }
}
