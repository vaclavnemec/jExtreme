package jextreme.algorithms;

import jextreme.evolution.genetics.Genes;

/**
 * @author Vaclav
 */
public interface OptimizationAlgorithm {

    /**
     * @return optimum solution found by the algorithm
     */
    Genes getOptimumSolution();
}
