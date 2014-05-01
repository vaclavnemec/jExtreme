package jextreme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.OptimizationAlgorithm;
import jextreme.evolution.genetics.GeneDefinition;
import jextreme.evolution.genetics.Genotype;
import jextreme.evolution.genetics.crossover.ArithmeticCrossover;
import jextreme.evolution.genetics.crossover.GeneticCrossover;
import jextreme.evolution.genetics.crossover.UniformCrossover;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.Specimen;
import jextreme.functions.AxisParallelHyperEllipsoid;
import jextreme.listener.ConsoleEvolutionListener;

/**
 * Example of usage. Optimization of AxisParallelHyperEllipsoid function with
 * genetic algorithm.
 *
 * @author vaclavnemec
 */
public class SimpleExample {

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
    public static final void main(String... args) {

// first we must create solution factory
final SolutionFactory solutionFactory = new SolutionFactory() {

    @Override
    public Solution createSolution(Genotype genotype) {
        // each solution will be instance of AxisParallelHyperEllipsoid
        // genotype 
        return new AxisParallelHyperEllipsoid(genotype);
    }

    @Override
    public Specimen getSpecimen() {
        // Specimen defines range for our searching. Both axis has same ranges
        return new Specimen(Arrays.asList(new GeneDefinition(-AXIS_RANGES, AXIS_RANGES), new GeneDefinition(-AXIS_RANGES, AXIS_RANGES)));
    }
        };

// to create genetic algorithm we must specify which crossover strategies will be used
final List<GeneticCrossover> crossovers = new ArrayList<>();
crossovers.add(new ArithmeticCrossover());
crossovers.add(new UniformCrossover(0.2));

// we need couple of other parameters like population size, number of generations,
// mutation ratio and elitsm ratio to create genetic algorithm instance
final OptimizationAlgorithm algorithm = 
        new GeneticAlgorithm(solutionFactory, crossovers, new ConsoleEvolutionListener(),
                POPULATION_SIZE, NUMBER_OF_GENERATIONS, MUTATION_RATIO, ELITISM_RATE);

// in this moment the genetic algorithm will do its work
final AxisParallelHyperEllipsoid bestFoundSolution = (AxisParallelHyperEllipsoid) algorithm.getOptimumSolution();
// and the best solution should be somewhere around zero
System.out.println("BEST FOUND SOLUTION: " + bestFoundSolution); 
   }

}
