/**
 * Aug 26, 2013
 */
package jextreme.algorithms;

import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

import static javafx.scene.input.KeyCode.T;

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
     * @param selectedParents
     */
    void reportParents(List<SolutionHolder> selectedParents);

    /**
     *
     * @param best
     */
    void reportBestSolution(SolutionHolder best);

}
