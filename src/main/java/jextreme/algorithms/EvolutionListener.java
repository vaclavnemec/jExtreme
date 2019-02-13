package jextreme.algorithms;

import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

/**
 * @author Vaclav
 * 
 */
public interface EvolutionListener {

    /**
     *
     * @param currentSolutions
     */
    void reportGeneration(List<SolutionHolder> currentSolutions);

    /**
     *
     * @param evolutionStep
     */
    void reportNewStep(int evolutionStep);

    /**
     *
     * @param best
     */
    void reportBestSolution(SolutionHolder best);

}
