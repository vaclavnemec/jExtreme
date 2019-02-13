package jextreme;

import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.OptimizationAlgorithm;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.genetics.crossover.ArithmeticCrossover;
import jextreme.evolution.genetics.crossover.UniformCrossover;
import jextreme.evolution.solution.Specimen;
import jextreme.listener.ConsoleEvolutionListener;

import java.util.Arrays;

/**
 * Example of usage. Optimization of AxisParallelHyperEllipsoid function with
 * genetic algorithm.
 *
 * @author vaclavnemec
 */
class SimpleExample {

    // parameters of genetic algorithm
    private static final int POPULATION_SIZE = 100;
    private static final double ELITISM_RATE = 0.05;
    private static final double MUTATION_RATIO = 0.1;
    private static final int NUMBER_OF_GENERATIONS = 1000;

    private static final double AXIS_RANGES = 600;

    /**
     * Triggers the optimization and prints an output.
     *
     * @param args not used
     */
    public static void main(String... args) {

        // we need couple of other parameters like population size, number of generations,
        // mutation ratio and elitsm ratio to create genetic algorithm instance
        final OptimizationAlgorithm algorithm =
                new GeneticAlgorithm(
                        genes -> {
                            double d1 = genes.getGenes()[0];
                            double d2 = genes.getGenes()[1];
                            return -((d1 * d1) + (d1 * d1 + d2 * d2));
                        },
                        new Specimen(new Range[] {new Range(-AXIS_RANGES, AXIS_RANGES), new Range(-AXIS_RANGES, AXIS_RANGES)}),
                        Arrays.asList(new ArithmeticCrossover(), new UniformCrossover(0.2)),
                        new ConsoleEvolutionListener(),
                        POPULATION_SIZE,
                        NUMBER_OF_GENERATIONS,
                        MUTATION_RATIO,
                        ELITISM_RATE);

        // in this moment the genetic algorithm will do its work
        Genes optimumSolution = algorithm.getOptimumSolution();

        // and the best solution should be somewhere around zero
        System.out.println("BEST FOUND SOLUTION: " + Arrays.toString(optimumSolution.getGenes()));
    }

}
