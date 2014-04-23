package jextreme.algorithms;

import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.solution.SolutionHolder;

/**
 *
 * @author Vaclav
 */
public class BlindAlgorithm extends AbstractOptimizationAlgorithm {

    private final long amountOfEvaluations;

    /**
     *
     * @param solutionFactory
     * @param amountOfEvaluations
     */
    public BlindAlgorithm(final SolutionFactory solutionFactory, final long amountOfEvaluations) {
        super(solutionFactory);
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
    public Solution getOptimumSolution() {
        Solution best = null;
        Double bestFitness = null;
        for (long i = 0; i < this.amountOfEvaluations; i++) {
            final SolutionHolder randomSolution = this.createRandomSolution();
            final Double fitness = randomSolution.getSolution().getFitness();
            if (best == null || fitness > bestFitness) {
                best = randomSolution.getSolution();
                bestFitness = fitness;
            }
        }
        return best;
    }
}
