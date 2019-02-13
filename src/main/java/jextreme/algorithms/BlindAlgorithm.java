package jextreme.algorithms;

import jextreme.algorithms.params.BlindAlgorithmParams;
import jextreme.evolution.genetics.Genes;
import jextreme.evolution.solution.SolutionHolder;

/**
 *
 * @author Vaclav
 */
public class BlindAlgorithm extends AbstractOptimizationAlgorithm {

    private final long amountOfEvaluations;

    public BlindAlgorithm(BlindAlgorithmParams params) {
        super(params);
        if (params.getNumberOfEvaluations() <= 0) {
            throw new IllegalArgumentException("Negative amount of evaluations");
        }
        this.amountOfEvaluations = params.getNumberOfEvaluations();
    }

    @Override
    public String toString() {
        return "BLIND";
    }

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
