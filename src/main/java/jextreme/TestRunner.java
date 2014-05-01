package jextreme;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jextreme.algorithms.ApacheGeneticAlgorithmAdapter;
import jextreme.algorithms.BlindAlgorithm;
import jextreme.algorithms.GeneticAlgorithm;
import jextreme.algorithms.OptimizationAlgorithm;
import jextreme.algorithms.SomaManyToOne;
import jextreme.evolution.genetics.GeneDefinition;
import jextreme.evolution.genetics.Genotype;
import jextreme.evolution.genetics.crossover.ArithmeticCrossover;
import jextreme.evolution.genetics.crossover.GeneticCrossover;
import jextreme.evolution.genetics.crossover.UniformCrossover;
import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.Specimen;
import jextreme.functions.AxisParallelHyperEllipsoid;
import jextreme.functions.DeJong;
import jextreme.functions.Griewangk;
import jextreme.functions.Rastrigin;
import jextreme.functions.Rosenbrock;
import jextreme.functions.RotatedHyperEllipsoid;
import jextreme.functions.Schwefel;
import jextreme.listener.ConsoleEvolutionListener;

/**
 *
 * @author Vaclav
 */
public class TestRunner {

    private static final int POPULATION = 10;
    private static final int GENERATIONS = 10;

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        new TestRunner().compare(DeJong.class, 0.0);
        new TestRunner().compare(AxisParallelHyperEllipsoid.class, 0.0);
        new TestRunner().compare(RotatedHyperEllipsoid.class, 0.0);
        new TestRunner().compare(Rastrigin.class, 0.0);
        new TestRunner().compare(Rosenbrock.class, 0.0);
        new TestRunner().compare(Schwefel.class, 838.0);
        new TestRunner().compare(Griewangk.class, 0.0);
    }

    /**
     *
     * @param <T>
     * @param solutionClass
     * @param globalSolution
     */
    public <T extends Solution> void compare(final Class<T> solutionClass, final double globalSolution) {
        final List<OptimizationAlgorithm> algorithms = this.getAlgorithms(solutionClass);

        Solution best = null;
        OptimizationAlgorithm bestA = null;
        for (final OptimizationAlgorithm a : algorithms) {
            final Solution optimumSolution = a.getOptimumSolution();
            if (best == null || best.getFitness() < optimumSolution.getFitness()) {
                best = optimumSolution;
                bestA = a;
            }
        }
        System.out.println(bestA + " found: " + best + " delta:" + Math.abs((globalSolution - best.getFitness())));
    }

    private <T extends Solution> List<OptimizationAlgorithm> getAlgorithms(final Class<T> solutionClass) {
        final List<OptimizationAlgorithm> algorithms = new ArrayList<>();
        final List<GeneticCrossover> crossovers = new ArrayList<>();
        crossovers.add(new ArithmeticCrossover());
        crossovers.add(new UniformCrossover(0.2));
        algorithms.add(new GeneticAlgorithm(new SolutionFactoryImpl(solutionClass), crossovers, new ConsoleEvolutionListener(), POPULATION, GENERATIONS * 250, 0.1, 0.05));

        algorithms.add(new SomaManyToOne(400, .99, .1, POPULATION, GENERATIONS, new SolutionFactoryImpl(solutionClass)));

        algorithms.add(new ApacheGeneticAlgorithmAdapter(0.2, 0.1, GENERATIONS, POPULATION, new SolutionFactoryImpl(solutionClass), 0.05));

        algorithms.add(new BlindAlgorithm(new SolutionFactoryImpl(solutionClass), 40000));
        return algorithms;
    }

}

class SolutionFactoryImpl implements SolutionFactory {

    private final Class<? extends Solution> solutionClass;

    public <T extends Solution> SolutionFactoryImpl(final Class<T> solutionClass) {
        this.solutionClass = solutionClass;
    }

    @Override
    public Specimen getSpecimen() {
        return new Specimen(Arrays.asList(new GeneDefinition(-BORDER, BORDER), new GeneDefinition(-BORDER, BORDER)));
    }

    private static final double BORDER = 600;

    @Override
    public Solution createSolution(final Genotype genotype) {
        try {
            return this.solutionClass.getConstructor(Genotype.class).newInstance(genotype);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException("solution could not be instantiated");
        }
    }

}
