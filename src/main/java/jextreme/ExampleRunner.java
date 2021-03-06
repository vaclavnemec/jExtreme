package jextreme;

import jextreme.algorithms.BlindAlgorithm;
import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.OptimizationAlgorithm;
import jextreme.algorithms.SomaManyToOne;
import jextreme.algorithms.params.BlindAlgorithmParams;
import jextreme.algorithms.params.GeneticAlgorithmParams;
import jextreme.algorithms.params.SomaParams;
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
class ExampleRunner {

    public static void main(String[] args) {
        // DeJong
        FitnessFunction dejongFunction = dimensions -> {
            double x = dimensions.getDimension(0);
            double y = dimensions.getDimension(1);
            return -(x * x + y * y);
        };

        // Axis Parallel Hyper Ellipsoid
        FitnessFunction hyperEllipsoid = dimensions -> {
            double x = dimensions.getDimension(0);
            double y = dimensions.getDimension(1);
            return -((x * x) + (x * x + y * y));
        };

        // rotated hyper ellipsoid
        FitnessFunction rotatedHyperEllipsoid = dimensions -> {
            double x = dimensions.getDimension(0);
            double y = dimensions.getDimension(1);
            return -(x * x + 2 * y * y);
        };

        // rastigin
        FitnessFunction rastigin = dimensions -> {
            double x = dimensions.getDimension(0);
            double y = dimensions.getDimension(1);
            return -(10 * 2 + (x * x - 10 * Math.cos(2 * Math.PI * x)) + (y * y - 10 * Math.cos(2 * Math.PI + y)));
        };

        // Rosenbrock
        FitnessFunction rosenbrock = dimensions -> {
            double y = dimensions.getDimension(0);
            double x = dimensions.getDimension(1);
            return -(100 * (y - x * x) * (y - x * x) + (1 - x) * (1 - x));
        };

        // Schwefel
        FitnessFunction schwefel = dimensions -> {
            double[] inputs = dimensions.getGenes();
            double result = 0;

            for (double input : inputs) {
                result += (-1) * input * Math.sin(Math.sqrt(Math.abs(input)));
            }
            // result += 418.9829 * inputs.length;
            return -result;
        };

        // Griewangk
        FitnessFunction griewangk = dimensions -> {
            double[] inputs = dimensions.getGenes();

            double result;

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
        };

        ExampleRunner exampleRunner = new ExampleRunner();
        exampleRunner.runAllAlgorithmsAndCompare(dejongFunction, 0.0);
        exampleRunner.runAllAlgorithmsAndCompare(hyperEllipsoid, 0.0);
        exampleRunner.runAllAlgorithmsAndCompare(rotatedHyperEllipsoid, 0.0);
        exampleRunner.runAllAlgorithmsAndCompare(rastigin, 0.0);
        exampleRunner.runAllAlgorithmsAndCompare(rosenbrock, 0.0);
        exampleRunner.runAllAlgorithmsAndCompare(schwefel, 838.0);
        exampleRunner.runAllAlgorithmsAndCompare(griewangk, 0.0);
    }

    private void runAllAlgorithmsAndCompare(FitnessFunction fitnessFunction, double globalSolution) {
        Genes best = null;
        OptimizationAlgorithm bestA = null;
        Collection<OptimizationAlgorithm> algorithms = getAllSupportedAlgorithms(fitnessFunction);
        for (OptimizationAlgorithm a : algorithms) {
            Genes optimumSolution = a.getOptimumSolution();
            if (best == null || fitnessFunction.apply(best) < fitnessFunction.apply(optimumSolution)) {
                best = optimumSolution;
                bestA = a;
            }
        }
        System.out.println(bestA + " found: " + Arrays.toString(best.getGenes()) + " delta:" + Math.abs((globalSolution - fitnessFunction.apply(best))));
    }

    private Collection<OptimizationAlgorithm> getAllSupportedAlgorithms(FitnessFunction fitnessFunction) {
        List<OptimizationAlgorithm> algorithms = new ArrayList<>();
        List<GeneticCrossover> crossovers = new ArrayList<>();
        crossovers.add(new ArithmeticCrossover());
        crossovers.add(new UniformCrossover(0.2));

        // specimen for all the functions is the same
        // specimen limits the algorithms range
        Specimen specimen = new Specimen(new Range[] {new Range(-600.0, 600.0), new Range(-600.0, 600.0)});

        GeneticAlgorithmParams gaParams = new GeneticAlgorithmParams();
        gaParams.setFitnessFunction(fitnessFunction);
        gaParams.setSpecimen(specimen);
        gaParams.setCrossovers(crossovers);
        gaParams.setEvolutionListener(new EmptyEvolutionListener());
        gaParams.setPopulationSize(10);
        gaParams.setNumberOfGenerations(10 * 250);
        gaParams.setMutationProbability(0.1);
        gaParams.setElitismRatio(0.05);

        SomaParams somaParams = new SomaParams();
        somaParams.setFitnessFunction(fitnessFunction);
        somaParams.setSpecimen(specimen);
        somaParams.setAmountOfMigrations(10);
        somaParams.setPopulationSize(10);
        somaParams.setPathLength(400);
        somaParams.setStep(.99);
        somaParams.setPerturbationLevel(0.1);

        BlindAlgorithmParams blindAlgorithmParams = new BlindAlgorithmParams();
        blindAlgorithmParams.setFitnessFunction(fitnessFunction);
        blindAlgorithmParams.setSpecimen(specimen);
        blindAlgorithmParams.setNumberOfEvaluations(40000);


        algorithms.add(new GeneticAlgorithm(gaParams));
        algorithms.add(new SomaManyToOne(somaParams));
        algorithms.add(new BlindAlgorithm(blindAlgorithmParams));

        return algorithms;
    }

}

