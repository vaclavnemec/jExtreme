package jextreme.algorithms;

import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

/**
 * @author Vaclav
 * 
 */
public interface EvolutionListener {

    /**
     * @param currentSolutions the list of current solutions in the generation
     */
    void reportGeneration(List<SolutionHolder> currentSolutions);

    /**
     * @param evolutionStep the number of the evolution step
     */
    void reportNewStep(int evolutionStep);

    /**
     * @param best this is the callback with the best solution found
     */
    void reportBestSolution(SolutionHolder best);

}
