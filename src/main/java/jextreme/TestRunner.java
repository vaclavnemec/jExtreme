package jextreme;

import jextreme.algorithms.BlindAlgorithm;
import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.OptimizationAlgorithm;
import jextreme.algorithms.SomaManyToOne;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.genetics.Range;
import jextreme.evolution.genetics.crossover.ArithmeticCrossover;
import jextreme.evolution.genetics.crossover.GeneticCrossover;
import jextreme.evolution.genetics.crossover.UniformCrossover;
import jextreme.evolution.solution.FitnessFunction;
import jextreme.evolution.solution.Specimen;
import jextreme.listener.EmptyEvolutionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Vaclav
 */
public class TestRunner {

    private static int POPULATION = 10;
    private static int GENERATIONS = 10;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        // DeJong
        new TestRunner().compare(genotype -> {
            double d1 = genotype.getGenes()[0];
            double d2 = genotype.getGenes()[1];
            return -(d1 * d1 + d2 * d2);
        }, 0.0);
        // Axis Parallel Hyper Ellipsoid
        new TestRunner().compare(genotype -> {
            double d1 = genotype.getGenes()[0];
            double d2 = genotype.getGenes()[1];
            return -((d1 * d1) + (d1 * d1 + d2 * d2));
        }, 0.0);
        // rotated hyper ellipsoid
        new TestRunner().compare(genotype -> {
            double d1 = genotype.getGenes()[0];
            double d2 = genotype.getGenes()[1];
            return -(d1 * d1 + 2 * d2 * d2);
        }, 0.0);
        // rastigin
        new TestRunner().compare(genotype -> {
            double x = genotype.getGenes()[0];
            double y = genotype.getGenes()[1];
            return -(10 * 2 + (x * x - 10 * Math.cos(2 * Math.PI * x)) + (y * y - 10 * Math.cos(2 * Math.PI + y)));
        }, 0.0);
        // Rosenbrock
        new TestRunner().compare(genotype -> {
            double y = genotype.getGenes()[0];
            double x = genotype.getGenes()[1];
            return -(100 * (y - x * x) * (y - x * x) + (1 - x) * (1 - x));
        }, 0.0);
        // Schwefel
        new TestRunner().compare(genotype -> {
            double[] inputs = genotype.getGenes();
            double result = 0;

            for (double input : inputs) {
                result += (-1) * input * Math.sin(Math.sqrt(Math.abs(input)));
            }
            // result += 418.9829 * inputs.length;
            return -result;
        }, 838.0);
        // Griewangk
        new TestRunner().compare(genotype -> {
            double[] inputs = genotype.getGenes();

            double result = 0;

            double sumPart = 0;
            for (double i : inputs) {
                sumPart += (i / 4000);
            }

            double productPart = 1;
            for (int i = 1; i <= inputs.length; i += 1) {
                productPart *= Math.cos(inputs[i - 1] / Math.sqrt(i));
            }

            result = 1 + sumPart - productPart;

            return -result;
        }, 0.0);
    }

    /**
     * @param globalSolution
     */
    public void compare(FitnessFunction fitnessFunction, double globalSolution) {
        Genes best = null;
        OptimizationAlgorithm bestA = null;
        Collection<OptimizationAlgorithm> algorithms = getAlgorithms(fitnessFunction);
        for (OptimizationAlgorithm a : algorithms) {
            Genes optimumSolution = a.getOptimumSolution();
            if (best == null || fitnessFunction.apply(best) < fitnessFunction.apply(optimumSolution)) {
                best = optimumSolution;
                bestA = a;
            }
        }
        System.out.println(bestA + " found: " + Arrays.toString(best.getGenes()) + " delta:" + Math.abs((globalSolution - fitnessFunction.apply(best))));
    }

    private Collection<OptimizationAlgorithm> getAlgorithms(FitnessFunction fitnessFunction) {
        List<OptimizationAlgorithm> algorithms = new ArrayList<>();
        List<GeneticCrossover> crossovers = new ArrayList<>();
        crossovers.add(new ArithmeticCrossover());
        crossovers.add(new UniformCrossover(0.2));
        Specimen specimen = new Specimen(new Range[] {new Range(-600.0, 600.0), new Range(-600.0, 600.0)});
        algorithms.add(new GeneticAlgorithm(fitnessFunction, specimen, crossovers, new EmptyEvolutionListener(), POPULATION, GENERATIONS * 250, 0.1, 0.05));
        algorithms.add(new SomaManyToOne(400, .99, .1, POPULATION, GENERATIONS, fitnessFunction, specimen));
        algorithms.add(new BlindAlgorithm(fitnessFunction, specimen, 40000));
        return algorithms;
    }

}

