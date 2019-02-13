package jextreme;

import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.params.GeneticAlgorithmParams;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.genetics.crossover.ArithmeticCrossover;
import jextreme.evolution.genetics.crossover.UniformCrossover;
import jextreme.evolution.solution.FitnessFunction;
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

    /**
     * Triggers the optimization and prints an output.
     *
     * @param args not used
     */
    public static void main(String... args) {

        FitnessFunction fitnessFunction = xAndY -> {
            double x = xAndY.getDimension(0);
            double y = xAndY.getDimension(1);
            return -((x * x) + (x * x + y * y));
        };

        Specimen specimen = new Specimen(new Range[] {
                new Range(-600, 600),
                new Range(-600, 600)
        });

        GeneticAlgorithmParams params = new GeneticAlgorithmParams();
        params.setFitnessFunction(fitnessFunction);
        params.setSpecimen(specimen);
        params.setPopulationSize(100);
        params.setNumberOfGenerations(1000);
        params.setMutationProbability(0.1);
        params.setElitismRatio(0.05);
        params.setEvolutionListener(new ConsoleEvolutionListener());
        params.setCrossovers(Arrays.asList(new ArithmeticCrossover(), new UniformCrossover(0.2)));

        // in this moment the genetic algorithm will do its work
        Genes optimumSolution = new GeneticAlgorithm(params).getOptimumSolution();

        // and the best solution should be somewhere around zero
        System.out.println("BEST FOUND SOLUTION: " + Arrays.toString(optimumSolution.getGenes()));
    }

}
