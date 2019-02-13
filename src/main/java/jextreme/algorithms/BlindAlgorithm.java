package jextreme.algorithms;

import jextreme.evolution.genetics.Genes;
import jextreme.evolution.solution.FitnessFunction;
import jextreme.evolution.solution.SolutionHolder;
import jextreme.evolution.solution.Specimen;

/**
 *
 * @author Vaclav
 */
public class BlindAlgorithm extends AbstractOptimizationAlgorithm {

    private final long amountOfEvaluations;

    /**
     * @param fitnessFunction the function to be optimised
     * @param specimen the specimen for the solution
     * @param amountOfEvaluations
     */
    public BlindAlgorithm(final FitnessFunction fitnessFunction, final Specimen specimen, final long amountOfEvaluations) {
        super(fitnessFunction, specimen);
        if (amountOfEvaluations <= 0) {
            throw new IllegalArgumentException("Negative amount of evaluations");
        }
        this.amountOfEvaluations = amountOfEvaluations;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "BLIND";
    }

    /**
     *
     * @return
     */
    @Override
    public Genes getOptimumSolution() {
        SolutionHolder best = null;
        Double bestFitness = null;
        for (long i = 0; i < this.amountOfEvaluations; i++) {
            final SolutionHolder randomSolution = this.createRandomSolution();
            final Double fitness = randomSolution.getSolution().getFitness();
            if (best == null || fitness > bestFitness) {
                best = randomSolution;
                bestFitness = fitness;
            }
        }
        return best.getGenes();
    }
}
